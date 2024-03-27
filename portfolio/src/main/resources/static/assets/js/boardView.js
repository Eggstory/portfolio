$(function() {
    $('#replySubmit').on("click", function() {
//        let replyComment = $("#replyComment").val();
//        let boardIdx = $("#boardIdx").val();
//        let memberIdx = $("#memberIdx").val();
//        let replyIdx = $("#replyIdx").val();
        let queryString = $("#comment-write").serialize() ;

        $.ajax({
            type : "post",
            url : "/board/replyAction",
            data : queryString,
            dataType : "json",
            success : function (data) {
                $('#replyContents').val('') //댓글 등록시 댓글 등록창 초기화
                console.log("테스트")
                $('#comment-content').replaceWith(data);
            }
        })
    })

    $('.comment-content').each(function(index) {
        $(this).find('span[class="openReplyForm"]').attr('class','openReplyForm_'+index)
        $(this).find('input[id="boardIdx"]').attr('id','boardIdx_'+index)
        $(this).find('input[id="memberIdx"]').attr('id','memberIdx_'+index)
        $(this).find('input[id="parentIdx"]').attr('id','parentIdx_'+index)
        $(this).find('textarea[id="replyComment"]').attr('id','replyComment_'+index)
        $(this).find('button[id="replySubmit"]').attr('id','replySubmit_'+index)
        $(this).find('div[class="hidden-replyForm"]').attr('class','hidden-replyForm_'+index)

            $('.openReplyForm_'+index).on('click', function() {
                if($('.hidden-replyForm_'+index).css("display") == "block") {
                    $('.hidden-replyForm_'+index).toggle();
                    $('.hidden-replyForm_'+index).css("display", "none")
                } else {
                    $('.hidden-replyForm_'+index).toggle();
                }
            })
    })

    $('.padding-left40').each(function(index) {
            $(this).find('span[class="openReReplyForm"]').attr('class','openReReplyForm_'+index)
            $(this).find('div[class="hidden-reReplyForm"]').attr('class','hidden-reReplyForm_'+index)

                $('.openReReplyForm_'+index).on('click', function() {
                    if($('.hidden-reReplyForm_'+index).css("display") == "block") {
                        $('.hidden-reReplyForm_'+index).toggle();
                        $('.hidden-reReplyForm_'+index).css("display", "none")
                    } else {
                        $('.hidden-reReplyForm_'+index).toggle();
                    }
                })
        })


})