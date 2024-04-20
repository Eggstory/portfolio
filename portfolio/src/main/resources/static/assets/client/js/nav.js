$(function() {

  $('.open-menu').on('click', function() {
    if($('.nav-bar').css("display") == "none") {
      $('.nav-bar').toggle();
      $('.header-gap').css("gap", "210px");
      $('.nav-bar').css("display", "flex")
    } else {
      $('.nav-bar').toggle();
      $('.header-gap').css("gap", "50px");
    }
  })
})
