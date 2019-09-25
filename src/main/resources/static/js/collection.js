$(function () {

    var videos = [{
        "uid": 90,
        "webpage": "#",
        "title": "900000",
        "author": "eiji",
        "headshot": "images/1.jpg",
        "view": 1450090,
        "danmakunum": "90w",
        "popularitynum": "900w",
        "date": "2018-1-5",
        "intro": "dasddsdsdsdsdd",
        "status": 0
    }, {
        "uid": 91,
        "webpage": "#",
        "title": "911111",
        "author": "yozora",
        "headshot": "images/2.jpg",
        "view": 1450091,
        "danmakunum": "91w",
        "popularitynum": "911w",
        "date": "2018-5-5",
        "intro": "dasddsdsdsdsdd",
        "status": 1
    }, {
        "uid": 92,
        "webpage": "#",
        "title": "922222",
        "author": "lee",
        "headshot": "images/3.jpg",
        "view": 1450092,
        "danmakunum": "92w",
        "popularitynum": "922w",
        "date": "2018-5-9",
        "intro": "dasddsdsdsdsdd",
        "status": 2
    }];

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

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = unescape(ca[i].trim());
            if (c.indexOf(name) == 0) return decodeURI(c.substring(name.length, c.length));
        }
        return "";
    }

    //获取收藏的视频
    //videoDemonId 自动生成的视频id
    var mainDiv = $(".mainDiv");
    $.ajax({
        type: "POST",
        url: "/PersonalCenter/getFavorite",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": uid
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            // if (data.result == 1) {
            //得到收藏视频信息
            var videos = data;
            if(videos.length != 0){
                mainDiv.removeClass("mainDivBg");
            }
            addCollectionVideos(videos);
            // }else{
            //     alert("获取收藏夹失败");
            // }
        },
        error: function (json) {
            alert("获取收藏夹错误");
        }
    });

    function addCollectionVideos(videos) {
        for (var index = 0; index < videos.length; index++) {
            var author = videos[index].author; //获取视频up
            var title = videos[index].title; //获取视频标题
            var date = videos[index].date; //获取视频发布时间
            var danmakunum = videos[index].danmakunum; //获取视频弹幕数
            // var popularitynum = videos[index].popularitynum; //获取视频人气值
            var duration = videos[index].duration; //获取视频人气值
            var view = videos[index].view; //获取视频播放量
            var headshot = videos[index].coversrc;//获取视频封面
            var webpage = videos[index].webpage;//获取视频页面

            //生成collection div
            var resultAHead = "<a href=" + webpage + " target='blank' class='nav'>";
            var resultVideoItemHead = "<div class='videoItem'>";
            var resultVideoHeadShot = "<div class='video-headshot'><img src='" + headshot + "' /></div>";
            var resultVideoInforDetailHead = "<div class='video-infor-detail'>";
            var resultTitleAndUp = "<div class='video-title'>" + title + "</div><div class='video-up'>" + author + "</div>";
            var resultVideoDetailHead = "<div class='video-detail'>";
            var resulrVideoView = "<div class='video-infor'><div class='video-infor-logo'><img src='images/view.png' /></div><div class='video-infor-item'>" + view + "</div></div>";
            var resulrVideoDanmakuNum = "<div class='video-infor'><div class='video-infor-logo'><img src='images/danmaku.png' /></div><div class='video-infor-item'>" + danmakunum + "</div></div>";
            var resulrVideoPopularityNum = "<div class='video-infor'><div class='video-infor-logo'><img src='images/duration.png' /></div><div class='video-infor-item'>" + duration + "</div></div>";
            var resultVideoDate = "<div class='video-infor video-infor-date'><div class='video-infor-logo'><img src='images/date.png' /></div><div class='video-infor-item'>" + date + "</div></div>";
            var resultVideoDetailEnd = "</div>";
            var resultVideoInforDetailEnd = "</div>";
            var resultVideoItemEnd = "</div>";
            var resultAEnd = "</a>";

            //渲染div
            $(".mainDiv").append(resultAHead + resultVideoItemHead + resultVideoHeadShot + resultVideoInforDetailHead + resultTitleAndUp + resultVideoDetailHead + resulrVideoView +
                resulrVideoDanmakuNum + resulrVideoPopularityNum + resultVideoDate + resultVideoDetailEnd + resultVideoInforDetailEnd + resultVideoItemEnd + resultAEnd);
        }
    }

});