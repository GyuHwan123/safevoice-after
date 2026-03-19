document.addEventListener("DOMContentLoaded", function () {
  const editBtn = document.getElementById("edit-user");
  const cancelBtn = document.getElementById("cancel-edit");
  const nameEl = document.getElementById("user-name");
  const emailEl = document.getElementById("user-email");
  const photoEl = document.getElementById("user-photo");
  const fileInput = document.getElementById("file-input");
  const backButton = document.querySelector(".back-button");
  const headerPhotoEl = document.querySelector(".profile-img"); // 작은 프로필

  let originalName = nameEl.textContent;
  let originalEmail = emailEl.textContent;
  let originalPhoto = photoEl.src;
  let originalHeaderPhoto = headerPhotoEl?.src || "";

  editBtn.addEventListener("click", () => {
    // 🔄 현재 상태 저장
    originalName = nameEl.textContent;
    originalEmail = emailEl.textContent;
    originalPhoto = photoEl.src;
    originalHeaderPhoto = headerPhotoEl?.src || "";

    const newName = prompt("이름을 입력하세요:", nameEl.textContent);
    if (newName === null) return;

    const newEmail = prompt("이메일을 입력하세요:", emailEl.textContent);
    if (newEmail === null) return;

    nameEl.textContent = newName;
    emailEl.textContent = newEmail;

    fileInput.click();
  });

  fileInput.addEventListener("change", () => {
    const file = fileInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const newImgSrc = e.target.result;
        photoEl.src = newImgSrc;

        // ✅ 작은 프로필도 변경
        if (headerPhotoEl) {
          headerPhotoEl.src = newImgSrc;
        }
      };
      reader.readAsDataURL(file);
    }
  });

 

  if (backButton) {
    backButton.addEventListener("click", () => history.back());
  }
});
