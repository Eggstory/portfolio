$(function() {

  $('.open-menu').on('click', function() {
    if($('.nav-bar').css("display") == "none") {
      $('.nav-bar').toggle();
      $('.nav-bar').css("display", "flex")
    } else {
      $('.nav-bar').toggle();
    }
  })
})