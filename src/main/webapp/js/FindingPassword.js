// FindingPassword.js

document.addEventListener('DOMContentLoaded', () => {
  // JSP에서 선언된 contextPath 전역 변수를 사용합니다.
  const ctx       = typeof contextPath !== 'undefined' ? contextPath : '';

  // 1) 아이디 확인 폼
  const findForm   = document.getElementById('findForm');
  const findMsg    = document.getElementById('findMsg');
  const usernameEl = document.getElementById('username');

  // 2) 비밀번호 수정 폼
  const resetForm  = document.getElementById('resetForm');
  const resetMsg   = document.getElementById('resetMsg');
  const idHidden   = document.getElementById('id');

  // 3) 비밀번호 입력 & 메시지
  const pw1    = document.getElementById('pw1');
  const pw2    = document.getElementById('pw2');
  const pw1Msg = document.getElementById('pw1-msg');
  const pw2Msg = document.getElementById('pw2-msg');

  // 비밀번호 형식: 영문+숫자 포함, 8자 이상
  const pwRegex = /^(?=.*[A-Za-z])(?=.*\d).{8,}$/;

  // ───────────────────────────────────────────────
  // 1) 아이디 확인 및 폼 전환
  findForm.addEventListener('submit', async e => {
    e.preventDefault();
    findMsg.textContent = '';
    findMsg.className   = 'message';

    const username = usernameEl.value.trim();
    if (!username) {
      findMsg.textContent = '아이디를 입력해 주세요.';
      findMsg.classList.add('error');
      return;
    }

    try {
      const res = await fetch(
        `${ctx}/IdDuplicateCheck.do?username=${encodeURIComponent(username)}`,
        { method: 'GET' }
      );
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const { available } = await res.json();

      if (!available) {
        // 아이디 존재
        findMsg.textContent = '아이디가 확인되었습니다. 비밀번호를 수정할 수 있습니다.';
        findMsg.classList.add('success');
        idHidden.value = username;  // hidden 필드에 저장
        document.getElementById('findContainer').classList.add('hidden');
        document.getElementById('resetContainer').classList.remove('hidden');
      } else {
        findMsg.textContent = '아이디가 존재하지 않거나 잘못 입력되었습니다.';
        findMsg.classList.add('error');
      }
    } catch (err) {
      console.error('아이디 확인 오류:', err);
      findMsg.textContent = '서버와 통신 중 오류가 발생했습니다.';
      findMsg.classList.add('error');
    }
  });

  // ───────────────────────────────────────────────
  // 2) 실시간 비밀번호 형식 & 일치 메시지
  pw1.addEventListener('input', () => {
    const ok = pwRegex.test(pw1.value);
    pw1Msg.textContent = ok
      ? '안전한 비밀번호 형식입니다.'
      : '비밀번호는 영문+숫자 포함 8자 이상이어야 합니다.';
    pw1Msg.className = `message ${ok ? 'green' : 'red'}`;
    checkMatch();
  });

  pw2.addEventListener('input', checkMatch);

  function checkMatch() {
    if (!pw2.value) {
      pw2Msg.textContent = '';
      pw2Msg.className = 'message';
      return;
    }
    const match = pw1.value === pw2.value;
    pw2Msg.textContent = match
      ? '비밀번호가 일치합니다.'
      : '비밀번호가 일치하지 않습니다.';
    pw2Msg.className = `message ${match ? 'green' : 'red'}`;
  }

  // ───────────────────────────────────────────────
  // 3) 비밀번호 수정 요청
  resetForm.addEventListener('submit', async e => {
    e.preventDefault();
    resetMsg.textContent = '';
    resetMsg.className   = 'message';

    const id          = idHidden.value.trim();
    const updatePw    = pw1.value.trim();
    const confirmPw   = pw2.value.trim();

    // 클라이언트 검증
    if (!pwRegex.test(updatePw)) {
      resetMsg.textContent = '올바른 비밀번호 형식을 사용해 주세요.';
      resetMsg.classList.add('error');
      return;
    }
    if (updatePw !== confirmPw) {
      resetMsg.textContent = '비밀번호가 일치하지 않습니다.';
      resetMsg.classList.add('error');
      return;
    }

    try {
      const response = await fetch(`${ctx}/ChangePassword.do`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          id: id,
          updatePw: updatePw,
          confirmPassword: confirmPw
        })
      });

      if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
      }

      const resultText = (await response.text()).trim();
      console.log('서버 응답:', resultText);

      if (resultText === 'success') {
        resetMsg.textContent = '비밀번호가 성공적으로 변경되었습니다.';
        resetMsg.classList.add('success');
        window.location.href = `${ctx}/GoLogin.do`;
      } else {
        resetMsg.textContent = '비밀번호 변경에 실패했습니다. 다시 시도해주세요.';
        resetMsg.classList.add('error');
      }
    } catch (err) {
      console.error('비밀번호 변경 오류:', err);
	  resetMsg.textContent = '서버와 통신 중 오류가 발생했습니다.';
	  resetMsg.classList.add('error');
	  window.location.href = `${ctx}/GoFindingPassword.do`;
    }
  });
});
