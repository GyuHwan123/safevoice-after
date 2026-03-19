document.addEventListener("DOMContentLoaded", function () {
  const backButton = document.querySelector(".back-button");

  if (backButton) {
    backButton.addEventListener("click", () => history.back());
  }
});

if (list) {
  list.addEventListener("click", function (e) {
    // 삭제 버튼
    if (e.target && e.target.classList.contains("delete-btn")) {
      const childItem = e.target.closest(".child-item");
      const childId = childItem?.querySelector(".child-name")?.textContent?.trim();

      if (childId && confirm(`${childId} 자녀를 삭제하시겠습니까?`)) {
        fetch("DeleteChild.do", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: `childId=${encodeURIComponent(childId)}`
        })
          .then(res => {
            if (res.ok) {
              childItem.remove(); // UI에서도 제거
            } else {
              alert("삭제 실패!");
            }
          })
          .catch(err => {
            alert("서버 오류: " + err);
          });
      }
    }

    // 수정 버튼
    if (e.target && e.target.classList.contains("edit-btn")) {
      const childItem = e.target.closest(".child-item");
      const nameDiv = childItem.querySelector(".child-name");
      const img = childItem.querySelector(".child-avatar");

      const newName = prompt("자녀 이름을 입력하세요:", nameDiv.textContent);
      if (newName) nameDiv.textContent = newName;

      const fileInput = document.createElement("input");
      fileInput.type = "file";
      fileInput.accept = "image/*";

      fileInput.addEventListener("change", function () {
        const file = fileInput.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = function (event) {
            img.src = event.target.result;
          };
          reader.readAsDataURL(file);
        }
      });

      fileInput.click();
    }
  });

  Sortable.create(list, {
    handle: ".drag-handle",
    animation: 150,
  });
}
