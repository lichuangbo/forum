function closeItem() {
    var user = $(".user");
    user.removeClass("open");
}

function openItem() {
    var user = $(".user");
    user.addClass("open");
}

$(function () {
    var oFileInput = new FileInput();
    oFileInput.Init("avatar-img", "/file/uploadAvatar");

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
        if (checkPass() && checkPass2()) {
            const password = $("#email-pass").val();
            $.post("/savePassword", {
                emailPassword: password
            }, function(response) {
                if (response.code == 200) {
                    alertWindow("修改成功");
                    $("#email-pass").val("");
                    $("#email-pass1").val("");
                }
            });
        }
    });
});

//初始化fileinput
var FileInput = function () {
    var oFile = {};

    //初始化fileinput控件（第一次初始化）
    oFile.Init = function (ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);

        //初始化上传控件的样式
        control.fileinput({
            language: 'zh',
            uploadUrl: uploadUrl,
            allowedFileExtensions: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            multiple: false,
            autoReplace: true,
            showUpload: false,
            showRemove: false,
            showCaption: false,
            browseClass: "btn btn-primary",
            validateInitialCount: true,
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
            msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！"
        });

        //导入文件上传完成之后的事件
        $("#avatar-img").on("fileuploaded", function (event, data, previewId, index) {
            $.post('/modifyAvatar', {
                'avatarUrl': data.response.url
            }, function (response) {
                if (response.code == 200) {
                    loadAvatar();
                }
            })
        });

        function loadAvatar() {
            $("#avatar-img-upload").load(
                "/getAvatar",
                {},
                function () {
                }
            );
        }
    }
    return oFile;
};

function alertWindow(content) {
    $("#alert-text").text(content);
    $("#profile-alert").css("display", "block");
    setTimeout(function () {
        $("#profile-alert").hide();
    }, 1500);
}

/*密码校验*/
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
