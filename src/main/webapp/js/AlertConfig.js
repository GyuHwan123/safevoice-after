document.addEventListener('DOMContentLoaded', () => {
  const settingBtn = document.querySelector('.setting-btn');
  const settingMenu = document.querySelector('.setting-menu');
  const settingLabel = document.querySelector('.sos-setting-label');
  const backButton = document.querySelector(".back-button");

  // ✅ 반복 주기 저장된 값 복원
  const savedSetting = localStorage.getItem('sosRepeatSetting');
  if (savedSetting) {
    settingLabel.textContent = savedSetting;
	
	console.log("✅ AlertConfig.js loaded");


		document.querySelectorAll('.setting-toggle').forEach(toggle => {
		  console.log("📦 Found toggle:", toggle.id); // ← 각 토글 ID 출력됨

		  toggle.addEventListener('change', () => {
		    console.log("🎯 TOGGLE CHANGED:", toggle.id, "→", toggle.checked);
		  });
		});
  }

  // 반복주기 설정 클릭 시 저장 및 닫기
  settingBtn.addEventListener('click', () => {
    settingMenu.style.display = settingMenu.style.display === 'block' ? 'none' : 'block';
  });

  settingMenu.querySelectorAll('li').forEach(item => {
    item.addEventListener('click', () => {
      const selected = item.textContent;
      settingLabel.textContent = selected;
      localStorage.setItem('sosRepeatSetting', selected);
      settingMenu.style.display = 'none';
    });
  });

  // 외부 클릭 시 설정 메뉴 닫기
  document.addEventListener('click', (e) => {
    if (!settingBtn.contains(e.target) && !settingMenu.contains(e.target)) {
      settingMenu.style.display = 'none';
    }
  });

  // ✅ 소리/진동 토글 상태 복원 + 저장
  document.querySelectorAll('.setting-toggle').forEach(toggle => {
    const saved = localStorage.getItem(toggle.id);
    if (saved !== null) {
      toggle.checked = saved === 'true';
    }


	toggle.addEventListener('change', async () => {
	    localStorage.setItem(toggle.id, toggle.checked);

	    // 🔔 알림 설정 토글 시 푸시 구독 실행 또는 해제
	    if (toggle.id === 'warn-enable' || toggle.id === 'caution-enable') {
	      if (toggle.checked) {
	        console.log("🔔 알림 설정 ON → 푸시 구독 시도");
	        handlePushSubscription();
	      } else {
	        console.log("🚫 알림 설정 OFF → (구독 해제 처리 가능)");
	        const reg = await navigator.serviceWorker.ready;
	        const sub = await reg.pushManager.getSubscription();
	        if (sub) {
	          await sub.unsubscribe();
	          console.log("🧹 푸시 구독 해제 완료");
	          alert("🔕 알림 구독이 해제되었습니다.");
	        } else {
	          console.log("ℹ️ 현재 구독된 항목 없음");
	        }
	      }
	    }
	  });
	});

  // 뒤로가기
  if (backButton) {
    backButton.addEventListener("click", () => history.back());
  }
});

// ✅ 푸시 구독 처리 함수
async function handlePushSubscription() {
  try {
    let permission = Notification.permission;
    if (permission === 'default') {
      permission = await Notification.requestPermission();
    }

    if (permission !== 'granted') {
      alert("❌ 알림 권한이 허용되지 않았습니다.");
      return;
    }

    const reg = await navigator.serviceWorker.ready;
    const subscription = await reg.pushManager.subscribe({
      userVisibleOnly: true,
      applicationServerKey: urlBase64ToUint8Array(vapidPublicKey)
    });

    const memberId = window.memberId;
    await fetch(`${contextPath}/SaveSubscription.do?memberId=${memberId}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(subscription)
    });

    alert("✅ 푸시 구독이 완료되었습니다!");
  } catch (err) {
    console.error("❌ 푸시 구독 실패:", err);
    alert("알림 구독 중 오류가 발생했습니다.");
  }
}

// ✅ VAPID 키 변환 함수
function urlBase64ToUint8Array(base64String) {
  const padding = '='.repeat((4 - base64String.length % 4) % 4);
  const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/');
  const rawData = window.atob(base64);
  return Uint8Array.from([...rawData].map(char => char.charCodeAt(0)));
}

const vapidPublicKey = "BK47ejYRaSe6EDUclmUHMrqznzElZBnfY7CasYcaTQKWpZQe4BBUNNdqmBo2lR4sVd4m2aLuBHvYo44gwxbjogw";

