function validatePassword() {

    var memberMail = document.getElementById('memberMail').value;
    var memberPw = document.getElementById('memberPw').value;
    var memberName = document.getElementById('memberName').value;
    var confirmPw = document.getElementById('confirmPw').value;
    var resultDiv = document.getElementById('result');

//    var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
//    var pattern_email = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/;
var pattern_email = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$/;
var pattern_name = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;

if(pattern_name.test(memberName)) {
    if(pattern_email.test(memberMail)) {
        if (passwordRegex.test(memberPw)) {
            if (memberPw === confirmPw) {
                resultDiv.innerHTML = '비밀번호가 일치하며 유효합니다.';
                resultDiv.style.color = 'green';

                $.ajax({
                    type : "post",
                    url : "/joinAction",
                    data : {
                        "memberMail": memberMail,
                        "memberPw": memberPw,
                        "memberName": memberName
                    },
                    dataType : "text",
                    success : function (data) {
                        window.location.href = "/";
                    },
                    error : function (data) {
                        alert("회원가입 실패");
                    }
                })

            } else{
                resultDiv.innerHTML = '비밀번호가 일치하지 않습니다.';
                resultDiv.style.color = 'red';
                return;
            }
        } else{
            resultDiv.innerHTML = '비밀번호는 최소 8자에서 16자까지, 영문자, 숫자 및 특수 문자를 포함해야 합니다.';
            resultDiv.style.color = 'red';
            return;
        }
    } else {
        resultDiv.innerHTML = '이메일 양식에 맞게 작성해주세요.';
        resultDiv.style.color = 'red';
        return;
    }
} else {
    resultDiv.innerHTML = '닉네임 양식에 맞게 작성해주세요.';
    resultDiv.style.color = 'red';
    return;
}


}