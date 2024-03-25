$('#replySubmit').on("click", function() {
    let replyComment = $("#replyComment").val();
    $.ajax({
        type : "post",
        url : "/board/replyAction",
        data : {
            "replyComment" : replyComment,

        }
    })
})