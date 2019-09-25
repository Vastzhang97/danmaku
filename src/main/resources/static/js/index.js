//用户信息
// var uname = window.localStorage.getItem("uname");
// var uid = Number(window.localStorage.getItem("uid"));
// var headshot = window.localStorage.getItem("headshot");
var uname = getCookie("uname");
var uid = Number(getCookie("uid"));
var headshot = getCookie("headshot");
// alert(c_uname + "," + c_uid + "," + c_headshot);
// var uname = "yozora";
// var uid = 1;
// var headshot = "images/2.jpg";
var pastPlayList = [];

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

$(function () {
    //渲染视频
    var videos = [{
        "webpage": "#",
        "title": "aaa",
        "coversrc": "images/1.jpg",
        "view": "14500",
        "danmakunum": "58w",
        "popularitynum": "100w",
        "category": "0"
    }, {
        "webpage": "#",
        "title": "555",
        "coversrc": "images/1.jpg",
        "view": "14500",
        "danmakunum": "58w",
        "popularitynum": "100w",
        "category": "0"
    }, {
        "webpage": "#",
        "title": "888",
        "coversrc": "images/1.jpg",
        "view": "14500",
        "danmakunum": "58w",
        "popularitynum": "100w",
        "category": "0"
    }, {
        "webpage": "#",
        "title": "aaa",
        "coversrc": "images/1.jpg",
        "view": "14500",
        "danmakunum": "58w",
        "popularitynum": "100w",
        "category": "1"
    }, {
        "webpage": "#",
        "title": "qwe",
        "coversrc": "images/2.jpg",
        "view": "6000",
        "danmakunum": "10w",
        "popularitynum": "11w",
        "category": "1"
    }, {
        "webpage": "#",
        "title": "rrr",
        "coversrc": "images/3.jpg",
        "view": "12580",
        "danmakunum": "565",
        "popularitynum": "1110",
        "category": "1"
    }, {
        "webpage": "#",
        "title": "yuyu",
        "coversrc": "images/4.jpg",
        "view": "21321",
        "danmakunum": "9666",
        "popularitynum": "333",
        "category": "2"
    }, {
        "webpage": "#",
        "title": "阿斯达所多",
        "coversrc": "images/5.jpg",
        "view": "2333",
        "danmakunum": "233",
        "popularitynum": "2333",
        "category": "2"
    }];
    $(".islogin-title").attr("src", headshot);
    $(".min-user").children().attr("src", headshot);

    $(".head-list-items-first").css("cursor", "pointer");
    //获取首页所有视频信息
    // if ($(document).attr("title") == "首页") {
    //     $.ajax({
    //         type: "POST",
    //         url: "/getRecommendVideo",
    //         contentType: "application/json",
    //         // data: JSON.stringify({
    //         //     "type": type,
    //         //     "content": content
    //         // }),
    //
    //         //TODO category为int类型
    //
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             //得到视频信息
    //             var videos = data;
    //             for (var index = 0; index < videos.length; index++) {
    //                 var video = videos[index];
    //                 var category = video.category;
    //                 if (category == 0) { //渲染热门视频
    //                     // console.log(video);
    //                     var $div = $(".hot-list");
    //                     addHotVideos(video, $div);
    //                 } else if (category == 1) {//渲染动画区
    //                     var $div = $("#video-1");
    //                     addVideos(video, $div);
    //                 } else if (category == 2) {//渲染音乐区
    //                     var $div = $("#video-2");
    //                     addVideos(video, $div);
    //                 } else if (category == 3) {//渲染游戏区
    //                     var $div = $("#video-3");
    //                     addVideos(video, $div);
    //                 } else if (category == 4) {//渲染鬼畜区
    //                     var $div = $("#video-4");
    //                     addVideos(video, $div);
    //                 } else if (category == 5) {//渲染舞蹈区
    //                     var $div = $("#video-5");
    //                     addVideos(video, $div);
    //                 } else if (category == 6) {//渲染体育区
    //                     var $div = $("#video-6");
    //                     addVideos(video, $div);
    //                 } else if (category == 7) {//渲染科技区
    //                     var $div = $("#video-7");
    //                     addVideos(video, $div);
    //                 }
    //             }
    //         },
    //         error: function (json) {
    //             alert("得到视频信息错误");
    //         }
    //     });
    // }

    var pastPlay = [{
        "vid": "1",
        "title": "aaa",
        "date": "2018-3-5"
    }, {
        "vid": "2",
        "title": "bbb",
        "date": "2018-3-8"
    }, {
        "vid": "3",
        "title": "ccc",
        "date": "2018-3-9"
    }];

    getNavigationBar();
    //计时器
    var timer = null;

    $(".unlogin").css("display", "inline");
    $(".islogin").css("display", "none");
    if (uid != "undefined" && uid != null && uid != "") {
        $(".unlogin").css("display", "none");
        $(".islogin").css("display", "inline");
    }
    //投稿
    $(".head-submit").click(function () {
        if (uid != "undefined" && uid != null && uid != "") {
            window.location.href = '/goToContribution';
        } else {
            window.location.href = '/goToLogin';
        }
    });
    // 播放历史
    $(".head-list-items-play").hover(function () {
        timer = setTimeout(function () {
            $(".play-past").slideToggle(500);
        }, 300);
    }, function () {
        clearTimeout(timer);
    });

    $(".head-list-items-play").mouseleave(function () {
        $(".play-past").slideUp(500);
    });

    //移动端顶部个人信息
    $(".min-user").click(function () {
        if (uid != "undefined" && uid != null && uid != "") {
            window.location.href = '/goToInfor';
        } else {
            window.location.href = '/goToLogin';
        }
    })

    // 移动端顶部下拉菜单
    $("#headList").hover(function () {
        $("#headListDiv").slideToggle(500);
        // $("#SecondList").css("display","inline");
    });
    //

    //图片轮播
    // $("#all div").mouseover(function () {//鼠标进入离开事件  
    //     $(this).css("background-color", "#ff00ff").siblings().css("background-color", "white");
    //     $(this).css({ "background-color": "red", "font-size": "9px" }).siblings().hide();
    // });

    $(window).scroll(function () {//鼠标滚动事件  
        var _top = $(window).scrollTop();//获得鼠标滚动的距离  
    });

    //手动播放图片  
    $(".btn-list div").hover(function () {

        $(this).addClass("one").siblings().removeClass("one");
        index = $(this).index();
        i = index;
        $(".pic a").eq(index).stop().fadeIn(500).show().siblings().stop().fadeIn(500).hide();
    });

    //自动播放图片  
    var i = 0;
    var t = setInterval(autoplay, 3000);

    function autoplay() {
        i++;
        if (i > 5) i = 0;
        $(".btn-list div").eq(i).addClass("one").siblings().removeClass("one");
        $(".pic a").eq(i).stop().fadeIn(500).show().siblings().stop().fadeIn(500).hide();
    }

    $("#banner").hover(function () {
        clearInterval(t);
    }, function () {
        t = setInterval(autoplay, 3000);
    });
    $("#banner-btn").hover(function () {
        clearInterval(t);
    }, function () {
        t = setInterval(autoplay, 3000);
    });

    //搜索
    $("#searchBtn").click(function () {
        var type = Number($(".head-item-select").val());
        var oType = 8;
        var content = $("#searchInput").val();
        $.ajax({
            type: "POST",
            url: "/Search/search",
            contentType: "application/json",
            data: JSON.stringify({
                "category": type,
                "key": content
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {
                // if (data.result == 1) {
                //得到视频信息
                var videos = data.videoList;
                //得到当前页数
                var page = data.page.currentPage;
                //得到总页数
                var totalPage = data.page.totalPage;
                //存储视频信息
                window.localStorage.setItem("videos", JSON.stringify(videos));
                // window.localStorage.setItem("type", type);
                // window.localStorage.setItem("oType", oType);
                // window.localStorage.setItem("key", content);
                // setCookie("videos", JSON.stringify(videos[0]), 1);
                setCookie("type", type, 1);
                setCookie("oType", oType, 1);
                setCookie("key", content, 1);
                setCookie("page", page, 1);
                setCookie("totalPage", totalPage, 1);
                //跳转页面
                window.location.href = '/goToSearch';
                // } else {
                //     alert("搜索失败")
                // }
            },
            error: function (json) {
                alert("搜索错误");
            }
        });

    });


    //渲染热门视频
    function addHotVideos(video, $div) {
        var webpage = video.webpage;//网页地址
        var title = video.title; //视频标题
        var coverSrc = video.coversrc; //封面src

        var resultHotItemHead = "<div class='hot-items'>";
        var resultAHead = "<a href=" + webpage + ">";
        var resultImage = "<img class='hot-image' src=" + coverSrc + " />";
        var resultHotVideo = "<div class='hot-video-text'><div class='hot-video-title'>" + title + "</div><div class='hot-video-type'>热门视频</div></div>";
        var resultAEnd = "</a>";
        var resultHotItemEnd = "</div>";
        $div.append(resultHotItemHead + resultAHead + resultImage + resultHotVideo + resultAEnd + resultHotItemEnd);

    }

    //渲染分区视频
    function addVideos(video, $div) {

        var webpage = video.webpage;//网页地址
        var title = video.title; //视频标题
        var coverSrc = video.coversrc; //封面src
        var view = video.view; //播放量
        var danmakuNum = video.danmakunum;//弹幕数
        // var popularityNum = video.popularitynum;//人气值
        var duration = video.duration;//视频时长

        //生成video div
        var resultAHead = "<a href=" + webpage + " target='blank'>";
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

    $('#searchInput').bind('keyup', function (event) {
        console.log("keyup");
        if (event.keyCode == "13") {
            //回车执行查询
            $('#searchBtn').click();
        }
    });


});
//更多
$(document).on("click", ".main-item-more", function () {
    var type = Number($(this).attr("name"));
    var oType = 8;
    var key = "";
    var page = 1;
    searchVideos(type, oType, key, page);
});

$(document).on("click", ".head-options", function () {
    var type = Number($(this).attr("name"));
    var oType = 8;
    var key = "";
    var page = 1;
    searchVideos(type, oType, key, page);
});

function searchVideos(type, oType, key, page) {
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
            setCookie("type", type, 1);
            setCookie("oType", oType, 1);
            setCookie("key", key, 1);
            setCookie("page", page, 1);
            setCookie("totalPage", totalPage, 1);
            //存储视频信息
            window.localStorage.setItem("videos", JSON.stringify(videos));
            window.location.href = '/goToSearch';
        },
        error: function (json) {
            alert("检索更多视频出错");
        }
    });
}

//获取用户历史播放信息
function getNavigationBar() {
    if (uname != "undefined" && uname != null && uname != "") {
        $.ajax({
            type: "POST",
            url: "/PersonalCenter/getNavigationBar",
            contentType: "application/json",
            data: JSON.stringify({
                "uid": uid
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {
                // if (data.result == 1) {
                //得到历史播放信息
                pastPlayList = pastPlayList.concat(data.historyList);
                //得到未读消息消息提示
                var hasUnread = data.hasUnread;
                if (hasUnread == true) {
                    $(".msg-tip").css("display", "inline");
                } else {
                    $(".msg-tip").css("display", "none");
                }
                //渲染
                addPastPlay(pastPlayList);
                // }
                pastPlayList = [];
                //
            },
            error: function (json) {
            }
        });
    }
    var t = setTimeout("getNavigationBar()", 60000);
}

//渲染历史播放div
function addPastPlay(pastPlay) {
    $(".play-past-item").remove();
    for (var index = 0; index < pastPlay.length; index++) {
        var title = pastPlay[index].title; //获取视频标题
        var date = pastPlay[index].date; //获取播放时间
        var vid = pastPlay[index].vid; //获取视频id
        var webpage = pastPlay[index].webpage

        var resultItem = "<a href='" + webpage + "' target=\"_blank\" ><div class='play-past-item' name='" + vid + "'><div class='play-past-title'>" + title + "</div><div class='play-past-date'>" + date + "</div></div></a>";
        $(".play-past").append(resultItem);
    }
    var resultBottom = "<div class=\"play-past-item play-past-more\">查看更多>></div>";
    $(".play-past").append(resultBottom);
}

//置顶
function goTop(acceleration, time) {

    acceleration = acceleration || 0.1;
    time = time || 16;
    var x1 = 0;
    var y1 = 0;
    var x2 = 0;
    var y2 = 0;
    var x3 = 0;
    var y3 = 0;
    if (document.documentElement) {
        x1 = document.documentElement.scrollLeft || 0;
        y1 = document.documentElement.scrollTop || 0;
    }
    if (document.body) {
        x2 = document.body.scrollLeft || 0;
        y2 = document.body.scrollTop || 0;
    }
    var x3 = window.scrollX || 0;
    var y3 = window.scrollY || 0;
    // 滚动条到页面顶部的水平距离   
    var x = Math.max(x1, Math.max(x2, x3));
    // 滚动条到页面顶部的垂直距离   
    var y = Math.max(y1, Math.max(y2, y3));
    // 滚动距离 = 目前距离 / 速度, 因为距离原来越小, 速度是大于 1 的数, 所以滚动距离会越来越小   
    var speed = 1 + acceleration;
    window.scrollTo(Math.floor(x / speed), Math.floor(y / speed));
    // 如果距离不为零, 继续调用迭代本函数  
    if (x > 0 || y > 0) {
        var invokeFunction = "goTop(" + acceleration + ", " + time + ")";
        window.setTimeout(invokeFunction, time);
    }
}

function advice() {
    window.location.href = '/goToAdvice';
}

//设置cookie
function setCookie(name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getTime() + (expiredays * 24 * 60 * 60 * 1000))
    document.cookie = name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}


