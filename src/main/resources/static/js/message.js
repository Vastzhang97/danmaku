$(function () {

    var result = getCookie("privateMsg");
    if(result == "privateMsg"){
        $("#infor-iframe").attr("src", "/goToPrivateMsg");
        $("#infor-nav-fourth").css("backgroundColor", "dodgerblue");
        $("#infor-nav-fourth").css("color", "white");
        $("#infor-nav-fourth").siblings().css("backgroundColor", "\t#FCFCFC");
        $("#infor-nav-fourth").siblings().css("color", "black");
    }else{
        $("#infor-iframe").attr("src", "/goToSysMessage");
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
    //设置cookie
    function setCookie(name, value, expiredays) {
        var exdate = new Date()
        exdate.setDate(exdate.getTime() + (expiredays * 24 * 60 * 60 * 1000))
        document.cookie = name + "=" + escape(value) +
            ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
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
        $("#infor-iframe").attr("src", "/goToSysMessage");
        $("#headList").text("系统消息");
    });
    $("#infor-nav-second,#infor-nav-second-min").click(function () {
        $("#infor-iframe").attr("src", "/goToReply");
        $("#headList").text("回复我的");
    });
    $("#infor-nav-third,#infor-nav-third-min").click(function () {
        $("#infor-iframe").attr("src", "/goToReceiveLike");
        $("#headList").text("收到的赞");
    });
    $("#infor-nav-fourth,#infor-nav-fourth-min").click(function () {
        $("#infor-iframe").attr("src", "/goToPrivateMsg");
        $("#headList").text("我的私信");
    });
})