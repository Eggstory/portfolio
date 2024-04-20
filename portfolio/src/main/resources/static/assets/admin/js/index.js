function deleteValue() {
  var token = $("meta[name='_csrf']").attr('content');
  var header = $("meta[name='_csrf_header']").attr('content');
  var name = $('#userName').val();

  let memberIdx = '';
  $("input[name='RowCheck']:checked").each(function () {
    memberIdx = memberIdx + $(this).val() + ',';
  });
  if (memberIdx == '') {
    alert('삭제할 대상을 선택하세요.');
    return false;
  }
  memberIdx = memberIdx.replace('selectAll,', '');

  if (
    $("input[name='RowCheck']:checked")[
      $("input[name='RowCheck']:checked").length - 1
    ].className == 'member'
  ) {
    // 끝
    $.ajax({
      url: '/admin/member/delete',
      type: 'POST',
      traditional: true,
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      data: {
        memberIdx: memberIdx,
      },
      success: function (jdata) {
        if ((jdata = 1)) {
          alert('삭제 성공');
          location.replace('/admin/list/member');
        } else {
          alert('삭제 실패');
        }
      },
    });
  }

  //  if (
  //    $("input[name='RowCheck']:checked")[
  //      $("input[name='RowCheck']:checked").length - 1
  //    ].className == 'product'
  //  ) {
  //    // 끝
  //    $.ajax({
  //      url: '/admin/products/delete',
  //      type: 'POST',
  //      traditional: true,
  //      beforeSend: function (xhr) {
  //        xhr.setRequestHeader(header, token);
  //      },
  //      data: {
  //        reviewNo: reviewNo,
  //      },
  //      success: function (jdata) {
  //        if ((jdata = 1)) {
  //          alert('삭제 성공');
  //          location.replace('/admin/list/product');
  //        } else {
  //          alert('삭제 실패');
  //        }
  //      },
  //    });
  //  }
  //
  //  if (
  //    $("input[name='RowCheck']:checked")[
  //      $("input[name='RowCheck']:checked").length - 1
  //    ].className == 'notice'
  //  ) {
  //    $.ajax({
  //      url: '/admin/notices/delete',
  //      type: 'POST',
  //      traditional: true,
  //      beforeSend: function (xhr) {
  //        xhr.setRequestHeader(header, token);
  //      },
  //      data: {
  //        reviewNo: reviewNo,
  //      },
  //      success: function (jdata) {
  //        if ((jdata = 1)) {
  //          alert('삭제 성공');
  //          location.replace('/admin/list/notice');
  //        } else {
  //          alert('삭제 실패');
  //        }
  //      },
  //    });
  //  }
  //  if (
  //    $("input[name='RowCheck']:checked")[
  //      $("input[name='RowCheck']:checked").length - 1
  //    ].className == 'qna'
  //  ) {
  //    $.ajax({
  //      url: '/admin/qnas/delete',
  //      type: 'POST',
  //      traditional: true,
  //      beforeSend: function (xhr) {
  //        xhr.setRequestHeader(header, token);
  //      },
  //      data: {
  //        reviewNo: reviewNo,
  //      },
  //      success: function (jdata) {
  //        if ((jdata = 1)) {
  //          alert('삭제 성공');
  //          location.replace('/admin/list/QnA');
  //        } else {
  //          alert('삭제 실패');
  //        }
  //      },
  //    });
  //  }
  //
  //  if (
  //    $("input[name='RowCheck']:checked")[
  //      $("input[name='RowCheck']:checked").length - 1
  //    ].className == 'review'
  //  ) {
  //    // 끝
  //    $.ajax({
  //      url: '/admin/reviews/delete',
  //      type: 'POST',
  //      traditional: true,
  //      beforeSend: function (xhr) {
  //        xhr.setRequestHeader(header, token);
  //      },
  //      data: {
  //        reviewNo: reviewNo,
  //      },
  //      success: function (jdata) {
  //        if ((jdata = 1)) {
  //          alert('삭제 성공');
  //          location.replace('/admin/list/review');
  //        } else {
  //          alert('삭제 실패');
  //        }
  //      },
  //    });
  //  }
}

  function selectAll(selectAll) {
    const checkboxes = document.getElementsByName('RowCheck');

    checkboxes.forEach((checkbox) => {
      checkbox.checked = selectAll.checked;
    });
  }

$(function () {

//    $('input[name=allCheck]').on('click', function() {
//        console.log("되냐안되냐")
//        let checked = $(this).is(':checked');
//        if(checked) {
//            $('input[name=RowCheck]').prop('checked', true);
//        } else {
//            $('input[name=RowCheck]').prop('checked', false);
//        }
//    })

  $('#searchInput').keypress(function (event) {
    // 엔터 키의 keyCode는 13입니다.
    if (event.keyCode === 13) {
      let searchText = $(this).val();
      // 여기에 원하는 작업을 수행하는 코드를 추가하세요.
      $.ajax({
        type: 'get',
        url: '/admin/list/board?search=' + searchText + '&page=1',
        data: {},
        dataType: 'text',
        success: function (data) {
          window.location.href =
            '/admin/list/board?search=' + searchText + '&page=1';
        },
        error: function (data) {},
      });
      // 예시: 검색어를 서버로 전송하여 검색 결과를 가져오는 등의 작업 수행
    }
  });
});
