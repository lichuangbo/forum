function closeItem() {
    var user = $(".user");
    user.removeClass("open");
}

function openItem() {
    var user = $(".user");
    user.addClass("open");
}

function followUser(e) {
    const userPageId = $("#user-id").val();
    const userId = e.getAttribute("user-id");
    $.post("/followUser", {
        "userPageId": userPageId,
        "userId": userId
    }, function (response) {
        if (response.code == 200) {
            $("#follow-text-" + userId).text(response.data.message);
            $("#followedCount").text(response.data.followedCount);
            $("#followerCount").text(response.data.followerCount);
            e.classList.toggle("active");
        } else {
            alertWindow(response.message);
        }
    });
}

function followerUser(e) {
    const userPageId = $("#user-id").val();
    const userId = e.getAttribute("follower-user-id");
    $.post("/followUser", {
        "userPageId": userPageId,
        "userId": userId
    }, function (response) {
        if (response.code == 200) {
            $("#follower-text-" + userId).text(response.data.message);
            $("#followedCount").text(response.data.followedCount);
            $("#followerCount").text(response.data.followerCount);
            e.classList.toggle("active");
        } else {
            alertWindow(response.message);
        }
    });
}

$(function () {
    // 关注
    $("#follow-in-user").click(function () {
        const userPageId = $("#user-id").val();
        const userId = $("#user-id").val();
        $.post("/followUser", {
            "userPageId": userPageId,
            "userId": userId
        }, function (response) {
            if (response.code == 200) {
                $("#follow-in-user").toggleClass("active");
                $("#followedCount").text(response.data.followedCount);
                $("#followerCount").text(response.data.followerCount);
                $("#follow-big-text").text(response.data.message);
            } else {
                alertWindow(response.message);
            }
        });
    })
})

function alertWindow(content) {
    $("#alert-text").text(content);
    $("#user-alert").css("display", "block");
    setTimeout(function () {
        $("#user-alert").hide();
    }, 1500);
}