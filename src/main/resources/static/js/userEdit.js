function closeItem() {
    var user = $(".user");
    user.removeClass("open");
}

function openItem() {
    var user = $(".user");
    user.addClass("open");
}

$(function () {
    // 保存个人资料
    $('#saveProfile').click(function () {
        const nickName = $("#nickName").val();
        if (nickName == null || nickName.length < 1) {
            alertWindow("昵称不能为空");
            return;
        }

        $.post("/saveProfile", {
            nickname: nickName,
            descript: $("#descript").val()
        }, function(response) {
            if (response.code == 200) {
                alertWindow("修改成功");
            }
        });
    });

    // 修改密码
    $('#savePassword').click(function () {
        if (checkOldPass() && checkPass() && checkPass2()) {
            const oldPass = $("#old-pass").val();
            const password = $("#email-pass").val();
            $.post("/savePassword", {
                oldPassword: oldPass,
                newPassword: password
            }, function(response) {
                if (response.code == 200) {
                    alertWindow("密码修改成功");
                    $("#old-pass").val("");
                    $("#email-pass").val("");
                    $("#email-pass1").val("");
                } else {
                    alertWindow(response.message);
                }
            });
        }
    });
});

layui.use(['upload', 'layer'], function () {
    var $ = layui.jquery,
        upload = layui.upload,
        layer = layui.layer;

    upload.render({
        elem: '#test10',
        url: '/file/uploadAvatar',
        done: function(res){
            layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.url);
            $.post('/modifyAvatar', {
                'avatarUrl': res.url
            }, function (response) {
                if (response.code == 200) {
                    alertWindow("头像修改成功")
                } else {
                    alertWindow(response.message);
                }
            })
        }
    });
});

function alertWindow(content) {
    $("#alert-text").text(content);
    $("#profile-alert").css("display", "block");
    setTimeout(function () {
        $("#profile-alert").hide();
    }, 1500);
}

/*密码校验*/
function checkOldPass() {
    var pass = $("#old-pass").val();
    if (pass == null || pass.length < 1) {
        $("#old-pass").css("border", "1px solid red");
        $("#old-tap").text("请输入密码");
        return false;
    }
    var reg_pass = /^[a-zA-Z0-9]{6,20}$/;
    var flag = reg_pass.test(pass);
    if (flag) {
        $("#old-pass").css("border", "");
        $("#old-tap").text("");
    } else {
        $("#old-pass").css("border", "1px solid red");
        $("#old-tap").text("密码必须由6-20位大小写字母和数字组成");
    }
    return flag;
}

function checkPass() {
    var pass = $("#email-pass").val();
    if (pass == null || pass.length < 1) {
        $("#email-pass").css("border", "1px solid red");
        $("#pass-tap").text("请输入密码");
        return false;
    }
    var reg_pass = /^[a-zA-Z0-9]{6,20}$/;
    var flag = reg_pass.test(pass);
    if (flag) {
        $("#email-pass").css("border", "");
        $("#pass-tap").text("");
    } else {
        $("#email-pass").css("border", "1px solid red");
        $("#pass-tap").text("密码必须由6-20位大小写字母和数字组成");
    }
    return flag;
}

function checkPass2() {
    var pass2 = $("#email-pass1").val();
    if (pass2 == null || pass2.length < 1) {
        $("#email-pass1").css("border", "1px solid red");
        $("#pass1-tap").text("请输入密码");
        return false;
    }
    var reg_pass = /^[a-zA-Z0-9]{6,20}$/;
    var flag = reg_pass.test(pass2);
    if (flag) {
        $("#email-pass1").css("border", "");
        $("#pass1-tap").text("");
    } else {
        $("#email-pass1").css("border", "1px solid red");
        $("#pass1-tap").text("密码必须由6-20位大小写字母和数字组成");
    }
    var pass = $("#email-pass").val();
    if (pass2 !== pass) {
        $("#email-pass1").css("border", "1px solid red");
        $("#pass1-tap").text("两次密码输入不一致");
        return false;
    }
    return flag;
}
