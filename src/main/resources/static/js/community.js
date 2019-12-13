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
           } else if (response.code == 2003) {
               var isAccepted = confirm(response.message);
               if (isAccepted) {
                   window.open("https://github.com/login/oauth/authorize?client_id=e10d246abe53066b4519&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                   window.localStorage.setItem("closable", "true");
               }
           } else {
               alert(response.message);
           }
       },
       dataType: "json",
       contentType : "application/json;charset=utf-8"
    });
}