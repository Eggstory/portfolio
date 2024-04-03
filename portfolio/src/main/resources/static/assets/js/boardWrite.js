//$(document).ready(function() {
//  $('#summernote').summernote();
//});


function sendFile(file, el) {
  var form_data = new FormData();
  form_data.append('file', file);
  $.ajax({
    data: form_data,
    type : "post",
    url: 'summer_image',
    cache :false,
    contentType : false,
    enctype : 'multipart/form-data',
    processData : false,
    success : function(img_name) {
      $(el).summernote('editor.insertImage', img_name);
    }
  });
}

$(function() {
  $('#boardContent').summernote({
       placeholder: '최대 500자 작성 가능합니다.',
          height: 300,
          width : 800,
          lang: 'ko-KR',
          callbacks: {
            onImageUpload: function(files, editor, welEditable) {
              for(var i = files.length -1; i>=0; i--) {
                sendFile(files[i], this);
              }
            }
          }
   });

    let imageUrls = []; // 이미지 URL을 저장할 배열

      // 에디터 내용이 변경될 때마다 이미지 추출
      $('#boardContent').on('summernote.change', function() {
        let images = $("p img");

        // 추출한 이미지 태그를 순회하면서 각 태그를 처리하거나 출력
        images.each(function(index, element){
          let imageUrl = $(element).attr("src");
          imageUrls.push(imageUrl); // 배열에 이미지 URL 추가
        });

        // 이미지 URL 배열을 하나의 문자열로 결합하여 hidden input에 할당
        $("#hiddenInput").val(imageUrls.join(','));
      });
});