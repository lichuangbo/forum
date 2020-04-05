// 头像悬浮
function closeItem() {
    const user = $(".user");
    user.removeClass("open");
}

function openItem() {
    const user = $(".user");
    user.addClass("open");
}

// MarkDown转HTML
$(function () {
    editormd.markdownToHTML("article-view", {});
});

// 当网页向下滑动 200px 出现"返回顶部" 按钮
// 当网页向下滑动 380px 固定推荐问题
window.onscroll = function () {
    scrollFunction();
    fixedRelative();
};

function scrollFunction() {
    if (document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
        $(".ant-back-top").css("display", "block");
    } else {
        $(".ant-back-top").css("display", "none");
    }
}

function fixedRelative() {
    if (document.body.scrollTop > 380 || document.documentElement.scrollTop > 380) {
        $(".fixedRelative").css("position", "fixed").css("top", "66px").css("width", "310px").css("height", "390.8px");
    } else {
        $(".fixedRelative").css("position", "").css("top", "").css("width", "").css("height", "");
    }
}

// 点击按钮，返回顶部
let timer = null;

function topFunction() {
    cancelAnimationFrame(timer);
    //获取当前毫秒数
    let startTime = +new Date();
    //获取当前页面的滚动高度
    let b = document.body.scrollTop || document.documentElement.scrollTop;
    let d = 500;
    let c = b;
    timer = requestAnimationFrame(function func() {
        var t = d - Math.max(0, startTime - (+new Date()) + d);
        document.documentElement.scrollTop = document.body.scrollTop = t * (-c) / d + b;
        timer = requestAnimationFrame(func);
        if (t == d) {
            cancelAnimationFrame(timer);
        }
    });
}

// 评论
$(function () {
    function comment2target(targetId, type, commentContent) {
        if ($("#user-img").attr("src") === "/images/default-avatar.png") {
            alertWindow("当前操作需登录");
            return;
        }
        if (commentContent == null || commentContent.length === 0) {
            alertWindow("评论内容不能为空");
            return;
        }

        $.post("/comment", {
            "parentId": targetId,
            "content": commentContent,
            "type": type
        }, function (response) {
            if (response.code == 200) {
                $("#user-content").val("");
                $("#totalCommentCount1").text(response.data);
                $("#totalCommentCount2").text(response.data);
                loadComment();
            } else {
                alertWindow(response.message);
            }
        });
    }

    function loadComment() {
        const articleId = $("#article-id").val();
        // load相当于post请求
        $("#comment-list").load(
            "/getComment",
            {"articleId": articleId},
            function () {
            }
        );
    }

    $('#comment2Article').click(function () {
        const articleId = $("#article-id").val();
        const userContent = $("#user-content").val();
        comment2target(articleId, 1, userContent);
    });

    // 点赞
    $('#thumbUpArticle').click(function () {
        const articleId = $("#article-id").val();
        $.post("/thumbUpArticle", {
            "targetId": articleId
        }, function (response) {
            if (response.code == 200) {
                $("#thumb-up-article-count").text(response.data).toggleClass("article-like-num");
                $("#thumbUpArticle").toggleClass("article-like");
            } else {
                alertWindow(response.message);
            }
        });
    })

    // 收藏
    $("#favoriteArticle").click(function () {
        const articleId = $("#article-id").val();
        $.post("/favoriteArticle", {
            "articleId": articleId
        }, function (response) {
            if (response.code == 200) {
                $("#favoriteArticle").toggleClass("article-favorite");
            } else {
                alertWindow(response.message);
            }
        });
    })

    // 滚动到评论区
    $("#scroll2Comment").click(function () {
        $('#user-img')[0].scrollIntoView(true);
    })

    // 关注
    $("#follow-writer").click(function () {
        const articleId = $("#article-id").val();
        $.post("/followWriter", {
            "articleId": articleId
        }, function (response) {
            if (response.code == 200) {
                $("#follow-writer").toggleClass("followed");
                $("#follow-text").text(response.data);
            } else {
                alertWindow(response.message);
            }
        });
    })
});

function followClick() {
    alertWindow("当前操作需登录");
}

function alertWindow(content) {
    $("#alert-text").text(content);
    $("#article-alert").css("display", "block");
    setTimeout(function () {
        $("#article-alert").hide();
    }, 1500);
}

function showInput(e) {
    const commentId = e.getAttribute("comment-id");
    const inputSelector = "#collapse-" + commentId + " textarea";
    const respNickname = e.getAttribute("resp-nickname");
    const placeholder = "回复" + respNickname + "...";
    $(inputSelector).attr('placeholder', placeholder);
}

// 评论点赞
function thumbUpComment(e) {
    const commentId = e.getAttribute("comment-id");
    $.post("/thumbUpComment", {
        "targetId": commentId
    }, function (response) {
        if (response.code == 200) {
            $("#thumb-up-comment-like-" + commentId).text(response.data);
            e.classList.toggle("comment-like");
        } else {
            alertWindow(response.message);
        }
    });
}