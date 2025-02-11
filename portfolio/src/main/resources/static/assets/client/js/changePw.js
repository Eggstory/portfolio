
document.addEventListener("DOMContentLoaded", () => {

  const changePw = document.querySelector("#changePw");
  const resultDiv = document.querySelector("#result");

  var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;


  // 비밀번호 재설정
  changePw.addEventListener('click', () => {

    const pw = document.querySelector("#pw").value;
    const newPw1 = document.querySelector("#newPw1").value;
    const newPw2 = document.querySelector("#newPw2").value;

//          if(!email) {
//            alert("이메일을 입력해야 합니다.");
//            return;
//          }

    let dataForPw = {
      "memberPw": pw,
      "newPw1": newPw1,
      "newPw2": newPw2
    }

    if(passwordRegex.test(pw)) {
      if(passwordRegex.test(newPw1)) {
        if(passwordRegex.test(newPw2)) {
          if( newPw1 === newPw2) {
            resultDiv.innerHTML = '비밀번호가 일치하며 유효합니다.';
            resultDiv.style.color = 'green';

            // AJAX 요청을 통해 비밀번호 재발급 요청
            fetch("/myInfo/changePw", {  // 백엔드 URL로 변경
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
              },
              body: JSON.stringify(dataForPw) // 입력한 이메일 값을 JSON 형식으로 전송
            })
            .then(response => response.text())
            .then(message => {
                alert(message);
                location.href="/"
             })
            .catch(error => alert("비밀번호 변경 실패"));
          } else{
              resultDiv.innerHTML = '비밀번호가 일치하지 않습니다.';
              resultDiv.style.color = 'red';
              return;
          }
        } else{
            resultDiv.innerHTML = '비밀번호 소,대문자,숫자,특수문자를 최소 1개씩 포함해주세요.';
            resultDiv.style.color = 'red';
            return;
        }
      } else {
          resultDiv.innerHTML = '비밀번호 소,대문자,숫자,특수문자를 최소 1개씩 포함해주세요.';
          resultDiv.style.color = 'red';
          return;
      }
    } else {
        resultDiv.innerHTML = '비밀번호 소,대문자,숫자,특수문자를 최소 1개씩 포함해주세요.';
        resultDiv.style.color = 'red';
        return;
    }
  })
});