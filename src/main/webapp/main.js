// ✅ 서비스워커 등록만 유지
navigator.serviceWorker.register('/SafeVoice/sw.js', { scope: '/SafeVoice/' })
  .then(() => console.log("✅ 서비스워커 등록 완료"))
  .catch((err) => console.error("❌ 서비스워커 등록 실패:", err));