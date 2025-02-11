$(function () {

var memberMail = document.getElementById('memberMail').value;
var pw = document.getElementById('memberPw').value;
//let pattern_email_success = 0;
//let pattern_pw_success = 0;

  var pattern_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
//  let pattern_email = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/;
  var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
  let num = pw.search(/[0-9]/g);
  let eng = pw.search(/[a-zA-Z]/gi);
//  let spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
  let spe = pw.search(/[`~!@#$%^&*|\\'";:/?]/g);

  $('.id').change(function () {
    let email = $(this).val();

    pattern_email = new RegExp(pattern_email);
    if (pattern_email.test(email) == false) {
      $('.id-warn').text(function (i, txt) {
        return '정확한 이메일 주소를 입력하세요';
      });
      $('.id-warn').css('height', '40px');
    } else {
      $('.id-warn').text('');
      $('.id-warn').css('height', '18px');
    }

    if ($('.id').val() == '') {
      $('.id-warn').text('');
      $('.id-warn').css('height', '18px');
    }
  });

  $('.pw').change(function () {
    let pw = $(this).val();

    if (pw.length < 8 || pw.length > 16) {
      $('.pw-warn').text(function (i, txt) {
        return '8자리 ~ 16자리 이내로 입력해주세요.';
      });
      $('.pw-warn').css('height', '40px');
    } else if (pw.search(/\s/) != -1) {
      $('.pw-warn').text(function (i, txt) {
        return '비밀번호는 공백 없이 입력해주세요.';
      });
      $('.pw-warn').css('height', '40px');
//    } else if (num < 0 || eng < 0 || spe < 0) {
    } else if (!passwordRegex.test(pw)) {
      $('.pw-warn').text(function (i, txt) {
        return '영문,숫자, 특수문자를 혼합하여 입력해주세요.';
      });
      $('.pw-warn').css('height', '40px');
    } else {
      $('.pw-warn').text('');
      $('.pw-warn').css('height', '18px');
      return '';
    }

    if ($('.pw').val() == '') {
      $('.pw-warn').text('');
      $('.pw-warn').css('height', '18px');
    }
  });

//    if(pattern_email.test(memberMail)) {
//        if (passwordRegex.test(memberPw)) {
//            $.ajax({
//                type : "post",
//                url : "/loginAction",
//                data : {
//                    "memberMail": memberMail,
//                    "memberPw": memberPw
//                },
//                dataType : "text",
//                success : function (data) {
//                    window.location.href = "/";
//                },
//                error : function (data) {
//                    console.log("로그인 실패");
//                }
//            })
//        } else{
//            alert("비밀번호가 틀립니다.");
//            return;
//        }
//    } else {
//        alert("메일이 틀립니다.");
//        return;
//    }

// ========================================================================================================

    const findPw = document.querySelector("#findPw");

    // 비밀번호 재설정
    findPw.addEventListener('click', () => {
      const email = prompt("이메일을 입력하세요.");

      if(!email) {
        alert("이메일을 입력해야 합니다.");
        return;
      }
      if(!pattern_email.test(email)) {
        alert("이메일 양식에 맞춰주세요.");
        return;
      }

      // AJAX 요청을 통해 비밀번호 재발급 요청
      fetch("/resetPw", {  // 백엔드 URL로 변경
          method: "POST",
          headers: {
              "Content-Type": "application/json",
              "Accept": "application/json"
          },
          body: JSON.stringify({ "memberMail": email }) // 입력한 이메일 값을 JSON 형식으로 전송
      })
        .then(response => response.text())
        .then(message => alert(message))
        .catch(error => alert("비밀번호 재설정 요청 실패"));

    })

});
