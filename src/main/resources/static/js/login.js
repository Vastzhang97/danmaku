$(function () {

    $("#register").click(function () {
        window.location.href = '/goToRegister';
    });

    $("#login").click(function () {
        $.ajax({
            type: "POST",
            url: "/login",
            contentType: "application/json",
            data: JSON.stringify({
                "uname": $("#uname").val(),
                "password": $("#password").val()
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {
                // var success = eval("(" + data + ")"); //包数据解析为json 格式                                                            
                if (data.result == 1) {
                    // window.localStorage.setItem("uname", $("#uname").val());
                    // window.localStorage.setItem("password", $("#password").val());
                    // window.localStorage.setItem("headshot", data.headshot);
                    // window.localStorage.setItem("uid", data.uid);
                    setCookie("uname", $("#uname").val(), 1);
                    setCookie("password", $("#password").val(), 1);
                    setCookie("headshot", data.headshot, 1);
                    setCookie("uid", data.uid, 1);
                    setCookie("privilege", data.privilege, 1);
                    console.log(data);
                    window.location.href = '/goToIndex';
                }
                if (data.result == 0) {
                    alert(data.msg);
                }
                if (data.result == 2) {
                    alert(data.msg);
                }
            },
            error: function (json) {
                alert("登录出错");
            }
        });
        // console.log($("#username").val() + $("#password").val());
    });

    $('#password').bind('keyup', function (event) {
        console.log("keyup");
        if (event.keyCode == "13") {
            //回车执行查询
            $('#login').click();
        }
    });
});

//设置cookie
function setCookie(name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getTime() + (expiredays * 24 * 60 * 60 * 1000))
    document.cookie = name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

