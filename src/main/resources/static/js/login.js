function checkEmail() {
    var email = $("#email").val();
    if (email == null || email.length < 1) {
        $("#email").css("border", "1px solid red");
        $("#email-tap").text("请输入邮箱");
        return false;
    }
    var reg_email = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    var flag = reg_email.test(email);
    if (flag) {
        $("#email").css("border", "");
        $("#email-tap").text("");
    } else {
        $("#email").css("border", "1px solid red");
        $("#email-tap").text("邮箱格式有误");
    }
    $.post("/checkEmail", {
        "email": email
    }, function (response) {
        if (response.code == 2014) {
            $("#email").css("border", "1px solid red");
            $("#email-tap").text(response.message);
            flag = false;
        }
    });
    return flag;
}

function checkLoginEmail() {
    var email = $("#email").val();
    if (email == null || email.length < 1) {
        $("#email").css("border", "1px solid red");
        $("#email-tap").text("请输入邮箱");
        return false;
    }
    var reg_email = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    var flag = reg_email.test(email);
    if (flag) {
        $("#email").css("border", "");
        $("#email-tap").text("");
    } else {
        $("#email").css("border", "1px solid red");
        $("#email-tap").text("邮箱格式有误");
    }
    return flag;
}

function checkPass() {
    var pass = $("#password").val();
    var reg_pass = /^[a-zA-Z0-9]{6,20}$/;
    if (pass == null || pass.length < 1) {
        $("#password").css("border", "1px solid red");
        $("#pass1-tap").text("请输入密码");
        return false;
    }
    var flag = reg_pass.test(pass);
    if (flag) {
        $("#password").css("border", "");
        $("#pass1-tap").text("");
    } else {
        $("#password").css("border", "1px solid red");
        $("#pass1-tap").text("密码必须由6-20位大小写字母和数字组成");
    }
    return flag;
}

function checkPass2() {
    var pass2 = $("#password2").val();
    var reg_pass = /^[a-zA-Z0-9]{6,20}$/;
    if (pass2 == null || pass2.length < 1) {
        $("#password2").css("border", "1px solid red");
        $("#pass2-tap").text("请输入密码");
        return false;
    }
    var flag = reg_pass.test(pass2);
    if (flag) {
        $("#password2").css("border", "");
        $("#pass2-tap").text("");
    } else {
        $("#password2").css("border", "1px solid red");
        $("#pass2-tap").text("密码必须由6-20位大小写字母和数字组成");
    }
    var pass = $("#password").val();
    if (pass2 !== pass) {
        $("#password2").css("border", "1px solid red");
        $("#pass2-tap").text("两次密码输入不一致");
        return false;
    }
    return flag;
}

function checkCode() {
    var code = $("#code").val();
    if (code == null || code.length < 1) {
        $("#code").css("border", "1px solid red");
        $("#code-tap").text("请输入验证码");
        return false;
    }
    var reg_code = /^[a-zA-Z0-9]{4}$/;
    var flag = reg_code.test(code);
    if (flag) {
        $("#code").css("border", "");
        $("#code-tap").text("");
    } else {
        $("#code").css("border", "1px solid red");
        $("#code-tap").text("验证码格式有误");
    }
    return flag;
}

function closeItem() {
    var user = $(".user");
    user.removeClass("open");
}

function openItem() {
    var user = $(".user");
    user.addClass("open");
}

function getCode() {
    var email = $("#email").val();
    if (checkEmail()) {
        $.post("/getCode", {
            "email": email
        }, function (data) {
        })
    }
}

function register() {
    if (checkEmail() && checkPass() && checkPass2() && checkCode()) {
        var email = $("#email").val();
        var password = $("#password").val();
        var code = $("#code").val();

        $.ajax({
            type: "post",
            url: "/register",
            contentType: "application/json;charset=utf-8",// 以JSON格式发送
            dataType: "json",// 返回值必须是JSON格式
            data: JSON.stringify({
                "email": email,
                "password": password,
                "code": code
            }),
            success: function (response) {
                console.log("response:" + response);
                if (response.code == 2012 || response.code == 2013) {
                    $("#code").css("border", "1px solid red");
                    $("#code-tap").text(response.message);
                } else {
                    document.location.href = "/"
                }
            }
        });
    }
}

function login() {
    if (checkLoginEmail() && checkPass()) {
        var email = $("#email").val();
        var password = $("#password").val();
        var state = $("#remember_me").is(":checked");
        $.post("/login", {
            "email": email,
            "password": password,
            "state": state
        }, function (response) {
            if (response.code == 2015) {
                $("#login-tap").text(response.message);
            } else {
                document.location.href = "/"
            }
        })
    }
}