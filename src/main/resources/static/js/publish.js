$(function () {
    var editor = editormd("question-editor", {
        width: "100%",
        height: "600px",
        path: "js/lib/",
        placeholder: "感谢使用 星月-MarkDown编辑器 祝您创作愉快!",
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "/file/upload"
    });

    $("#outEdit").click(function () {
        let flag = confirm("确定退出吗？系统可能不会保存您所做的更改。");
        if (flag == true) {
            document.location="/";
        }
    })
});

function wordCheck() {
    var len = $("#title").val().length;
    if (len > 50) {
        $("#title").css("border", "1px solid red")
    } else {
        $("#title").css("border", "")
    }
    $("#word-length").text(len);
}

function selectTag(e) {
    // 拿到标签的值
    var value = e.getAttribute("data-tag");
    var inputValue = $("#tag").val();

    if (inputValue.indexOf(value) == -1) {
        if (inputValue) {
            $("#tag").val(inputValue + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

/*解决模态框-覆盖颠倒问题*/
$(document).on('show.bs.modal', '.modal', function (event) {
    $(this).appendTo($('body'));
}).on('shown.bs.modal', '.modal.in', function (event) {
    setModalsAndBackdropsOrder();
}).on('hidden.bs.modal', '.modal', function (event) {
    setModalsAndBackdropsOrder();
});


function setModalsAndBackdropsOrder() {
    var modalZIndex = 1040;
    $('.modal.in').each(function (index) {
        var $modal = $(this);
        modalZIndex++;
        $modal.css('zIndex', modalZIndex);
        $modal.next('.modal-backdrop.in').addClass('hidden').css('zIndex', modalZIndex - 1);
    });
    $('.modal.in:visible:last').focus().next('.modal-backdrop.in').removeClass('hidden');
}

/*鼠标悬浮*/
function closeItem() {
    var user = $(".user");
    user.removeClass("open");
}

function openItem() {
    var user = $(".user");
    user.addClass("open");
}

/*发布文章*/
function checkLegalArticle() {
    var $title = $("#title").val();
    var $content = $("#content").val();
    var $tag = $("#tag").val();
    if ($title == null || $title.length < 1) {
        alertWindow("文章标题不能为空");
        return false;
    }
    if ($title != null && $title.length > 50) {
        alertWindow("文章标题超过字数限制");
        return false;
    }
    if ($content == null || $content.length < 1) {
        alertWindow("文章内容不能为空");
        return false;
    }
    if ($tag == null || $tag.length < 1) {
        alertWindow("文章标签不能为空");
        return false;
    } else {
        $("#publish-alert").css("display", "none");
    }
    return true;
}

function clearAlert() {
    $("#alert-text").text("");
    $("#publish-alert").css("display", "none");
}

function alertWindow(content) {
    $("#alert-text").text(content);
    $("#publish-alert").css("display", "block");
}

function publishArticle() {
    if (checkLegalArticle()) {
        var $title = $("#title").val();
        var $content = $("#content").val();
        var $tag = $("#tag").val();
        var $id = $("#article-id").val();
        $.post("/publish", {
            "title": $title,
            "content": $content,
            "tag": $tag,
            "id": $id
        }, function (response) {
            if (response.code == 2003 || response.code == 2016) {
                alertWindow(response.message)
            } else {
                document.location.href = "/"
            }
        });
    }
}