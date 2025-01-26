document.addEventListener("DOMContentLoaded", () => {

    const modal = document.querySelector('#modal');
    const btn = document.querySelector('.modify-btn');
    const close = document.querySelector('.close');
  
    // 수정하기 모달창 열기
    btn.addEventListener('click', ()=> {
      modal.style.display = 'block';
    } )
    // btn.onclick = function () {
    //   modal.style.display = 'block';
    // };
  
    // 수정하기 모달창 닫기
    close.addEventListener('click', ()=> {
      modal.style.display = 'none';
    } )
    // close.onclick = function () {
    //   modal.style.display = 'none';
    // };

    // =========================================================

    // 작성글 or 작성댓글 목록 보이기
    const items = document.querySelectorAll('.list-tab');

    items.forEach((item) => {
      item.addEventListener('click', () => {
        items.forEach((e) => {
          e.classList.remove('active');
        });
        item.classList.add('active');
      });
    });

    // =========================================================

    // 개인정보 상태변경
    const switchLabel = document.querySelector(".switch_label");
    const open = document.querySelector(".open");
    const private = document.querySelector(".private");
    let switchVal = document.querySelector("#switch");
    let isPublic = true;

    switchLabel.addEventListener("click", function () {
      isPublic = !isPublic; // 상태 변경
      console.log(isPublic);
  
      if (isPublic) {
        open.style.display = 'none';
        private.style.display = "block";
        switchVal.value = "N";
        console.log(switchVal.value);
      } else {
        open.style.display = 'block';
        private.style.display = "none";
        switchVal.value = "Y";
        console.log(switchVal.value);
      }
    })

    // =========================================================


});