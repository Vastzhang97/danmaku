$(function () {
    $("#infor-iframe").attr("src", "/goToAdminUser");
    //
    // 左侧导航栏
    if (document.body.clientWidth <= 768) {
        $(".nav-min").click(function () {
            $(".left-item-min").css({
                // "display":"inline",
                "width": "50%"
            });
        });
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
        $("#infor-iframe").attr("src", "/goToAdminUser");
        $("#headList").text("管理用户");
    });
    $("#infor-nav-second,#infor-nav-second-min").click(function () {
        $("#infor-iframe").attr("src", "/goToAdminContribution");
        $("#headList").text("管理视频");
    });
    $("#infor-nav-third,#infor-nav-third-min").click(function () {
        $("#infor-iframe").attr("src", "/goToaAdminComment");
        $("#headList").text("管理评论");
    });
    $("#infor-nav-fourth,#infor-nav-fourth-min").click(function () {
        $("#infor-iframe").attr("src", "/goToAdminDanmaku");
        $("#headList").text("管理弹幕");
    });
})