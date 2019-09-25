$(function () {

    $("#infor-iframe").attr("src", "/goToPerson");

    var privilege = Number(getCookie("privilege"));
    var loginUid = Number(getCookie("uid"));
    if (privilege == 0) {
        $(".admin").css("display", "inline");
    }else{
        $(".admin").css("display", "none");
    }

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = unescape(ca[i].trim());
            if (c.indexOf(name) == 0) return decodeURI(c.substring(name.length, c.length));
        }
        return "";
    }

    //
    // 左侧导航栏
    if (document.body.clientWidth <= 768) {
        $(".nav-min").click(function () {
            $(".left-item-min").css({
                // "display":"inline",
                "width": "50%"
            });
        });
        // $(".left-item-min").siblings().click(function () {
        //     $(".left-item-min").css({
        //         // "display":"inline",
        //         "width": "0%"
        //     });
        // });
        $(".left-item-min,.right-item,#headList,#headListDiv").click(function () {
            $(".left-item-min").css({
                // "display":"inline",
                "width": "0%"
            });
        });
    }

    $(".infor-item").click(function () {
        var index = $(this).index();
        $(".infor-item").eq(index).css("backgroundColor", "dodgerblue");
        $(".infor-item").eq(index).css("color", "white");
        $(".infor-item").eq(index).siblings().css("backgroundColor", "white");
        $(".infor-item").eq(index).siblings().css("color", "black");
    });
    //
    //左侧导航栏跳转
    $("#infor-nav-first,#infor-nav-first-min").click(function () {
        $("#infor-iframe").attr("src", "/goToPerson");
        $("#headList").text("我的信息");
    });
    $("#infor-nav-second,#infor-nav-second-min").click(function () {
        $("#infor-iframe").attr("src", "/goToMyVideo");
        $("#headList").text("我的视频");
    });
    $("#infor-nav-third,#infor-nav-third-min").click(function () {
        $("#infor-iframe").attr("src", "/goToCollection");
        $("#headList").text("我的收藏");
    });
    $("#infor-nav-fourth,#infor-nav-fourth-min").click(function () {
        $("#infor-iframe").attr("src", "/goToMyContribution");
        $("#headList").text("我的投稿");
    });
    $("#infor-nav-fifth,#infor-nav-fifth-min").click(function () {
        var result = confirm("是否要退出登录?");
        if (result == true) {
            $.ajax({
                type: "POST",
                url: "/logout",
                contentType: "application/json",
                data: JSON.stringify({
                    "uid": loginUid
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    if (data[0].result == 1) {
                        //清空cookie
                        clearAllCookie();
                        window.location.href = '/goToIndex';
                    }
                },
                error: function (json) {
                    alert("用户登出错误");
                }
            });
        }
    });
})

function clearAllCookie() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;)
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
    }
}