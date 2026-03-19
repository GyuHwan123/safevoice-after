const sosBtn = document.querySelector('.sos-button');

sosBtn.addEventListener('click', () => {
  sosBtn.classList.toggle('sos-flashing');

  setTimeout(() => {
    sosBtn.classList.remove('sos-flashing');
  }, 5000); // 5000ms = 5초
  
  // 로그인한 자녀 ID 가져오기
  const memberId = window.loginMemberId;

  fetch("SendSosAlert.do", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: `memberId=${encodeURIComponent(memberId)}`
  })
  .then(res => {
    if (res.ok) {
      alert("🚨 SOS 알림이 전송되었습니다.");
    } else {
      alert("❌ SOS 전송 실패");
    }
  });
});