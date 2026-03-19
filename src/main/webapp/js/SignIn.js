'use strict';

document.addEventListener('DOMContentLoaded', () => {
  const path = window.path;  // JSP에서 정의한 contextPath

  /*** 1) 카카오 우편번호 팝업 ***/
  const btnPost = document.getElementById('btnPostcode');
  const layerBg = document.getElementById('postcodeLayer');
  const layerWrp = document.getElementById('postcodeContainer');
  const btnClose = document.getElementById('closePostcodeLayer');

  function execDaumPostcode() {
    layerBg.style.display = 'flex';
    const old = layerWrp.querySelector('.wrap');
    if (old) old.remove();

    new daum.Postcode({
      oncomplete(data) {
        let addr = data.userSelectedType === 'R'
          ? data.roadAddress
          : data.jibunAddress;
        let extra = '';
        if (data.userSelectedType === 'R') {
          if (data.bname && /[동|로|가]$/.test(data.bname)) extra += data.bname;
          if (data.buildingName && data.apartment === 'Y') {
            extra += extra
              ? `, ${data.buildingName}`
              : data.buildingName;
          }
          if (extra) addr += ` ${extra}`;
        }
        document.getElementById('postcode').value = data.zonecode;
        document.getElementById('address').value = addr;
        document.getElementById('detailAddress').focus();
        layerBg.style.display = 'none';
      },
      width: '100%',
      height: '100%'
    }).embed(layerWrp);
  }

  if (btnPost)  btnPost.addEventListener('click', execDaumPostcode);
  if (btnClose) btnClose.addEventListener('click', () => {
    layerBg.style.display = 'none';
  });

  /*** 2) 팝업 자동 띄우기 제어 ***/
  let showPostcodePopup = false;
  if (showPostcodePopup && layerBg) {
    layerBg.style.display = 'flex';
  }

  /*** 3) 입력 요소 초기화 함수 ***/
  function initSignInFeatures() {
    // 3-1) 아이디 형식 + 중복 확인
    const usernameInput = document.getElementById('username');
    const usernameMsg   = document.getElementById('username-msg');
    if (usernameInput && usernameMsg) {
      let checkTimer = null;
      usernameInput.addEventListener('input', () => {
        const v = usernameInput.value.trim();
        const valid = /^[0-9A-Za-z]{4,10}$/.test(v);

        if (!valid) {
          clearTimeout(checkTimer);
          usernameMsg.textContent = '아이디는 영문+숫자 조합, 4~10자이어야 합니다.';
          usernameMsg.className = 'message red';
          return;
        }

        usernameMsg.textContent = '형식은 적합합니다. 중복 여부를 확인합니다…';
        usernameMsg.className = 'message blue';

        clearTimeout(checkTimer);
        checkTimer = setTimeout(() => {
          fetch(`/SafeVoice/IdDuplicateCheck.do?username=${encodeURIComponent(v)}`)
            .then(res => res.json())
            .then(data => {
			  if(data.error){
				usernameMsg.textContent = data.error;
				usernameMsg.className = 'message red';
				return;
			  }
              if (data.available) {
                usernameMsg.textContent = '사용 가능한 아이디입니다.';
                usernameMsg.className = 'message green';
              } else {
                usernameMsg.textContent = '이미 사용 중인 아이디입니다.';
                usernameMsg.className = 'message red';
              }
            })
            .catch(() => {
              usernameMsg.textContent = '서버 확인 중 오류가 발생했습니다.';
              usernameMsg.className = 'message red';
            });
        }, 500);
      });
    }

    // 3-2) 비밀번호 검사
    const pw1 = document.getElementById('pw1');
    const pw2 = document.getElementById('pw2');
    const msg1 = document.getElementById('pw1-msg');
    const msg2 = document.getElementById('pw2-msg');
    if (pw1 && pw2 && msg1 && msg2) {
      const checkMatch = () => {
        if (!pw2.value) {
          msg2.textContent = '';
          msg2.className = 'message';
          return;
        }
        const m = pw1.value === pw2.value;
        msg2.textContent = m
          ? '비밀번호가 일치합니다.'
          : '비밀번호가 일치하지 않습니다.';
        msg2.className = `message ${m ? 'green' : 'red'}`;
      };
      pw1.addEventListener('input', () => {
        const ok = /^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$/.test(pw1.value);
        msg1.textContent = ok
          ? '안전한 비밀번호 형식입니다.'
          : '비밀번호는 영문+숫자 포함 최소 8자 이상이어야 합니다.';
        msg1.className = `message ${ok ? 'blue' : 'red'}`;
        checkMatch();
      });
      pw2.addEventListener('input', checkMatch);
    }

    // 3-3) 이메일 결합
	  const emailId   = document.getElementById("email-id");
	  const domainTxt = document.getElementById("domain-txt");
	  const domainSel = document.getElementById("domain-list");
	  const emailFull = document.getElementById("email-full");

	  if (emailId && domainTxt && domainSel && emailFull) {
	    function updateEmail() {
	      console.log(
	        "[updateEmail] select value =", `"${domainSel.value}"`,
	        "disabled before =", domainTxt.disabled
	      );

	      if (domainSel.value.trim() === "type") {
	        // 직접입력일 때: 이전에 disabled 상태였을 경우에만 value 초기화
	        if (domainTxt.disabled) {
	          domainTxt.value = "";
	        }
	        domainTxt.removeAttribute("disabled");
	        domainTxt.disabled = false;
	        domainTxt.focus();
	        console.log("→ 직접입력: disabled 해제");
	      } else {
	        domainTxt.setAttribute("disabled", "disabled");
	        domainTxt.disabled = true;
	        domainTxt.value = domainSel.value.trim();
	        console.log(`→ ${domainSel.value}: disabled 설정`);
	      }

	      emailFull.value = `${emailId.value}@${domainTxt.value}`;
	      console.log(
	        "disabled after =", domainTxt.disabled,
	        "domainTxt.value =", `"${domainTxt.value}"`
	      );
	    }

	    domainSel.addEventListener("change", updateEmail);
	    emailId.addEventListener("input", updateEmail);
	    domainTxt.addEventListener("input", updateEmail);
	    updateEmail();
	  }

    // 3-4) 전화번호 자동 포커스
    [['phone1','phone2','phone3','MyNum']].forEach(([p1,p2,p3,out]) => {
      const els = [p1,p2,p3].map(id => document.getElementById(id));
      const outEl = document.getElementById(out);
      if (els.every(x=>x) && outEl) {
        els.forEach((el,i,arr) => {
          el.addEventListener('input', () => {
            el.value = el.value.replace(/\D/g,'');
            if (el.value.length === el.maxLength) {
              if (i < 2) arr[i+1].focus();
              else {
                const val = `${els[0].value}-${els[1].value}-${els[2].value}`;
                outEl.textContent = val;
                document.getElementById('tel').value = val;
              }
            }
          });
        });
      }
    });

    // 3-5) 다크모드 토글
    const toggleBtn = document.getElementById('toggleMode');
    if (toggleBtn) {
      if (localStorage.getItem('theme')==='dark') {
        document.body.classList.add('dark-mode');
        toggleBtn.textContent = '☀️';
      }
      toggleBtn.addEventListener('click', () => {
        const dark = document.body.classList.toggle('dark-mode');
        toggleBtn.textContent = dark ? '☀️' : '🌙';
        localStorage.setItem('theme', dark?'dark':'light');
      });
    }

    // 3-6) 약관 모달
    const openTerms  = document.getElementById('openTerms');
    const termsModal = document.getElementById('termsModal');
    const closeTerms = document.getElementById('closeTerms');
    const agreeBtn   = document.getElementById('agreeBtn');
    const cancelBtn  = document.getElementById('cancelBtn');
    const agreeAll   = document.getElementById('agreeAll');
    const consentMsg = document.getElementById('consent-msg');
    const form       = document.getElementById('signForm');

    if (openTerms && termsModal) {
      openTerms.addEventListener('click', e => {
        e.preventDefault();
        termsModal.style.display = 'flex';
      });
    }
    if (closeTerms)  closeTerms.addEventListener('click', () => termsModal.style.display = 'none');
    if (cancelBtn)  cancelBtn.addEventListener('click', () => termsModal.style.display = 'none');
    if (agreeBtn) {
      agreeBtn.addEventListener('click', () => {
        agreeAll.checked = true;
        consentMsg.style.display = 'none';
        termsModal.style.display = 'none';
      });
    }
    if (termsModal) {
      termsModal.addEventListener('click', e => {
        if (e.target === termsModal) termsModal.style.display = 'none';
      });
    }
  }

  /*** 4) 생년월일 오늘까지 제한 ***/
  function restrictBirthToToday() {
    const b = document.getElementById('birth');
    if (!b) return;
    const d = new Date(), y = d.getFullYear(),
          m = String(d.getMonth()+1).padStart(2,'0'),
          dd= String(d.getDate()).padStart(2,'0');
    b.max = `${y}-${m}-${dd}`;
  }

  /*** 5) 가입 제출 & 환영 모달 ***/
  /*
  function bindSubmit() {
	const formEl      = document.getElementById('signForm');
	const welcomeModal= document.getElementById('welcomeModal');
	const welcomeMsg  = document.getElementById('welcomeMessage');
	const welcomeOk   = document.getElementById('welcomeBtnOk'); // 변경된 ID 사용
    if (!formEl||!welcomeModal||!welcomeMsg||!welcomeOk) return;

    formEl.addEventListener('submit', e=>{
      e.preventDefault();
      const u  = document.getElementById('username').value.trim();
      const p1 = document.getElementById('pw1').value;
      const p2 = document.getElementById('pw2').value;
      const bd = document.getElementById('birth').value;
      if (!/^[0-9A-Za-z]{4,10}$/.test(u))
        return alert('아이디 조건을 확인해주세요.');
      if (!/^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$/.test(p1))
        return alert('비밀번호 형식을 확인해주세요.');
      if (p1!==p2) return alert('비밀번호가 일치하지 않습니다.');
      if (bd>new Date().toISOString().split('T')[0])
        return alert('생년월일을 잘못 입력하셨습니다.');

      const fd = new FormData(formEl);
	  	console.log(formEl.method)
	  for (const key of fd.keys()){
		console.log(key, fd.get(key));
	  }
      fetch(formEl.action, { method: formEl.method, body: fd })
        .then(r=>r.json())
        .then(res=>{
          if (res.success) {
            const typ = document.querySelector('input[name="memType"]:checked')?.value;
            const nm  = document.getElementById('name').value.trim();
            welcomeMsg.textContent = typ==='P'
              ? `${nm}님, 부모님 회원가입을 환영합니다!`
              : `${nm}님, 자녀 회원가입을 축하합니다!`;
            welcomeModal.style.display='flex';
          } else {
            alert('회원가입 실패: '+res.msg);
          }
        })
        .catch(()=>{
          alert('서버 오류가 발생했습니다.');
        });
    });

    welcomeOk.addEventListener('click', ()=>{
      location.href = `${path}/GoLogin.do`;
    });
  }
  */

  // **초기화 호출**
  initSignInFeatures();
  restrictBirthToToday();
  /*bindSubmit();*/
});
