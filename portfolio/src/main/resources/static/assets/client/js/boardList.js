
$(function() {
$('#searchInput').keypress(function(event) {
        // 엔터 키의 keyCode는 13입니다.
        if (event.keyCode === 13) {
            let searchText = $(this).val();
            // 여기에 원하는 작업을 수행하는 코드를 추가하세요.
            $.ajax({
                type : "get",
                url : "/board?search="+searchText+"&page=1",
                data : { },
                dataType : "text",
                success : function (data) {
                    window.location.href= "/board?search="+searchText+"&page=1";
                },
                error : function (data) {

                }
            })
            // 예시: 검색어를 서버로 전송하여 검색 결과를 가져오는 등의 작업 수행
        }



    });
});