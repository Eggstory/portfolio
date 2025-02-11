document.addEventListener("DOMContentLoaded", () => {

    const modal = document.querySelector('#modal');
    const btn = document.querySelector('.modify-btn');
    const close = document.querySelector('.close');
    const close2 = document.querySelector('.close2');
  
    // 수정하기 모달창 열기
    btn.addEventListener('click', ()=> {
      modal.style.display = 'block';
    } )
    // btn.onclick = function () {
    //   modal.style.display = 'block';
    // };
  
    // 수정하기 모달창 닫기
    close.addEventListener('click', ()=> {
//      modal.style.display = 'none';
//        location.reload();
        history.go(0);
    } )

    close2.addEventListener('click', () => {
      history.go(0);
    })
    // close.onclick = function () {
    //   modal.style.display = 'none';
    // };

    // =========================================================



    // 작성글 or 작성댓글 목록 보이기
    const items = document.querySelectorAll('.list-tab');
    const boardTab = document.querySelector(".board-tab");
    const replyTab = document.querySelector(".reply-tab");
    const listTable = document.querySelectorAll(".list-table");
    const boardTable = document.querySelector(".board-table");
    const replyTable = document.querySelector(".reply-table");
//    const boardNav = document.querySelector(".board-nav");
//    const replyNav = document.querySelector(".reply-nav");


    items.forEach((item) => {
      item.addEventListener('click', () => {

        items.forEach((e) => {
          e.classList.remove('active');
        });
        item.classList.add('active');

        if(boardTab.classList.contains('active')) {
          boardTable.style.display = 'table';
          replyTable.style.display = 'none';
//          boardNav.style.display = 'flex';
//          replyNav.style.display = 'none';
        } else if(replyTab.classList.contains('active')) {
          boardTable.style.display = 'none';
          replyTable.style.display = 'table';
//          boardNav.style.display = 'none';
//          replyNav.style.display = 'flex';
        }

      });
    });


    // =========================================================

    // 개인정보 상태변경
    const switchLabel = document.querySelector(".switch_label");
//    let open = document.querySelector(".open");
    let private = document.querySelector(".private");
    let switchVal = document.querySelector("#switch");
    let isPublic = true;

    if (switchVal.value == "Y") {
      private.textContent = "*공개상태 입니다";
    } else if (switchVal.value == "N") {
      private.textContent = "*비공개상태 입니다";
    }


    switchLabel.addEventListener("click", () => {
      isPublic = !isPublic; // 상태 변경
      console.log(isPublic);
  
      if (switchVal.value == "Y") {
        switchVal.checked = true;
//        open.style.display = 'none';
//        private.style.display = "block";
        switchVal.value = "N";
        private.textContent = "*비공개상태 입니다";
        console.log(switchVal.value);
      } else if (switchVal.value == "N") {
      switchVal.checked = false;
//        open.style.display = 'block';
//        private.style.display = "none";
        switchVal.value = "Y";
        private.textContent = "*공개상태 입니다";
        console.log(switchVal.value);
      }
    })

    // ========================================================

    // 버튼들 기능구현
    const changePic = document.querySelector("#changePic");
    const resetPw = document.querySelector("#resetPw");
    const delAccount = document.querySelector("#deleteAccount");

    let checkDelete = "계정삭제"

    // 계정 삭제
    delAccount.addEventListener('click', () => {
      let memberIdx = parseInt(document.querySelector("#idx").value);
      let queryData = { "memberIdx": memberIdx}

      let delPrompt = prompt(`계정 삭제를 원하신다면, '${checkDelete}'를 입력해주세요`);
      if(delPrompt == checkDelete) {
        fetch("/myInfo/delete", {
          method: "POST",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(queryData)
        })
        .then(response => {
          if(response.ok) {
            alert("삭제 완료");
            location.href = '/';
          } else {
            console.error("요청 실패! 상태 코드:", response.status);
          }
        })
        .catch(error => {
          console.error("오류 발생", error);
          alert("데이터 전송에 실패했습니다.");
        })
      } else {
        alert("문구가 달라 삭제되지 않았습니다.")
      }
    })

    // 비밀번호 변경 사이트로 이동
    resetPw.addEventListener('click', () => {
      location.href = "/changePw"
    });


    // =========================================================

    // 이름 조건
    var pattern_name = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;

    // 데이터 전송
    const editInfo = document.querySelector("#editInfo");
    editInfo.addEventListener('click', function() {
      let visible = document.querySelector("#switch").value;
      let memberIdx = document.querySelector("#idx").value;
      let memberName = document.querySelector("#inputName").value;
      let introduction = document.querySelector("#inputIntroduction").value;

      let requestData =  {
        "memberIdx": memberIdx,
        "memberName": memberName,
        "introduction": introduction,
        "visible": visible
      };

      // http://localhost:8090

      // fetch
      if(pattern_name.test(memberName)) {
        fetch("/myInfo/editAction", {
          method: "POST",
          headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(requestData)
        })
        .then(response => {
          if(!response.ok) {
            throw new Error("오류 발생")
          }
          return response.json();
        })
        .then(data => {
          console.log("서버응답 : ", data);
          alert("데이터가 성공적으로 전송되었습니다.");
          location.reload();
  //        modal.style.display = 'none';
  //        document.querySelector("#displayName").textContent = data.memberName;
  //        document.querySelector("#displayIntroduction").textContent = data.introduction;
        })
        .catch(error => {
          console.error("오류 발생:", error);
          alert("데이터 전송에 실패했습니다.");
        })
      } else {
            alert("이름 조건 : 2이상 10이하 글자수");
            return;
        }


    //axios
//    axios.post("/myInfo/editAction", requestData, {
//        headers: {
//          'Accept': 'application/json',
//          "Content-Type": "application/json"
//        }
//      })
//      .then(response => {
//        console.log("서버응답 : ", response.data);
//        alert("데이터가 성공적으로 전송되었습니다.");
//      })
//      .catch(error => {
//        console.error("오류 발생:", error);
//        alert("데이터 전송에 실패했습니다.");
//      });

    // jQuery AJAX 사용
//      $.ajax({
//        url: "/myInfo/editAction",
//        type: "POST",
//        contentType: "application/json",  // JSON 형식으로 보내기
//        dataType: "json",  // 응답 데이터 형식
//        data: JSON.stringify(requestData),  // 요청 데이터를 JSON으로 변환하여 보내기
//        success: function(response) {
//          console.log("서버응답 : ", response);
//          alert("데이터가 성공적으로 전송되었습니다.");
//        },
//        error: function(xhr, status, error) {
//          console.error("오류 발생:", error);
//          alert("데이터 전송에 실패했습니다.");
//        }
//      });

// =============================================================================================================

      // type 쿼리 string 분기
//    const urlParams = new URLSearchParams(window.location.search);
//    const typeParam = urlParams.get("type");
//
//    if (typeParam === "board") {
//        boardTab.classList.add("active");
//    } else if (typeParam === "reply") {
//        replyTab.classList.add("active");
//    }

// =======================================================================================================

      // nav
//      document.querySelectorAll(.page-nav).forEach((el) => {
//        el.addEventListener("click", (event) => {
//          if(event.target.classList.contains('board-pprev')) {
//
//          }
//        })
//      })


    })
});