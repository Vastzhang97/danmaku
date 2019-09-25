$(function () {

    $("#login").click(function () {
        window.location.href = '/goToLogin';
    });
    $("#register").click(function () {
        var uname = $("#uname").val();
        var password = $("#password").val();
        if (uname != "" && password != "") {
            $.ajax({
                type: "POST",
                url: "/register",
                contentType: "application/json",
                data: JSON.stringify({
                    "uname": $("#uname").val(),
                    "password": $("#password").val()
                }),
                dataType: "json",
                async: false,
                cache: false,
                success: function (data) {
                    // var success = eval("(" + data + ")"); //包数据解析为json 格式
                    if (data[0].result == 1) {
                        window.location.href = '/goToLogin';
                    } else {
                        alert("注册失败");
                    }
                },
                error: function (json) {
                    alert("注册出错");
                }
            });
        }else {
            alert("账号密码不能为空哦！")
        }
        // console.log($("#username").val() + $("#password").val());
    });

});