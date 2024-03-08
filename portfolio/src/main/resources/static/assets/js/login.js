$(function () {
  $('.id').change(function () {
    let email = $(this).val();
    let pattern_email = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/;
    pattern_email = new RegExp(pattern_email);
    if (pattern_email.test(email) == false) {
      $('.id-warn').text(function (i, txt) {
        return '정확한 이메일 주소를 입력하세요';
      });
      $('.id-warn').css('height', '40px');
    }

    if ($('.id').val() == '') {
      $('.id-warn').text('');
      $('.id-warn').css('height', '18px');
    }
  });
  

  $('.pw').change(function () {
    let pw = $(this).val();
    let num = pw.search(/[0-9]/g);
    let eng = pw.search(/[a-z]/gi);
    let spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);

    if (pw.length < 8 || pw.length > 20) {
      $('.pw-warn').text(function (i, txt) {
        return '8자리 ~ 20자리 이내로 입력해주세요.';
      });
    } else if (pw.search(/\s/) != -1) {
      $('.pw-warn').text(function (i, txt) {
        return '비밀번호는 공백 없이 입력해주세요.';
      });
    } else if (num < 0 || eng < 0 || spe < 0) {
      $('.pw-warn').text(function (i, txt) {
        return '영문,숫자, 특수문자를 혼합하여 입력해주세요.';
      });
    } else {
      return true;
    }
    $('.pw-warn').css('height', '40px');


    if($('.pw').val() == "") {
      $('.pw-warn').text("")
      $('.pw-warn').css("height", '18px')
    }

  });
});
