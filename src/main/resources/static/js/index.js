// 当网页向下滑动 180px 固定推荐作者
window.onscroll = function () {
    fixedRecommend();
};

function fixedRecommend() {
    if (document.body.scrollTop > 180 || document.documentElement.scrollTop > 180) {
        $(".recommended-authors").css("position", "fixed").css("top", "66px").css("width", "310px").css("height", "390.8px");
    } else {
        $(".recommended-authors").css("position", "").css("top", "").css("width", "").css("height", "");
    }
}

// 图标旋转
let rotation = 0;
jQuery.fn.rotate = function (degrees) {
    $(this).css({'transform': 'rotate(' + degrees + 'deg)'});
    return $(this);
};

function loadRecommend() {
    rotation += 360;
    $(".refresh-icon").rotate(rotation);
    // load相当于post请求
    $("#recommend-list").load(
        "/getRecommend",
        {},
        function () {
        }
    );
}

function followRecommend(e) {
    const userId = e.getAttribute("recommend-id");
    $.post("/followRecommend", {
        "recommendUserId": userId
    }, function (response) {
        if (response.code == 200) {
            $("#follow-style-" + userId).toggleClass("followed");
            $("#follow-icon-" + userId).toggleClass("glyphicon-ok");
            $("#follow-text-" + userId).text(response.data);
        } else {
            alertWindow(response.message);
        }
    });
}

function alertWindow(content) {
    $("#index-text").text(content);
    $("#index-alert").css("display", "block");
    setTimeout(function () {
        $("#index-alert").hide();
    }, 1500);
}