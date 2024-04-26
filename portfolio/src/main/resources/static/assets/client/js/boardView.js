


$(function() {

    // 이전 페이지의 URL을 가져옴
//    let previousUrl = document.referrer;
//    const url = previousUrl.split('?');
//    const detail = url[1];

    $('.back').on("click", function() {

    window.location.href = "/board"

//    if (previousUrl.includes("?page=")) {
//        // 페이지 번호 파라미터를 가져옴
//        let pageNumber = previousUrl.split("?")[1];
//
//        // 새로운 URL을 생성하여 해당 페이지로 이동
//        window.location.href = "/board?" + pageNumber;
//    }


//        let currentUrl = window.location.href;
//        let referrerUrl = document.referrer;
//
//            // currentUrl과 referrerUrl이 다를 때까지 실행
//            let interval = setInterval(function() {
//                if (currentUrl !== referrerUrl) {
//                    clearInterval(interval); // currentUrl과 referrerUrl이 다를 경우 setInterval 종료
//                } else {
//                    history.back(); // currentUrl과 referrerUrl이 같은 경우 이전 페이지로 이동
//                    currentUrl = window.location.href;
//                    referrerUrl = document.referrer;
//                }
//            }, 50);
//            history.back(); // 마지막으로 currentUrl과 referrerUrl이 다른 경우 페이지 이동
        });

// 그냥 컨트롤러에서 되게 함
//    $('#replySubmit').on("click", function() {
//
////        let replyComment = $("#replyComment").val();
////        let boardIdx = $("#boardIdx").val();
////        let memberIdx = $("#memberIdx").val();
////        let parentIdx = $("#parentIdx").val();
//        let queryString = $("#comment-write").serialize();
//
//
//// 요즘 success, error, complete 보다 done, fail 를  쓰는 추세라 함
//        $.ajax({
//            type : "post",
//            url : "/board/replyAction",
//            data : queryString,
//            dataType : "json",
//            success : function (data) {
//            // 작동안됨...
////                window.location.reload();
//            },
//            error : function (data) {
//
//            }
//        })
//        .done(function() {
//            //왜인지 작동안함 -_-
//            //window.location.reload();
//            //$('#replyContents').val('') //댓글 등록시 댓글 등록창 초기화
//            //$('#comment-content').replaceWith(data);
//
//        })
//        .fail(function() {
//
//        })
//        .always(function() {
//
//        });
//    })

    $('.comment-content').each(function(index) {
        $(this).find('span[class="openReplyForm"]').attr('class','openReplyForm_'+index)
        $(this).find('input[id="boardIdx"]').attr('id','boardIdx_'+index)
        $(this).find('input[id="memberIdx"]').attr('id','memberIdx_'+index)
        $(this).find('input[id="parentIdx"]').attr('id','parentIdx_'+index)
        $(this).find('input[id="replyIdx"]').attr('id','replyIdx_'+index)
        $(this).find('textarea[id="replyComment"]').attr('id','replyComment_'+index)
        $(this).find('button[id="replySubmit"]').attr('id','replySubmit_'+index)
        $(this).find('div[class="hidden-replyForm"]').attr('class','hidden-replyForm_'+index)
        $(this).find('span[class="deleteReply"]').attr('class','deleteReply_'+index)
        $(this).find('span[class="updateReply"]').attr('class','updateReply_'+index)
        $(this).find('span[class="restoreReply"]').attr('class','restoreReply_'+index)
        $(this).find('div[class="updateReplyForm"]').attr('class','updateReplyForm_'+index)


        $('.openReplyForm_'+index).on('click', function() {
            if($('.hidden-replyForm_'+index).css("display") == "block") {
                $('.hidden-replyForm_'+index).toggle();
                $('.hidden-replyForm_'+index).css("display", "none")
            } else {
                $('.hidden-replyForm_'+index).toggle();
            }
        })

        $('.deleteReply_'+index).on("click", function() {
            if (confirm("정말 삭제하시겠습니까??") == true){    //확인
                let replyIdx = $("#replyIdx_"+index).val();

                $.ajax({
                    type : "post",
                    url : "/board/replyDelete",
                    data : {"replyIdx":replyIdx},
                    dataType : "text",
                    success : function (data) {
                        window.location.reload();
                    }
                })
            }else{   //취소
                return false;
            }
        })

        $('.restoreReply_'+index).on("click", function() {
            if (confirm("복구하시겠습니까?") == true){    //확인
                let replyIdx = $("#replyIdx_"+index).val();

                $.ajax({
                    type : "post",
                    url : "/board/replyRestore",
                    data : {"replyIdx":replyIdx},
                    dataType : "text",
                    success : function (data) {
                        window.location.reload();
                    }
                })
            }else{   //취소
                return false;
            }
        })

        $('.updateReply_'+index).on('click', function() {

            let replyComment = $(".updateReplyForm_"+index).text();
            let textarea = $('<textarea class="editTextarea" style="width: 850px;"></textarea>').text(replyComment);
            let button = $('<button class="editReply" style="float: right;">수정</button>');
            let div = $('<div class="changeForm"></div>').append(textarea, button);
            $('.updateReplyForm_'+index).replaceWith(div)


            $('.editReply').on('click', function() {

                let replyIdx = $("#replyIdx_"+index).val();
                let replyComment = $(".editTextarea").val();
                let boardIdx = $("#boardIdx_"+index).val();
                let memberIdx = $("#memberIdx_"+index).val();

                $.ajax({
                    type : "post",
                    url : "/board/replyUpdate",
                    data : {
                        replyIdx: replyIdx,
                        replyComment: replyComment,
                        boardIdx: boardIdx,
                        memberIdx: memberIdx
                    },
                    dataType : "text",
                    success : function (data) {
//                        window.location.reload();
                        let textareaClass = $('.editTextarea');
                        let div = $("<div></div>").addClass("updateReplyForm_"+index);
                        div.text(textareaClass.val());

                        $('.changeForm').replaceWith(div)
                        $('.editTextarea').remove();
                        $('.editReply').remove();
                    },
                    error : function (data) {

                    }
                })
            })
        })
    })

    $('.padding-left40').each(function(index) {
        $(this).find('span[class="openReReplyForm"]').attr('class','openReReplyForm_'+index)
        $(this).find('div[class="hidden-reReplyForm"]').attr('class','hidden-reReplyForm_'+index)
        $(this).find('span[class="deleteReReply"]').attr('class','deleteReReply_'+index)
        $(this).find('span[class="updateReReply"]').attr('class','updateReReply_'+index)
        $(this).find('span[class="restoreReReply"]').attr('class','restoreReReply_'+index)
        $(this).find('div[class="updateReReplyForm"]').attr('class','updateReReplyForm_'+index)
        $(this).find('input[id="reReplyIdx"]').attr('id','reReplyIdx_'+index)
        $(this).find('input[id="reMemberIdx"]').attr('id','reMemberIdx_'+index)
        $(this).find('input[id="reParentIdx"]').attr('id','reParentIdx_'+index)
        $(this).find('input[id="reBoardIdx"]').attr('id','reBoardIdx_'+index)




        $('.openReReplyForm_'+index).on('click', function() {
            if($('.hidden-reReplyForm_'+index).css("display") == "block") {
                $('.hidden-reReplyForm_'+index).toggle();
                $('.hidden-reReplyForm_'+index).css("display", "none")
            } else {
                $('.hidden-reReplyForm_'+index).toggle();
            }
        })

        $('.deleteReReply_'+index).on("click", function() {

            if (confirm("정말 삭제하시겠습니까??") == true){    //확인
                let replyIdx = $("#reReplyIdx_"+index).val();

                //JSON.stringify()
                $.ajax({
                    type : "post",
                    url : "/board/replyDelete",
                    data : {"replyIdx":replyIdx},
                    dataType : "text",
                    success : function (data) {
                        window.location.reload();
                    }
                })
            }else{   //취소
                return false;
            }
        })


        $('.restoreReReply_'+index).on("click", function() {
            if (confirm("복구하시겠습니까?") == true){    //확인
                let replyIdx = $("#reReplyIdx_"+index).val();

                $.ajax({
                    type : "post",
                    url : "/board/replyRestore",
                    data : {"replyIdx":replyIdx},
                    dataType : "text",
                    success : function (data) {
                        window.location.reload();
                    }
                })
            }else{   //취소
                return false;
            }
        })

        $('.updateReReply_'+index).on('click', function() {

            let replyComment = $(".updateReReplyForm_"+index).text();
            let textarea = $('<textarea class="editTextarea" style="width: 800px;"></textarea>').text(replyComment);
            let button = $('<button class="editReply" style="float: right;">수정</button>');
            let div = $('<div class="changeForm"></div>').append(textarea, button);
            $('.updateReReplyForm_'+index).replaceWith(div)


            $('.editReply').on('click', function() {

                let replyIdx = $("#reReplyIdx_"+index).val();
                let replyComment = $(".editTextarea").val();
                let boardIdx = $("#reBoardIdx_"+index).val();
                let memberIdx = $("#reMemberIdx_"+index).val();
                let parentIdx = $("#reParentIdx_"+index).val();

                $.ajax({
                    type : "post",
                    url : "/board/replyUpdate",
                    data : {
                        replyIdx: replyIdx,
                        replyComment: replyComment,
                        boardIdx: boardIdx,
                        memberIdx: memberIdx,
                        parentIdx: parentIdx
                    },
                    dataType : "text",
                    success : function (data) {
                        let textareaClass = $('.editTextarea');
                        let div = $("<div></div>").addClass("updateReReplyForm_"+index);
                        div.text(textareaClass.val());

                        $('.changeForm').replaceWith(div)
                        $('.editTextarea').remove();
                        $('.editReply').remove();
                    },
                    error : function (data) {

                    }
                })
            })
        })
    })

    $('.update').on("click", function() {
        let boardIdx = $('.boardIdx').val();

        $.ajax({
            type : "get",
            url : "/board/edit?boardIdx=" + boardIdx,
            data : { },
            dataType : "text",
            success : function (data) {
                window.location.href = "/board/edit?boardIdx=" + boardIdx;
            }
        })

    })

    $('.delete').on("click", function() {
        if (confirm("정말 삭제하시겠습니까??") == true){
            let boardIdx = $('.boardIdx').val();

            $.ajax({
                type : "post",
                url : "/board/delete",
                data : { boardIdx: boardIdx },
                dataType : "text",
                success : function (data) {
                    window.location.href= "/board";
                }
            })
        }
    })
})




//function deleteReply(id) {
//
//    let replyIdx = id;
//
//    $.ajax({
//        url : "/board/replyDelete",
//        type : 'delete',
//        data : replyIdx,
//        dataType : 'json',
//        success : function (response) {
//            alert('삭제되었습니다.');
//            window.location.reload();
//        },
//        error : function (request, status, error) {
//            console.log(error)
//        }
//    })
//}