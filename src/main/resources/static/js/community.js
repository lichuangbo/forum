/**
 * 回复问题
 */
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId, 1, commentContent);
}

/**
 * 回复评论
 * @param e 传递当前对象
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var commentContent = $("#input-" + commentId).val();
    comment2target(commentId, 2, commentContent);
}

/**
 * 给目标parent-id评论
 * @param targetId  评论ID
 * @param type  评论类型
 * @param commentContent    评论内容
 */
function comment2target(targetId, type, commentContent) {
    // 前端异常情况处理
    if (!commentContent) {
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({
            "parentId": targetId,
            "content": commentContent,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else if (response.code == 2003) {
                var isAccepted = confirm(response.message);
                if (isAccepted) {
                    // 跳转到新页面登录，登录成功后关闭窗口，跳转回来  问题：跳转回来后，如何刷新一次页面？
                    window.open("https://github.com/login/oauth/authorize?client_id=e10d246abe53066b4519&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                    window.localStorage.setItem("closable", "true");
                }
            } else {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

/**
 * 展开二级评论页，并展示二级评论所有内容
 * @param e 当前对象
 */
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    var collapse = e.getAttribute("data-collapse");
    if (collapse) {// 折叠二级评论页
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {// 展开二级评论页
        var subCommentContainer = $("#comment-" + id);
        // 如果子元素的数量大于1，说明加载过了
        if (subCommentContainer.children().length != 1) {
            comments.addClass("in");
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-size img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "comment-menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));
                    // 将media-left和media-body组装到media div中
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    // 将media div组装到sub-comments div中
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 sub-comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}