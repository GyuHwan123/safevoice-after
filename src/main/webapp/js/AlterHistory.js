document.addEventListener("DOMContentLoaded", function () {
  const alertList = document.querySelector('.alert-list');
  const backButton = document.querySelector('.back-button');

  // ✅ 진입 시 바로 알림 확인 처리
  localStorage.setItem('alertChecked', 'true');

  // 뒤로가기 버튼 기능
  if (backButton) {
    backButton.addEventListener('click', () => history.back());
  }

  // 알림 항목 추가 함수
  function addAlert(date, time, type) {
    if (!alertList) return;

    let colorClass = '';
    if (type.startsWith('S')) colorClass = 'icon-red';
    else if (type.startsWith('위')) colorClass = 'icon-orange';
    else if (type.startsWith('경')) colorClass = 'icon-yellow';

    const article = document.createElement('article');
    article.className = 'alert-item';
    article.innerHTML = `
      <div class="alert-content">
        <div class="alert-date">${date}</div>
        <div class="alert-time">${time}</div>
        <div class="alert-type toggle-details" style="display: none;">${type}</div>
      </div>
      <div class="alert-icon ${colorClass}">!</div>
    `;

    article.addEventListener('click', () => {
      const detail = article.querySelector('.toggle-details');
      if (detail) {
        detail.style.display = detail.style.display === 'none' ? 'block' : 'none';
      }
    });

    alertList.prepend(article);
  }

  let alertData = [];

  fetch('/SafeVoice/GetAlertHistory.do')
    .then(res => res.json())
    .then(data => {
      console.log(data);
      data.forEach(item => {
        if (item.alertTime) {
          const [date, time] = item.alertTime.split(' ');
          item.date = date;
          item.time = time;
        }
      });

      alertData = data;
      if (Array.isArray(alertData)) {
        alertData.forEach(alert => {
          addAlert(
            `${alert.name}`,
            `${alert.date}<br>${alert.time || ''}`,
            `${alert.alertType} - ${alert.alertContext || ''}`
          );
        });
      } else {
        console.warn("⚠️ alertData가 정의되지 않았거나 배열이 아님");
      }
    })
    .catch(error => {
      console.error("오류:", error);
    });
	
	// 진입 시 알람 확인 처리 및 서버 업데이트 
	// 일단은 주석 처리
    //localStorage.setItem('alertChecked', 'true');
	
	fetch('/SafeVoice/MarkAlertsAsRead.do', {
	  method: 'POST'
	})
	.then(res => {
	  if (!res.ok) throw new Error('서버 응답 오류');
	  console.log("✅ 서버에 알림 확인 처리 완료");
	})
	.catch(err => {
	  console.error("❌ 알림 상태 업데이트 실패:", err);
	});

});
