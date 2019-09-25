var $div = $(".search-result");
$(function () {
    var videos = [{
        "webpage": "#",
        "title": "aaa",
        "coversrc": "images/1.jpg",
        "view": "14500",
        "danmakunum": "58w",
        "popularitynum": "100w"
    }, {
        "webpage": "#",
        "title": "qwe",
        "coversrc": "images/2.jpg",
        "view": "6000",
        "danmakunum": "10w",
        "popularitynum": "11w"
    }, {
        "webpage": "#",
        "title": "rrr",
        "coversrc": "images/3.jpg",
        "view": "12580",
        "danmakunum": "565",
        "popularitynum": "1110"
    }, {
        "webpage": "#",
        "title": "yuyu",
        "coversrc": "images/4.jpg",
        "view": "21321",
        "danmakunum": "9666",
        "popularitynum": "333"
    }, {
        "webpage": "#",
        "title": "阿斯达所多",
        "coversrc": "images/5.jpg",
        "view": "2333",
        "danmakunum": "233",
        "popularitynum": "777773"
    }];

    //渲染视频
    var videos = JSON.parse(window.localStorage.getItem("videos"));
    // var type = Number(window.localStorage.getItem("type"));
    // var oType = Number(window.localStorage.getItem("oType"));
    // var videos = JSON.parse(getCookie("videos"));
    var type = Number(getCookie("type"));
    var oType = Number(getCookie("oType"));
    var page = Number(getCookie("page"));
    var totalPage = Number(getCookie("totalPage"));

    $(".total-page").text(totalPage);

    //
    //根据视频类型渲染标签
    // var oType = "8";
    // var type = "0";
    $("#" + type).removeClass("search-type-unclick").addClass("search-type-click");
    $("#" + oType).removeClass("search-type-unclick").addClass("search-type-click");

    //

    var key;
// if (window.localStorage.getItem("key") != "unddefined" && window.localStorage.getItem("key") != null && window.localStorage.getItem("key") != "") {
//     key = window.localStorage.getItem("key");
//     $("#searchInput").val(key);
// }
    if (getCookie("key") != "unddefined" && getCookie("key") != null && getCookie("key") != "") {
        key = getCookie("key");
        $("#searchInput").val(key);
    }
    addVideos(videos, $div);
    //


});

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = unescape(ca[i].trim());
        if (c.indexOf(name) == 0) return decodeURI(c.substring(name.length, c.length));
    }
    return "";
}

//根据标签检索
$(document).on("click",".search-type",function(){
    $(this).removeClass("search-type-unclick").addClass("search-type-click");
    $(this).attr("name", "1");
    $(this).siblings().removeClass("search-type-click").addClass("search-type-unclick");
    $(this).siblings().attr("name", "0");
    var content = $("#searchInput").val();
    var type = -1;
    var oType = -1;
    $("#video-type>div").each(function () {
        if ($(this).attr("name") == "1") {
            type = Number($(this).attr("id"));
        }
    });
    if(type == -1){
        type = Number(getCookie("type"));
    }
    $("#video-oType>div").each(function () {
        if ($(this).attr("name") == "1") {
            oType = Number($(this).attr("id"));
        }
    });
    if(oType == -1){
        oType = Number(getCookie("oType"));
    }
    var page = 1;
    submitPage(type, oType, content, page);
    // $.ajax({
    //     type: "POST",
    //     url: "/Search/getTopVideo",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //         "type": type,
    //         "oType": oType,
    //         "key": key
    //     }),
    //     dataType: "json",
    //     async: false,
    //     cache: false,
    //     success: function (data) {
    //         //得到视频信息
    //         var videos = data.video;
    //         //得到当前页数
    //         var page = data.page;
    //         //得到总页数
    //         var totalPage = data.totalPage;
    //         //渲染
    //         addVideos(videos, $div);
    //         setCookie("type", type, 1);
    //         setCookie("oType", oType, 1);
    //         setCookie("key", content, 1);
    //         setCookie("page",page,1);
    //         setCookie("totalPage",totalPage,1);
    //         //存储视频信息
    //         window.localStorage.setItem("videos", JSON.stringify(videos));
    //     },
    //     error: function (json) {
    //         alert("根据标签检索出错");
    //     }
    // });
});

//跳转页数
//首页
$(document).on("click", ".first-page", function () {
    var type = Number(getCookie("type"));
    var oType = Number(getCookie("oType"));
    var key = getCookie("key");
    var page = 1;
    submitPage(type, oType, key, page);
});
//上一页
$(document).on("click", ".pervious-page", function () {
    var type = Number(getCookie("type"));
    var oType = Number(getCookie("oType"));
    var key = getCookie("key");
    var page = Number($(".present-page").text()) - 1;
    if (page >= 1) {
        submitPage(type, oType, key, page);
    }
});
//下一页
$(document).on("click", ".next-page", function () {
    var type = Number(getCookie("type"));
    var oType = Number(getCookie("oType"));
    var key = getCookie("key");
    var totalPage = Number(getCookie("totalPage"));
    var page = Number($(".present-page").text()) + 1;
    if (page <= totalPage) {
        submitPage(type, oType, key, page);
    }
});
//尾页
$(document).on("click", ".last-page", function () {
    var type = Number(getCookie("type"));
    var oType = Number(getCookie("oType"));
    var totalPage = Number(getCookie("totalPage"));
    var key = getCookie("key");
    var page = totalPage;
    submitPage(type, oType, key, page);
});


//提交搜索信息
function submitPage(type, oType, key, page) {
    $.ajax({
        type: "POST",
        url: "/Search/getTopVideo",
        contentType: "application/json",
        data: JSON.stringify({
            "type": type,
            "oType": oType,
            "key": key,
            "currentPage": page
        }),
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            //得到视频信息
            var videos = data.videoList;
            //得到当前页数
            var page = data.page.currentPage;
            //得到总页数
            var totalPage = data.page.totalPage;
            //渲染
            addVideos(videos, $div);
            setCookie("type", type, 1);
            setCookie("oType", oType, 1);
            setCookie("key", key, 1);
            setCookie("page", page, 1);
            setCookie("totalPage", totalPage, 1);
            //存储视频信息
            window.localStorage.setItem("videos", JSON.stringify(videos));
            $(".present-page").text(page);
            $(".total-page").text(totalPage);
        },
        error: function (json) {
            alert("根据标签检索出错");
        }
    });
}

//设置cookie
function setCookie(name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getTime() + (expiredays * 24 * 60 * 60 * 1000))
    document.cookie = name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

//渲染视频搜索结果
function addVideos(videos, $div) {
    // if (videos) {
    $div.empty();
    for (var index = 0; index < videos.length; index++) {
        var webpage = videos[index].webpage;//网页地址
        var title = videos[index].title; //视频标题
        var coverSrc = videos[index].coversrc; //封面src
        var view = videos[index].view; //播放量
        var danmakuNum = videos[index].danmakunum;//弹幕数
        // var popularityNum = videos[index].popularitynum;//人气值
        var duration = videos[index].duration;//视频时长

        //生成video div
        var resultAHead = "<a href=" + webpage + " target='_blank'>";
        var resultAreaItemHead = "<div class='area-item'>";
        var resultImage = "<img class='hot-image' src=" + coverSrc + " />";
        var resultVideoTextHead = "<div class='hot-video-text'>";
        var resultVideoTitle = "<div class='hot-video-title'><div class='video-infor-title'>" + title + "</div></div>";
        var resultVideoAttrHead = "<div class='hot-video-type'>";
        var resulrVideoView = "<div class='video-infor'><div class='video-infor-logo'><img src='images/view.png' /></div><div class='video-infor-item'>" + view + "</div></div>";
        var resulrVideoDanmakuNum = "<div class='video-infor'><div class='video-infor-logo'><img src='images/danmaku.png' /></div><div class='video-infor-item'>" + danmakuNum + "</div></div>";
        var resulrVideoPopularityNum = "<div class='video-infor'><div class='video-infor-logo'><img src='images/duration.png' /></div><div class='video-infor-item'>" + duration + "</div></div>";
        var resultVideoAttrEnd = "</div>";
        var resultVideoTextEnd = "</div>";
        var resultAreaItemEnd = "</div>";
        var resultAEnd = "</a>";
        $div.append(resultAHead + resultAreaItemHead + resultImage + resultVideoTextHead + resultVideoTitle + resultVideoAttrHead + resulrVideoView + resulrVideoDanmakuNum +
            resulrVideoPopularityNum + resultVideoAttrEnd + resultVideoTextEnd + resultAreaItemEnd + resultAEnd);
    }
    // }
}

$('#searchInput').bind('keyup', function (event) {
    console.log("keyup");
    if (event.keyCode == "13") {
        //回车执行查询
        $('#searchBtn').click();
    }
});


