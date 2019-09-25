$(function () {
    //显示当前登录用户信息
    // var uname = window.localStorage.getItem("uname");
    // var password = window.localStorage.getItem("password");
    // var headshot = window.localStorage.getItem("headshot");
    // var uid = window.localStorage.getItem("uid");
    var uname = getCookie("uname");
    var uid = Number(getCookie("uid"));
    var headshot = getCookie("headshot");
    var password = getCookie("password");
    // alert(uname + "," + uid + "," + headshot + "," + password);
    // var uname = "yozora";
    // var uid = 1;
    // var headshot = "images/2.jpg";

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = unescape(ca[i].trim());
            if (c.indexOf(name) == 0) return decodeURI(c.substring(name.length, c.length));
        }
        return "";
    }

    // var uid = 1;
    // var uname = 555;
    // var password = 666;
    $("#uname").val(uname);
    $("#password").val(password);
    $("#changeHeadShot").attr("src", headshot);

    //更换头像
    $(".changeHeadShotHint").css("display", "none");
    $(".headShot").hover(function () {
        $(".changeHeadShotHint").css("display", "inline");
    });
    $(".headShot").mouseleave(function () {
        $(".changeHeadShotHint").css("display", "none");
    });

    $(".changeHeadShotHint").click(function () {
        $("#file").click();
    });

    //保存头像
    $("#headShotBtn").click(function () {
        // $.ajaxFileUpload({
        //     url: '/PersonalCenter/uploadHeadshot',
        //     fileElementId: 'file',
        //     dataType: 'txt',
        //     secureuri: false,
        //     success: function (data) {
        //         alert(data);
        //         if (data == "FAILED") {
        //             alert("上传失败!")
        //         } else {
        //             alert(data);
        //             setCookie("headshot", data.headshot, 1);
        //             window.location.href = '/goToPerson';
        //         }
        //     },
        //     error: function (data, status, e) {
        //         alert(e);
        //     }
        // });
        $.ajaxFileUpload({
            url: '/PersonalCenter/uploadHeadshot',
            fileElementId: 'file',
            dataType: 'txt',
            secureuri: false,
            success: function (data) {
                if (data == "FAILED") {
                    alert("上传失败!")
                } else {
                    console.log(data);
                    setCookie("headshot", data, 1);
                    window.location.href = '/goToPerson';
                }
            },
            error: function (data, status, e) {
                alert(e);
            }
        });
    });

    //修改保存信息
    $("#inforBtn").click(function () {
        $.ajax({
            type: "POST",
            url: "/PersonalCenter/alfterMyInfo",
            contentType: "application/json",
            data: JSON.stringify({
                "uid": uid,
                "uname": $("#uname").val(),
                "password": $("#password").val()
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {
                if (data[0].result == 1) {
                    // window.localStorage.setItem("uname", $("#uname").val());
                    // window.localStorage.setItem("password", $("#password").val());
                    // window.localStorage.setItem("uid", data.uid);
                    setCookie("uname", $("#uname").val(), 1);
                    setCookie("password", $("#password").val(), 1);
                    alert("修改成功！");
                    $('#infor-iframe').attr('src', $('#infor-iframe').attr('/goToPerson'));
                    // window.location.href = '/goToInfor';
                }
                if (data[0].result == 0) {
                    alert("信息修改失败");
                }
            },
            error: function (json) {
                alert("修改出错");
            }
        });
    });

});

//设置cookie
function setCookie(name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getTime() + (expiredays * 24 * 60 * 60 * 1000))
    document.cookie = name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

//修改头像
function filechange(event) {
    var files = event.target.files, file;
    if (files && files.length > 0) {
        // 获取目前上传的文件
        file = files[0];// 文件大小校验的动作
        if (file.size > 1024 * 1024 * 2) {
            alert('图片大小不能超过 2MB!');
            return false;
        }
        // 获取 window 的 URL 工具
        var URL = window.URL || window.webkitURL;
        // 通过 file 生成目标 url
        var imgURL = URL.createObjectURL(file);
        //用attr将img的src属性改成获得的url
        $("#changeHeadShot").attr("src", imgURL);
        // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
        // URL.revokeObjectURL(imgURL);
    }
}

//身份证验证
function verifyIdentify() {
    var cardno = $("#identify").val();
    $.ajax({
        type: "POST",
        url: "/PersonalCenter/verifyIDCard",
        contentType: "application/json",
        data: JSON.stringify({
            "cardno": cardno
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            var resultcode = Number(data.resultcode);
            if (resultcode == 200) {
                var result = data.result;
                alert("验证成功!" + result.area + "," + result.sex + "," + result.birthday);
            }
            if (resultcode != 200) {
                var errorCode = data.error_code;
                var reason = data.reason;
                alert(reason);
            }
        },
        error: function (json) {
            alert("身份证验证出错");
        }
    });
}

// function verifyIdentifyErrorCode(key) {
//     var result = "";
//     switch (key){
//         case 203801:
//             result = "请输入正确的15或18位身份证!";
//             break;
//         case 203802:
//             result = "错误的身份证或无结果!";
//             break;
//         case 203803:
//             result = "身份证校验位不正确!";
//             break;
//         case 203804:
//             result = "查询失败!";
//             break;
//         default:
//             result = "其他错误!";
//     }
//     return result;
// }