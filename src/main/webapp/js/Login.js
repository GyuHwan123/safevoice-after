
document.addEventListener('DOMContentLoaded', () => {
  // ─── 다크모드 토글 ───
  const toggleButton = document.getElementById("toggleMode");
  if (toggleButton) {
    if (localStorage.getItem("theme") === "dark") {
      document.body.classList.add("dark-mode");
      toggleButton.textContent = "☀️";
    }
    toggleButton.addEventListener("click", () => {
      const isDark = document.body.classList.toggle("dark-mode");
      toggleButton.textContent = isDark ? "☀️" : "🌙";
      localStorage.setItem("theme", isDark ? "dark" : "light");
    });
  }

  // ─── 로그인 처리 ───
  const loginButton      = document.getElementById("loginBtn");
  const userIdInput      = document.getElementById("userId");
  const userPwInput      = document.getElementById("userPw");
  const saveIdCheckbox   = document.getElementById("saveId");
  const autoLoginCheckbox= document.getElementById("autoLogin");
  const successModal     = document.getElementById("successModal");
  const errorModal       = document.getElementById("errorModal");
  const closeSuccessBtn  = document.getElementById("closeModalBtn");
  const closeErrorBtn    = document.getElementById("closeErrorModalBtn");

  // 저장된 ID/PW, 자동로그인 체크
  const savedId   = localStorage.getItem("savedUserId");
  const savedPw   = localStorage.getItem("savedUserPw");
  const doAuto    = localStorage.getItem("autoLogin") === "true";

  if (savedId) {
    userIdInput.value = savedId;
    saveIdCheckbox.checked = true;
  }
  if (savedPw && doAuto) {
    userPwInput.value = savedPw;
    autoLoginCheckbox.checked = true;
    setTimeout(() => loginButton.click(), 300);
  }

  loginButton.addEventListener("click", () => {
    const id = userIdInput.value.trim();
    const pw = userPwInput.value.trim();
    if (!id || !pw) {
      alert("⚠️ 아이디와 비밀번호를 모두 입력해주세요.");
      return;
    }
    // ID 저장
    if (saveIdCheckbox.checked)   localStorage.setItem("savedUserId", id);
    else                           localStorage.removeItem("savedUserId");
    // 자동로그인 저장
    if (autoLoginCheckbox.checked){
      localStorage.setItem("savedUserPw", pw);
      localStorage.setItem("autoLogin", "true");
    } else {
      localStorage.removeItem("savedUserPw");
      localStorage.setItem("autoLogin", "false");
    }

    // 성공/실패 모달 띄우기 (여기에 서버 통신 로직을 넣어도 OK)
    if (successModal && closeSuccessBtn) {
      successModal.style.display = "flex";
      closeSuccessBtn.addEventListener("click", () => successModal.style.display = "none");
      setTimeout(() => successModal.style.display = "none", 3000);
    } else {
      alert("✅ 로그인 성공!");
    }
  });

  // ─── 소셜 로그인 ───
  const kakaoBtn = document.getElementById("kakao-login-btn");
  if (kakaoBtn) kakaoBtn.addEventListener("click", e => {
    e.preventDefault();
    const clientId    = "YOUR_KAKAO_CLIENT_ID";
    const redirectUri= "YOUR_KAKAO_REDIRECT_URI";
    window.location.href =
      `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}`;
  });

  const naverBtn = document.getElementById("naver-login-btn");
  if (naverBtn) naverBtn.addEventListener("click", e => {
    e.preventDefault();
    const clientId    = "YOUR_NAVER_CLIENT_ID";
    const redirectUri= "YOUR_NAVER_REDIRECT_URI";
    window.location.href =
      `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}`;
  });
});
/**
 * 
 */