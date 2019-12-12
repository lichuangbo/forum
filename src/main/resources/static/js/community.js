function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
       type: "POST",
       url: "/comment",
       data: JSON.stringify({
           "parentId": questionId,
           "content": commentContent,
           "type": 1
       }),
       success: function (response) {
           if (response.code == 200) {
               // 成功响应，隐藏输入栏
               $("#comment-section").hide();
           } else {
               alert(response.message);
           }
       },
       dataType: "json",
       contentType : "application/json;charset=utf-8"
    });
}