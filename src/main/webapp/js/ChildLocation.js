// 이미 JSP에서 전달된 window.contextPath와 window.rawKey를 사용
const contextPath = window.contextPath;  // JSP에서 전달된 contextPath
const rawKey = window.rawKey;  // JSP에서 전달된 서비스 키

// 지도 초기화
kakao.maps.load(async function() {
  // 2-1) map 생성
  const map = new kakao.maps.Map(
    document.getElementById('map'),
    { center: new kakao.maps.LatLng(35.110841, 126.877338), level: 4 }
  );

  // 2-2) Geocoder 인스턴스 생성 (kakao.maps.load() 내부에서 호출)
  const geocoder = new kakao.maps.services.Geocoder();

  // 3) DOM 요소 참조
  const btnChild = document.getElementById('btnChild');
  const btnInst = document.getElementById('btnInst');
  const locateBtn = document.getElementById('locateBtn');
  const skyToggle = document.getElementById('skyToggle');

  // 4) 상태 변수 선언
  let instData = [];
  let instMarkers = [];
  let currentMarker = null;
  let searchMarker = null;
  let mode = 'child';
  let isSkyView = false;

  // 5) 기관 데이터 비동기로 불러오기 (백엔드에서 JSON 받아오기)
  async function loadInstitutions() {
    try {
      const res = await fetch(`${contextPath}/api/institutions`);
      if (!res.ok) {
        console.error('기관 데이터 HTTP 오류:', res.status);
        throw new Error(`HTTP ${res.status}`);
      }
      const json = await res.json();
      const items = (((json.response || {}).body || {}).items || {}).item || [];
      instData = items.map(item => ({
        position: new kakao.maps.LatLng(
          parseFloat(item.lat), parseFloat(item.lot)  // 위도(lat)와 경도(lot)로 위치 생성
        ),
        name: item.centerNm || '이름 없음',  // 기관 이름
        address: item.roadNmAddr || item.lotnoAddr || '주소 없음'  // 주소
      }));

      // 마커 표시 함수 호출
      showInstMarkers(map.getCenter()); // 지도 중심 위치에 마커 표시
    } catch (err) {
      console.error('기관 로드 실패:', err);
      alert('기관 정보를 불러오는 동안 오류가 발생했습니다. 콘솔을 확인해주세요.');
    }
  }

  // 6) 기관 마커 표시 (반경 6km 이내)
  function showInstMarkers(center) {
    instMarkers.forEach(m => m.setMap(null));  // 기존 마커 제거
    instMarkers = [];
    const radius = 6000;  // 6km 반경

    // 각 기관 데이터에 대해 마커 표시
    instData.forEach(d => {
      const dist = kakao.maps.geometry.spherical.computeDistanceBetween(center, d.position);
      if (dist <= radius) {
        const marker = new kakao.maps.Marker({ 
          map, 
          position: d.position  // 마커 위치 설정
        });

        // XSS 방지 및 마커 정보 내용 설정
        const cleanName = DOMPurify.sanitize(d.name);
        const cleanAddress = DOMPurify.sanitize(d.address);
        const infoContent = `<div style="padding:5px;">
                             <strong>${cleanName}</strong><br/>${cleanAddress}
                           </div>`;

        const info = new kakao.maps.InfoWindow({ content: infoContent });
        marker.addListener('mouseover', () => info.open(map, marker));
        marker.addListener('mouseout', () => info.close());
        instMarkers.push(marker);  // 마커 리스트에 추가
      }
    });
  }

  // 7) 아이 위치 표시 (커스텀 마커)
  function showChildLocation() {
    const loc = new kakao.maps.LatLng(35.151048, 126.916022);
    if (currentMarker) currentMarker.setMap(null);
    const img = new kakao.maps.MarkerImage(
      contextPath + '/image/marker.png',
      new kakao.maps.Size(36,36),
      { offset: new kakao.maps.Point(18,36) }
    );
    currentMarker = new kakao.maps.Marker({ map, position: loc, image: img });
    map.setCenter(loc);
  }

  // 8) 주소 검색 (입력값 검증 및 XSS 방지)
  function searchAddress() {
    const raw = document.querySelector('.search-bar input').value.trim();
    if (!raw) return alert('주소를 입력해 주세요.');
    const addr = DOMPurify.sanitize(raw);
    geocoder.addressSearch(addr, (res, status) => {
      if (status === kakao.maps.services.Status.OK) {
        if (searchMarker) searchMarker.setMap(null);
        const loc = new kakao.maps.LatLng(res[0].y, res[0].x);
        searchMarker = new kakao.maps.Marker({ map, position: loc });
        map.setCenter(loc);
      } else {
        alert('검색 결과가 없습니다.');
      }
    });
  }

  // 9) 버튼 이벤트 바인딩
  btnChild.addEventListener('click', () => {
    mode = 'child';
    btnChild.classList.add('active');
    btnInst.classList.remove('active');
  });

  btnInst.addEventListener('click', () => {
    mode = 'inst';
    btnInst.classList.add('active');
    btnChild.classList.remove('active');
    loadInstitutions();
  });

  document.querySelector('.search-icon')
          .addEventListener('click', searchAddress);
  document.querySelector('.search-bar input')
          .addEventListener('keypress', e => e.key === 'Enter' && searchAddress());

  // 10) 위치 확인 버튼 (geolocation 옵션 추가)
  locateBtn.addEventListener('click', () => {
    if (mode === 'child') {
      showChildLocation();
    } else {
      if (!navigator.geolocation) {
        return alert('위치 정보 지원 안 함');
      }
      navigator.geolocation.getCurrentPosition(
        pos => {
          const userLoc = new kakao.maps.LatLng(
            pos.coords.latitude,
            pos.coords.longitude
          );
          map.setCenter(userLoc);

          if (instData.length === 0) loadInstitutions();
          showInstMarkers(userLoc);
        },
        err => {
          console.error('Geolocation 오류:', err);
          alert('현재 위치를 가져오는 데 실패했습니다. 위치 권한을 확인해주세요.');
        },
        { enableHighAccuracy: true, timeout: 5000, maximumAge: 0 }
      );
    }
  });

  // 11) 스카이뷰 토글
  skyToggle.addEventListener('click', () => {
    isSkyView = !isSkyView;
    map.setMapTypeId(
      isSkyView ? kakao.maps.MapTypeId.HYBRID : kakao.maps.MapTypeId.ROADMAP
    );
    skyToggle.classList.toggle('on');
  });
});
