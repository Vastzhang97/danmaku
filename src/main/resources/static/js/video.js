var danmakus = [
    // {
    // "id": 1,
    // "content": "11111",
    // "duration": "10",
    // "date": "3-1 10:10",
    // "color": 1,
    // "position": 1
    // }, {
    //     "id": 2,
    //     "content": "22222",
    //     "duration": "15",
    //     "date": "3-2 10:10",
    //     "color": 2,
    //     "position": 1
    // }, {
    //     "id": 3,
    //     "content": "33333",
    //     "duration": "20",
    //     "date": "3-3 10:10",
    //     "color": 3,
    //     "position": 1
    // }, {
    //     "id": 4,
    //     "content": "44444",
    //     "duration": "25",
    //     "date": "3-4 10:10",
    //     "color": 4,
    //     "position": 1
    // },
];

var newDanmku = [];//新添加的webSocket收到的弹幕
var newDanmakus = [];//新发弹幕的数组

//用户信息
// var loginUname = window.localStorage.getItem("username");
// var loginUid = Number(window.localStorage.getItem("uid"));
// var Uheadshot = window.localStorage.getItem("headshot");
var loginUname = getCookie("uname");
var loginUid = Number(getCookie("uid"));
var Uheadshot = getCookie("headshot");
var password = getCookie("password");
// var loginUname = "yozora";
// var loginUid = 1;
// var Uheadshot = "images/6.jpg";
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = unescape(ca[i].trim());
        if (c.indexOf(name) == 0) return decodeURI(c.substring(name.length, c.length));
    }
    return "";
}

//获取视频信息
var videoDemonId = Number($("#videoDemonId").text()); //videoDemonId 自动生成的视频id

//评论数组(id,uname)
var commentArray = []; //评论视频的评论
var commentReplyArray = []; //回复评论的评论

//已点赞评论数组(cid)
var commentLikeArray = [];
//已点赞弹幕数组(did)
var danmakuLikeArray = [];
//已关注用户数组(id)
var userFocuesArray = [];

$(function () {

    var videos = [{
        "uid": 1,
        "webpage": "#",
        "title": "aaa",
        "author": "eiji",
        "headshot": "images/1.jpg",
        "view": 14500,
        "danmakunum": "58w",
        "popularitynum": "100w",
        "date": "2018-5-5",
        "intro": "dasddsdsdsdsdd"
    }]

    var comments = [{
        "uid": 1,
        "cid": 1,
        "rcid": "null",
        "uname": "yozora",
        "headshot": "images/1.jpg",
        "content": "1111111111ssssssssssssss<br/>ssssssssssssssssssss按时打算打我打多久爱哦大家哦矮点殴打搜到爱街道ID骄傲死的骄傲is大家奥斯的骄傲is的骄傲is觉得个安静到时间到四点就暗示",
        "date": "2018-3-1 10:10",
        "likenum": "11"
    }, {
        "uid": 91,
        "cid": 2,
        "rcid": "null",
        "uname": "eiji",
        "headshot": "images/2.jpg",
        "content": "2222222222222222,91",
        "date": "2018-3-2 10:10",
        "likenum": "222"
    }, {
        "uid": 92,
        "cid": 3,
        "rcid": "null",
        "uname": "zzz",
        "headshot": "images/3.jpg",
        "content": "333333333333333333333333333333,92",
        "date": "2018-3-3 10:10",
        "likenum": 3333
    }, {
        "uid": 93,
        "cid": 4,
        "rcid": "null",
        "uname": "qqq",
        "headshot": "images/4.jpg",
        "content": "444444444444444444444444444444444444444,93",
        "date": "2018-3-4 10:10",
        "likenum": 44444
    }, {
        "uid": 94,
        "cid": 5,
        "rcid": 1,
        "uname": "fff",
        "headshot": "images/5.jpg",
        "content": "55555555,94",
        "date": "2018-3-5 10:10",
        "likenum": 55
    }, {
        "uid": 95,
        "cid": 6,
        "rcid": 1,
        "uname": "gggg",
        "headshot": "images/6.jpg",
        "content": "666666666666666666,95",
        "date": "2018-3-6 10:10",
        "likenum": 66
    }, {
        "uid": 90,
        "cid": 7,
        "rcid": 6,
        "uname": "yozora",
        "headshot": "images/1.jpg",
        "content": "7777777777,90",
        "date": "2018-3-7 10:10",
        "likenum": 77777
    }, {
        "uid": 20,
        "cid": 8,
        "rcid": 7,
        "uname": "8888",
        "headshot": "images/1.jpg",
        "content": "8888888888,20",
        "date": "2018-3-7 10:10",
        "likenum": 8888
    }, {
        "uid": 21,
        "cid": 9,
        "rcid": 8,
        "uname": "9999",
        "headshot": "images/1.jpg",
        "content": "9999999999,21",
        "date": "2018-3-7 10:10",
        "likenum": 99
    }, {
        "uid": 22,
        "cid": 10,
        "rcid": 7,
        "uname": "10101010101010",
        "headshot": "images/1.jpg",
        "content": "101010110,22",
        "date": "2018-3-7 10:10",
        "likenum": 1010
    },]

    // //评论数组(id,uname)
    // var commentArray = []; //评论视频的评论
    // var commentReplyArray = []; //回复评论的评论
    // 弹幕列表 - 弹幕屏蔽
    $(".video-danmaku-list").click(function () {
        $(".danmaku-list").css("display", "inline");
        $(".danmaku-shield").css("display", "none");
    });
    $(".video-danmaku-shield").click(function () {
        $(".danmaku-list").css("display", "none");
        $(".danmaku-shield").css("display", "inline");
    });

    if (loginUid == 0) {
        $("#UheadShot").attr("src", "images/defaultHeadshot.jpg");
    } else {
        $("#UheadShot").attr("src", Uheadshot);
    }

    //获取视频信息
    // $.ajax({
    //     type: "POST",
    //     url: "",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //         "vid": videoDemonId
    //     }),
    //     dataType: "json",
    //     async: true,
    //     cache: false,
    //     success: function (data) {
    //         if (data.result == 2) {
    //             //得到视频信息
    //             var videos = data;
    //             //渲染视频信息
    //             $(".title").text(videos[0].title); //视频标题
    //             // $(".video-title").val() = videos.title; //视频scr
    //             $(".view").text(videos[0].view); //视频播放量
    //             $(".danmakunum").text(videos[0].danmakunum); //视频弹幕数
    //             $(".popularitynum").text(videos[0].popularitynum); //视频人气值
    //             $(".date").text(videos[0].date); //视频发布时间
    //             $(".up-name").text(videos[0].author); //视频up名
    //             $(".upheadshot").attr("src", videos[0].headshot);  //视频up头像
    //             $(".video-intro-content").html(videos[0].intro);//视频简介
    $("#attentionBtn").attr("name", videos[0].uid);//up id
    //         }
    //     },
    //     error: function (json) {
    //         alert("视频获取失败");
    //     }
    // });

    //获取视频相关信息并增加点击量
    $.ajax({
        type: "POST",
        url: "/Video/initVideoInfo",
        contentType: "application/json",
        data: JSON.stringify({
            "vid": videoDemonId
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            // console.log(data);
            $(".view").text(data.view); //视频播放量
            $(".danmakunum").text(data.danmakunum); //视频弹幕数
            $(".duration").text(data.duration);//视频时长
            $(".date").text(data.date);//投稿时间
        },
        error: function (json) {
            alert("增获取视频相关信息并增加点击量出错");
        }
    });

    //添加视频播放历史记录
    if (loginUid != null && loginUid != undefined && loginUid != "") {
        $.ajax({
            type: "POST",
            url: "/PersonalCenter/addHistory",
            contentType: "application/json",
            data: JSON.stringify({
                "vid": videoDemonId,
                "uid": loginUid
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {

            },
            error: function (json) {
                alert("添加视频播放历史记录错误!");
            }
        });
    }

    //判断当前视频是否已收藏
    // $.ajax({
    //     type: "POST",
    //     url: "",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //         "uid": loginUid,
    //         "vid": videoDemonId
    //     }),
    //     dataType: "json",
    //     async: true,
    //     cache: false,
    //     success: function (data) {
    //         if (data[0].result == 1) {
    //             $(".video-collect").val("已收藏");
    //         }
    //     },
    //     error: function (json) {
    //         alert("判断当前视频是否已收藏失败");
    //     }
    // });

    //判断当前视频是否已点赞
    // $.ajax({
    //     type: "POST",
    //     url: "/Video/checkFocus",
    //     contentType: "application/json",
    //     data: JSON.stringify({
    //         "uid": loginUid,
    //         "vid": videoDemonId
    //     }),
    //     dataType: "json",
    //     async: true,
    //     cache: false,
    //     success: function (data) {
    //         if (data[0].result == 1) {
    //             $(".video-like").val("已点赞");
    //             $(".video-like").attr('disabled', true);
    //         }
    //     },
    //     error: function (json) {
    //         alert("判断当前视频是否已点赞失败");
    //     }
    // });


    //获取弹幕信息
    //videoDemonId 自动生成的视频id
    $.ajax({
        type: "POST",
        url: "/Video/getDanmaku",
        contentType: "application/json",
        data: JSON.stringify({
            "vid": videoDemonId
        }),
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            //得到旧弹幕信息
            var danmaku = data;
            danmakus = danmakus.concat(danmaku);//添加到弹幕数组
            addDanmakuList(danmakus);//添加旧弹幕列表

            danmakuLikeStyle(danmakuLikeArray);//修改弹幕点赞样式
        },
        error: function (json) {
            alert("弹幕获取失败");
        }
    });

    /*
        //添加弹幕列表
        function addDanmakuList(danmakus) {
            for (var index = 0; index < danmakus.length; index++) {
                var id = danmakus[index].id; //获取弹幕id
                var content = danmakus[index].content; //获取弹幕内容
                var duration = changeVideoCurrentTime(danmakus[index].duration); //获取弹幕对应视频播放时间
                var date = danmakus[index].date; //获取弹幕发布时间
                var color = danmakus[index].color; //获取弹幕颜色
    
                //生成danmaku div
                var resultDivHead = "<div id=" + id + " class=danmaku-div nav>";
                var resultDanmakuTime = "<div class='danmaku danmakuHind danmaku-time'>&nbsp;" + duration + "</div>";
                var resultDanmakuContent = "<div class='danmaku danmakuHind danmaku-content'>&nbsp;" + content + "<input type='button' class='like likeHind nav' value='点赞'></div>";
                var resultDanmakuDate = "<div class='danmaku danmakuHind danmaku-date'>&nbsp;" + date + "<input type='button' class='inform informHind nav' value='举报'></div>";
                var resultDivEnd = "</div>";
    
                $(".danmaku-items").append(resultDivHead + resultDanmakuTime + resultDanmakuContent + resultDanmakuDate + resultDivEnd);
            }
        }
    */

    // //发送弹幕
    // // $("#danmakuBtn").click(function () {
    // $(document).on("click", "#danmakuBtn", function () {
    //     if (loginUid != null) {
    //         var content = $("#danmakuInput").val(); //获得弹幕内容
    //         var video = document.getElementById("video");
    //         var currenttime = Math.round(video.currentTime);//获取视频当前播放时间
    //         var color = addDanmakuColor($("#danmakuColor").val()); //获取弹幕颜色
    //         var position = $("#danmakuPosition").val();//获取弹幕位置
    //
    //         //添加弹幕后台数据库
    //         $.ajax({
    //             type: "POST",
    //             url: "/Video/addDanmaku",
    //             contentType: "application/json",
    //             data: JSON.stringify({
    //                 "vid": videoDemonId,
    //                 "uid": loginUid,
    //                 "content": content,
    //                 "currenttime": currenttime,
    //                 "color": color,
    //                 "position": position,
    //             }),
    //             dataType: "json",
    //             async: true,
    //             cache: false,
    //             success: function (data) {
    //                 if (data.result == 2) {
    //                     //得到弹幕信息
    //                     var danmaku = data;
    //                     // var danmaku = [{
    //                     //     "id": 0,
    //                     //     "content": content,
    //                     //     "duration": currenttime,
    //                     //     "date": "2018-5-27",
    //                     //     "color": color,
    //                     //     "position": position
    //                     // }];
    //                     danmakus = danmakus.concat(danmaku);//添加到弹幕数组
    //                     console.log(danmakus);
    //                     addDanmakuList(danmaku);//添加新弹幕列表
    //                     addNewDanmaku(danmaku[0]);//添加视频新弹幕
    //                 }
    //             },
    //             error: function (json) {
    //                 alert("弹幕获取失败");
    //             }
    //         });
    //     } else {
    //         alet("请先登录！");
    //     }
    // });

    /*
        var id = 1;//弹幕初始id
        var num = 1; //弹幕id(可变)
        var div_pre = 'danmu-'; //弹幕id前缀
    
        //添加视频新弹幕
        function addNewDanmaku(content, position, color) {
            if (position == "roll") {
                var index = 0;//弹幕位置的高度的下标
                var danmuHeight = $(".danmu").height();
                var toptxt = ["0", danmuHeight, danmuHeight * 2, danmuHeight * 3, danmuHeight * 4, danmuHeight * 5, danmuHeight * 6, danmuHeight * 7, danmuHeight * 8];//弹幕可选位置的高度
                var top;//弹幕实际位置的高度
    
                var haveDiv = $(".danmu");
                out:
                for (var i = num; i < id; i++) {
                    if ($("#" + div_pre + i).length > 0) {
                        var left = $("#" + div_pre + i).offset().left;
                        if (left <= ($("#video").width() - $("#" + div_pre + i).width())) {
                            top = toptxt[0];
                            break out;
                        } else {
                            index = index + 1;
                            top = toptxt[index];
                        }
                    } else {
                        top = toptxt[0];
                    }
                }
    
                var resultDanmaku = "<div id='" + div_pre + id + "' class='danmu' style='color:" + color + ";top:" + top + "px;'>" + content + "</div>";//生成danmu div
                $("#video").before(resultDanmaku); //添加弹幕到视频上
    
                num = id - index;
                id = id + 1;
                if (index >= toptxt.length) { //弹幕位置高度重置
                    index = 0;
                }
    
                var danmakuWidth = "400"; //获取弹幕最大宽度
                $(".danmu").animate({ left: '-' + danmakuWidth + "px" }, 15000, function () { //滚动弹幕动画样式
                    $(this).remove();
    
                    if (haveDiv.length > 0) { //视频框里没有弹幕时，重置弹幕位置的高度
                    } else {
                        index = 0;
                    }
                });
            }
            $("#danmakuInput").val("");//把输入框内容设置为空
        }
    
        //设置弹幕颜色
        function addDanmakuColor(colorSelete) {
            if (colorSelete == "white") {
                return "white";
            } else if (colorSelete == "red") {
                return "red";
            } else if (colorSelete == "green") {
                return "green";
            } else if (colorSelete == "blue") {
                return "blue";
            } else if (colorSelete == "yellow") {
                return "yellow";
            } else {
                return '#' + (function (h) { //随机色
                    return new Array(7 - h.length).join("0") + h
                })((Math.random() * 0x1000000 << 0).toString(16))
            }
        }
    
        //设置弹幕位置
        // function addDanmakuPosition(position){
    
        // }
    
        //转换视频时间
        function changeVideoCurrentTime(currenttime) {
            var l = currenttime;     //计算奔视频有多少秒  
            var hour = Math.floor(l / 3600);                //计算有多少个小时  
            var min = Math.floor(((l - hour * 3600) / 60));     //计算有多少分钟  
            var sec = l % 60;     //计算有多少秒  
            var h1 = "" + hour;
            var m1 = "" + min;
            var s1 = "" + sec;
            if (hour < 10) {
                h1 = "0" + hour;
            }
            if (min < 10) {
                m1 = "0" + min;
            }
            if (sec < 10) {
                s1 = "0" + sec;
            }
            var normalTime = h1 + ":" + m1 + ":" + s1;
    
            return normalTime;
        }
    */

    // //视频点赞
    // // $(".video-like").click(function () {
    // $(document).on("click", ".video-like", function () {
    //     var vid = videoDemonId;//视频ID
    //     var uid = loginUid;//用户id
    //     $.ajax({
    //         type: "POST",
    //         url: "/Video/likeVideo",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "vid": vid,
    //             "uid": uid
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             //操作判断
    //             if (data.result == 1) {
    //                 alert("点赞成功！");
    //             } else {
    //                 alert("点赞失败！");
    //             }
    //         },
    //         error: function (json) {
    //             alert("点赞出错!");
    //         }
    //     });
    // });
    //
    // //视频收藏
    // // $(".video-collect").click(function () {
    // $(document).on("click", ".video-collect", function () {
    //     var vid = videoDemonId;//视频ID
    //     var uid = loginUid;//用户id
    //     $.ajax({
    //         type: "POST",
    //         url: "/Video/addFavorite",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "vid": vid,
    //             "uid": uid
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             //操作判断
    //             if (data.result == 1) {
    //                 alert("收藏成功！");
    //             }
    //             if (data.result == 0) {
    //                 alert("收藏失败！");
    //             }
    //         },
    //         error: function (json) {
    //             alert("收藏出错!");
    //         }
    //     });
    // });

    //弹幕选中样式1
    // $(".danmaku-div").hover(
    //     function () {
    //         $(this).find(".danmaku").removeClass("danmakuHind").addClass("danmakuHover");
    //         $(this).find(".inform").removeClass("informHind").addClass("informHover");
    //         $(this).find(".like").removeClass("likeHind").addClass("likeHover");
    //         $(this).find(".danmaku-date").removeClass("danmaku-date-hind").addClass("danmaku-date-hover");
    //     },
    //     function () {
    //         $(this).find(".danmaku").removeClass("danmakuHover").addClass("danmakuHind");
    //         $(this).find(".inform").removeClass("informHover").addClass("informHind");
    //         $(this).find(".like").removeClass("likeHover").addClass("likeHind");
    //         $(this).find(".danmaku-date").removeClass("danmaku-date-hover").addClass("danmaku-date-hind");
    //     }
    // );

    // //弹幕点赞
    // // $(".like").click(function () {
    // $(document).on("click", ".like", function () {
    //     //获取点击弹幕id
    //     var danmakuId = $(this).closest('.danmaku-div').attr('id');
    //     $.ajax({
    //         type: "POST",
    //         url: "/Video/likeDanmaku",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "vid": videoDemonId,
    //             "did": danmakuId
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             //操作判断
    //             if (data.result == 1) {
    //                 alert("点赞成功！");
    //             }
    //             if (data.result == 0) {
    //                 alert("点赞失败！");
    //             }
    //         },
    //         error: function (json) {
    //             alert("点赞出错!");
    //         }
    //     });
    // });

    // //弹幕举报
    // // $(".inform").click(function () {
    // $(document).on("click", ".inform", function () {
    //     //获取点击弹幕id
    //     var danmakuId = $(this).closest('.danmaku-div').attr('id');
    //
    //     $.ajax({
    //         type: "POST",
    //         url: "/Video/reportDanmaku",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "vid": videoDemonId,
    //             "did": danmakuId
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             //操作判断
    //             if (data.result == 1) {
    //                 alert("举报成功！");
    //             }
    //             if (data.result == 0) {
    //                 alert("举报失败！");
    //             }
    //         },
    //         error: function (json) {
    //             alert("举报出错!");
    //         }
    //     });
    // });

    //获取视频评论
    $.ajax({
        type: "POST",
        url: "/Video/getComment",
        contentType: "application/json",
        data: JSON.stringify({
            "vid": videoDemonId
        }),
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            //获取评论
            var comments = data;

            addComment(comments);
            //评论栏初始化样式(收缩)
            commentReplyHindAndShow();
            // //修改评论点赞按钮样式
            commentLikeStyle(commentLikeArray);
            //修改用户关注按钮样式
            userFocuesStyle(userFocuesArray);
        },
        error: function (json) {
            alert("获取评论出错!");
        }
    });

    // //添加评论
    // function addComment(comments) {
    //     for (var index = 0; index < comments.length; index++) {
    //         var rcid = comments[index].rcid; //被回复的评论的id
    //         if (rcid == null) { //筛选出视频评论
    //             var comment = comments[index];
    //             addVideoComment(comment);
    //         } else {//回复评论的评论
    //             var comment = comments[index];
    //             addReplyComment(comment);
    //         }
    //     }
    // }

    // //添加视频评论
    //     // function addVideoComment(comment) {
    //     //     var uid = comment.uid; //评论者的id
    //     //     var cid = comment.cid; //评论的id
    //     //     var uname = comment.sUname; //评论者的用户名
    //     //     var headshot = comment.sHeadshot; //评论者的头像
    //     //     var content = comment.content; //评论内容
    //     //     var date = comment.date; //评论发布的时间
    //     //     var likenum = comment.likenum; //评论的点赞数
    //     //
    //     //     commentArray.push({
    //     //         cid: cid,
    //     //         uid: uid,
    //     //         uname: uname
    //     //     });
    //     //
    //     //     //生成评论栏
    //     //     var resultCommentHead = "<div id='comment" + cid + "' class='video-comment-item'>";
    //     //     var resultHeadShot = "<div class='headShot-div'><div class='headShot'><img id='headShot' name='" + uid + "' class='nav headShot-send' src='" + headshot + "'/></div><div class='attention'><input type='button' name='" + uid + "' class='attentionBtn up-attention nav' value='关注'></div></div>"
    //     //     var resultCommentItemHead = "<div class='comment-item'>";
    //     //     var resultCommentUnameAndContent = "<div class='comment-detail comment-uname' name='" + uid + "'>" + uname + "</div><div class='comment-detail comment-content'>" + content + "</div>";
    //     //     var resultCommentDateHead = "<div class='comment-detail comment-date'>";
    //     //     var resultCommentDateContent = "<div class='date-content'>" + date + "</div>";
    //     //     var resultCommentLike = "<div class='comment-like'><input type='button' name='" + cid + "' class='commentLikeBtn nav' value='点赞'><span class='comment-like-num'>" + likenum + "</span></div>";
    //     //     var resultCommentInformAndReply = "<div class='comment-inform'><input type='button' name='" + cid + "' class='commentInformBtn nav' value='举报'></div><div class='comment-reply'><input type='button' name='" + cid + "' class='commentReplyBtn nav' value='回复'></div>";
    //     //     var resultCommentDateEnd = "</div>";
    //     //     var resultCommentItemEnd = "</div>";
    //     //
    //     //     //生成回复栏
    //     //     var resultReplyHead = "<div class='video-comment-submit video-comment-submit-reply video-comment-submit-reply-hind'>";
    //     //     var resultReplyHeadShot = "<div class='headShot-div'><div class='headShot headShot-submit-reply'><img id='UheadShot' class='nav' src='" + Uheadshot + "' /></div></div>";
    //     //     var resultReplyContentAndBtn = "<div class='video-comment-content'><textarea class='replyComment' name='replyComment'></textarea></div><div class='video-comment-btn'><input type='button' class='commentBtn nav' value='发布评论'></div>";
    //     //     var resultReplyEnd = "</div>";
    //     //
    //     //     var resultCommentEnd = "</div>";
    //     //
    //     //     //渲染评论区
    //     //     $(".video-comment").append(resultCommentHead + resultHeadShot + resultCommentItemHead + resultCommentUnameAndContent + resultCommentDateHead +
    //     //         resultCommentDateContent + resultCommentLike + resultCommentInformAndReply + resultCommentDateEnd + resultCommentItemEnd +
    //     //         resultReplyHead + resultReplyHeadShot + resultReplyContentAndBtn + resultReplyEnd + resultCommentEnd);
    //     // }

    // //添加回复评论
    // function addReplyComment(comment) {
    //     var uid = comment.uid; //评论者的id
    //     var cid = comment.cid; //评论的id
    //     var rcid = comment.rcid; //被回复的评论的id
    //     var uname = comment.sUname; //评论者的用户名
    //     var headshot = comment.sHeadshot; //评论者的头像
    //     var content = comment.content; //评论内容
    //     var date = comment.date; //评论发布的时间
    //     var likenum = comment.likenum; //评论的点赞数
    //
    //     commentReplyArray.push({
    //         cid: cid,
    //         uid: uid,
    //         uname: uname
    //     });
    //
    //     // console.log(commentReplyArray);
    //
    //     //生成回复评论栏
    //     var resultCommentHead = "<div id='comment" + cid + "' class='comment-reply-item video-comment-item'>";
    //     var resultHeadShot = "<div class='headShot-div headShot-div-reply'><div class='headShot headShot-reply'><img id='headShot' name='" + uid + "' class='nav headShot-send' src='" + headshot + "'/></div></div>"
    //     var resultCommentItemHead = "<div class='comment-item comment-item-reply'>";
    //     var resultCommentUnameAndContent = "<div class='comment-detail comment-uname comment-uname-reply' name='" + uid + "'>" + uname + "</div><div class='comment-detail comment-content comment-content-reply'>" + content + "</div>";
    //     var resultCommentDateHead = "<div class='comment-detail comment-date comment-date-reply'>";
    //     var resultCommentDateContent = "<div class='date-content'>" + date + "</div>";
    //     var resultCommentLike = "<div class='comment-like'><input type='button' name='" + cid + "' class='commentLikeBtn nav' value='点赞'><span class='comment-like-num'>" + likenum + "</span></div>";
    //     var resultCommentInformAndReply = "<div class='comment-inform'><input type='button' name='" + cid + "' class='commentInformBtn nav' value='举报'></div><div class='comment-reply'><input type='button' name='" + cid + "' class='commentReplyBtn nav' value='回复'></div>";
    //     var resultCommentDateEnd = "</div>";
    //     var resultCommentItemEnd = "</div>";
    //     var resultCommentEnd = "</div>";
    //
    //     for (var index = 0; index < commentArray.length; index++) {
    //         if (rcid == commentArray[index].cid) {
    //             // console.log(commentArray.length);
    //             // console.log(rcid);
    //             // console.log(comment);
    //             var id = "comment" + rcid;
    //             var unameReply = commentArray[index].uname;
    //             // console.log(id);
    //             $("#" + id).children(".comment-item").append(resultCommentHead + resultHeadShot + resultCommentItemHead + resultCommentUnameAndContent + resultCommentDateHead + resultCommentDateContent +
    //                 resultCommentLike + resultCommentInformAndReply + resultCommentDateEnd + resultCommentItemEnd + resultCommentEnd);
    //         }
    //     }
    //
    //     for (var index = 0; index < commentReplyArray.length; index++) {
    //         if (rcid == commentReplyArray[index].cid) {
    //             var id = "comment" + rcid;
    //             var unameReply = commentReplyArray[index].uname;
    //             var resultCommentUnameAndContent = "<div class='comment-detail comment-uname comment-uname-reply' name='" + uid + "'>" + uname + "<span class='replySpan'>&nbsp回复:&nbsp</span>" + unameReply + "</div><div class='comment-detail comment-content comment-content-reply'>" + content + "</div>";
    //             $("#" + id).parent().append(resultCommentHead + resultHeadShot + resultCommentItemHead + resultCommentUnameAndContent + resultCommentDateHead + resultCommentDateContent +
    //                 resultCommentLike + resultCommentInformAndReply + resultCommentDateEnd + resultCommentItemEnd + resultCommentEnd);
    //         }
    //     }
    // }

    // //textarea高度自适应文本
    // $(".videoComment,.replyComment").each(function () {
    //     this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    // }).on('input', function () {
    //     this.style.height = 'auto';
    //     this.style.height = (this.scrollHeight) + 'px';
    // });

    // //发布视频评论
    // // $(".videoCommentBtn").click(function () {
    // $(document).on("click", ".videoCommentBtn", function () {
    //     var vid = videoDemonId;//获取视频id
    //     var uid = loginUid; //获取用户id
    //     var content = $(".videoComment").val();
    //     if (content != "") {
    //         var result = changeCommentContent(content);//转换换行符
    //         // $(".video-intro-content").html(result);
    //         $.ajax({
    //             type: "POST",
    //             url: "/Video/addComment",
    //             contentType: "application/json",
    //             data: JSON.stringify({
    //                 "uid": uid,
    //                 "vid": vid,
    //                 "rcid": 0, //回复视频的评论，rcid = 0
    //                 "content": result,
    //             }),
    //             dataType: "json",
    //             async: true,
    //             cache: false,
    //             success: function (data) {
    //                 if (data.result == 1) {
    //                     alert("评论成功！");
    //                     var comment = data;
    //                     addVideoComment(comment);
    //                 } else {
    //                     alert("评论失败！")
    //                 }
    //             },
    //             error: function (json) {
    //                 alert("评论出错！");
    //             }
    //         });
    //     } else {
    //         alert("评论不能为空！");
    //     }
    // });

    // //关注
    // // $(".attentionBtn").click(function () {
    // $(document).on("click", ".attentionBtn", function () {
    //     var uid = Number($(this).attr("name")); //获取被关注者的id
    //     var fid = loginUid; //获取关注者（粉丝）的id
    //     // alert(uid);
    //     $.ajax({
    //         type: "POST",
    //         url: "/Video/focusUser",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "uid": uid,
    //             "fid": fid
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             if (data.result == 1) {
    //                 alert("关注成功！");
    //             }
    //             if (data.result == 0) {
    //                 alert("关注失败！")
    //             }
    //         },
    //         error: function (json) {
    //             alert("关注出错！");
    //         }
    //     });
    // });

    // //评论点赞
    // // $(".commentLikeBtn").click(function () {
    // $(document).on("click", ".commentLikeBtn", function () {
    //     var cid = $(this).attr("name"); //获取被点赞的评论的id
    //     var commentLikeNum = $(this).parent().children(".comment-like-num");
    //     $.ajax({
    //         type: "POST",
    //         url: "/Video/likeComment",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "cid": cid
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             if (data.result == 1) {
    //                 alert("点赞成功！");
    //                 var likenum = data.likenum; //获取当前点赞数
    //                 commentLikeNum.html(likenum); //渲染点赞数
    //             }
    //             if (data.result == 0) {
    //                 alert("点赞失败！")
    //             }
    //         },
    //         error: function (json) {
    //             alert("关注出错！");
    //         }
    //     });
    // });

    // //评论举报
    // // $(".commentInformBtn").click(function () {
    // $(document).on("click", ".commentInformBtn", function () {
    //     var cid = $(this).attr("name"); //获取被举报的评论的id
    //     $.ajax({
    //         type: "POST",
    //         url: "/reportComment",
    //         contentType: "application/json",
    //         data: JSON.stringify({
    //             "cid": cid
    //         }),
    //         dataType: "json",
    //         async: true,
    //         cache: false,
    //         success: function (data) {
    //             if (data.result == 1) {
    //                 alert("举报成功！");
    //             } else {
    //                 alert("举报失败！")
    //             }
    //         },
    //         error: function (json) {
    //             alert("举报出错！");
    //         }
    //     });
    // });

    // //回复
    // // $(".commentReplyBtn").click(function () {
    // $(document).on("click", ".commentReplyBtn", function () {
    //     var cid = $(this).attr("name");//获取被回复的评论的cid
    //
    //     var replyName = $(this).parents(".comment-reply-item").find(".replyName").text();
    //     if (replyName == "") {
    //         replyName = $(this).parents(".comment-item").find(".videoReplyName").text();
    //     }
    //     var resultReplyName = "当前回复: " + replyName;
    //     //显示对应回复栏
    //     $(this).parents(".video-comment-item").children(".video-comment-submit-reply").removeClass("video-comment-submit-reply-hind").addClass("video-comment-submit-reply-show").attr("name", cid);
    //     $(this).parents(".video-comment-item").children(".video-comment-submit-reply").find(".replyComment").val("").attr('placeholder', resultReplyName);
    //     //隐藏其他无关回复栏
    //     $(this).parents(".video-comment-item").siblings().children(".video-comment-submit-reply").removeClass("video-comment-submit-reply-show").addClass("video-comment-submit-reply-hind");
    //
    // });

    // //发布回复评论
    // // $(".commentBtn").click(function () {
    // $(document).on("click", ".commentBtn", function () {
    //     var rcid = $(this).parents(".video-comment-submit-reply").attr("name");//被回复的评论的id
    //     var uid = loginUid; //回复者的id
    //     var vid = videoDemonId; //视频id
    //     var rid = 0;//被回复的评论者的id
    //     var content = $(this).parents(".video-comment-submit-reply").find(".replyComment").val();
    //
    //     var comments = commentArray.concat(commentReplyArray);
    //     for (var index = 0; index < comments.length; index++) {
    //         var cid = comments[index].cid;
    //         if (rcid == cid) {
    //             rid = comments[index].uid;
    //         }
    //     }
    //
    //     // alert(vid + "," + uid + ",回复，" + rid + ",的评论," + rcid);
    //
    //     if (content != "") {
    //         var result = changeCommentContent(content);//转换换行符
    //         // $(".video-intro-content").html(result);
    //         $.ajax({
    //             type: "POST",
    //             url: "",
    //             contentType: "application/json",
    //             data: JSON.stringify({
    //                 "uid": uid,
    //                 "vid": vid,
    //                 "rcid": rcid, //被回复的评论的id
    //                 "rid": rid,//被回复的评论者的id
    //                 "content": result,
    //             }),
    //             dataType: "json",
    //             async: true,
    //             cache: false,
    //             success: function (data) {
    //                 if (data.result == 1) {
    //                     alert("回复成功！");
    //                     var comment = data;
    //                     addReplyComment(comment);
    //                 } else {
    //                     alert("回复失败！")
    //                 }
    //             },
    //             error: function (json) {
    //                 alert("关注出错！");
    //             }
    //         });
    //     } else {
    //         alert("评论不能为空！");
    //     }
    // });

    // //评论内容转换
    // function changeCommentContent(content) {
    //     var i;
    //     var c;
    //     var result = "";
    //     for (i = 0; i < content.length; i++) {
    //         c = content.substr(i, 1);
    //         if (c == "\n")
    //             result = result + "<br/>";
    //         else if (c != "\n")
    //             result = result + c;
    //     }
    //     return result;
    // };

    // //私信
    // // $(".headShot-send,.comment-uname").click(function () {
    // $(document).on("click", ".headShot-send,.comment-uname", function () {
    //     var rid = $(this).attr("name"); //接收者id
    //     var sid = loginUid; //发送者id
    //     window.localStorage.setItem("rid", rid);
    //     window.localStorage.setItem("sid", sid);
    //     // window.location.href = '#'; //跳转消息发送页面
    //     alert(rid + "," + sid);
    // });

    // //评论栏初始化样式(收缩)
    // function commentReplyHindAndShow() {
    //     $(".video-comment-item").each(function () {
    //         var replyNum = $(this).find(".comment-reply-item").length;
    //         if (replyNum <= 2) {
    //             $(this).find(".video-comment-reply-hind").css("display", "none");
    //         }
    //         if (replyNum > 2) {
    //             for (var index = 2; index < replyNum; index++) {
    //                 $(this).find(".comment-reply-item").eq(index).css("display", "none");
    //             }
    //         }
    //     });
    // }
    commentTextareaHeight();

    if (loginUid == null) {
        $(".videoComment").attr('placeholder', "请先登录");
    } else {
        $(".videoComment").attr('placeholder', "填写你对视频的评论...");
    }
    //判断当前用户是否已关注up
    $.ajax({
        type: "POST",
        url: "/Video/checkStatus",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": loginUid,
            "vid": videoDemonId
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            // console.log(data);
            if (data.focuesAuthor == true) {
                $("#attentionBtn").val("已关注");
            }
            if (data.favoriteVideo == true) {
                $(".video-collect").attr("alt", "已收藏");
                $(".video-collect").attr("src", "images/collection2.png")
            }
            if (data.hasLikeVideo == true) {
                $(".video-like").attr("alt", "已点赞");
                $(".video-like").attr("src", "images/like2.png")
                $(".video-like").attr('disabled', true);
            }
            danmakuLikeStyle(data.did)
            userFocuesStyle(data.hasFocuesUserId)
            commentLikeStyle(data.cid)
            // console.log(commentLikeArray);
            // console.log(danmakuLikeArray);
            // console.log(userFocuesArray);
        },
        error: function (json) {
            alert("判断当前用户是否已关注up失败");
        }
    });

});

//获得当前URL
var localhost = window.location.host;
var ws = new WebSocket("ws://" + localhost + "/VideoWebSocket?vid=" + Number(videoDemonId));

//连接发生错误的回调方法
ws.onerror = function () {
    // setMessageInnerHTML("服务器连接发生错误");
    console.log("服务器连接发生错误");
};

//连接成功建立的回调方法
ws.onopen = function () {
    // setMessageInnerHTML("服务器连接成功");
    console.log("服务器连接成功");
}

//接收到消息的回调方法
ws.onmessage = function (event) {
    var message = JSON.parse(event.data)
    console.log("在线人数 " + message.onlineNum);
    if (!message.onlineNum) {
        var danmaku = JSON.parse(event.data);
        // console.log(danmaku);
        // danmakus = danmakus.concat(danmaku);//添加到弹幕数组
        // console.log(danmakus);
        newDanmku = newDanmku.concat(danmaku);
        addDanmakuList(newDanmku);//添加新弹幕列表
        if (Number(danmaku.uid) == loginUid) {
            addNewDanmaku(danmaku);//添加视频新弹幕
        }
        // console.log(danmaku.uid);
        newDanmakus = newDanmakus.concat(danmaku);//t添加到新发弹幕数组
    }
}
//连接关闭的回调方法
ws.onclose = function () {
    // setMessageInnerHTML("服务器连接关闭");
    console.log("服务器连接关闭");
}


window.onunload = function (ev) {
    console.log("closeWebSocket");
    closeWebSocket();
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
//关闭WebSocket连接
function closeWebSocket() {
    ws.close();
}

var seekingTime = 0;

//获取当前视频时间
function videoTimeUpdate(event) {
    if (Math.round(event.currentTime) == seekingTime) {
        addDanmu();
        seekingTime = seekingTime + 1;
    }
    danmakus = danmakus.concat(newDanmakus);
    newDanmakus = [];
}

//拉动进度条
function videoSeeking(even) {
    seekingTime = Math.round(even.currentTime);
}

//拉动完进度条
function videoSeeked() {
    danmakus = danmakus.concat(newDanmakus);
    newDanmakus = [];
}

//发送弹幕
// $("#danmakuBtn").click(function () {
$(document).on("click", "#danmakuBtn", function () {
    var result = isLogin(loginUid);
    if (result == 1) {
        var content = $("#danmakuInput").val(); //获得弹幕内容
        if (content != "") {
            var video = document.getElementById("video");
            var currenttime = Math.round(video.currentTime);//获取视频当前播放时间
            var color = addDanmakuColor($("#danmakuColor").val()); //获取弹幕颜色
            var position = $("#danmakuPosition").val();//获取弹幕位置
            var vid = videoDemonId;
            var uid = loginUid;

            //添加弹幕后台数据库
            // $.ajax({
            //     type: "POST",
            //     url: "/Video/addDanmaku",
            //     contentType: "application/json",
            //     data: JSON.stringify({
            //         "vid": videoDemonId,
            //         "uid": loginUid,
            //         "content": content,
            //         "currenttime": currenttime,
            //         "color": Number($("#danmakuColor").val()),
            //         "position": Number(position),
            //     }),
            //     dataType: "json",
            //     async: true,
            //     cache: false,
            //     success: function (data) {
            //         if (data.result == 2) {
            //             //得到弹幕信息
            //             var danmaku = data;
            //             // var danmaku = [{
            //             //     "id": 0,
            //             //     "content": content,
            //             //     "duration": currenttime,
            //             //     "date": "2018-5-27",
            //             //     "color": color,
            //             //     "position": position
            //             // }];
            //             danmakus = danmakus.concat(danmaku);//添加到弹幕数组
            //             console.log(danmakus);
            //             addDanmakuList(danmaku);//添加新弹幕列表
            //             addNewDanmaku(danmaku[0]);//添加视频新弹幕
            //         }
            //     },
            //     error: function (json) {
            //         alert("弹幕获取失败");
            //     }
            // });
            var msg = '{' + '"uid":' + uid + ',' + '"vid":' + vid + ',' + '"content":' + '"' + content + '"' + ',' + '"currenttime":' + currenttime + ',' + '"color":' + Number($("#danmakuColor").val()) + ',' + '"position":' + Number(position) + '}';

            ws.send(msg);

            $("#danmakuInput").val("");//把输入框内容设置为空
        } else {
            alert("弹幕内容不能为空！");
        }
    } else {
        alert("请先登录再发送弹幕！");
    }

});


function addDanmu() {
    for (var index = 0; index < danmakus.length; index++) {
        var danmaku = danmakus[index];
        var currenttime = danmaku.currenttime;
        if (currenttime == seekingTime) {
            addNewDanmaku(danmaku);
            console.log(danmaku);
        }
    }
}

var id = 1;//弹幕初始id
var num = 1; //弹幕id(可变)
var div_pre = 'danmu-'; //弹幕id前缀

//添加视频新弹幕
function addNewDanmaku(danmaku) {
    var content = danmaku.content;
    var position = danmaku.position;
    var color = addDanmakuColor(danmaku.color);

    if (position == 1) { //滚动弹幕
        var index = 0;//弹幕位置的高度的下标
        var danmuHeight = $(".danmu").height();
        var toptxt = ["0", danmuHeight, danmuHeight * 2, danmuHeight * 3, danmuHeight * 4, danmuHeight * 5, danmuHeight * 6, danmuHeight * 7, danmuHeight * 8];//弹幕可选位置的高度
        var top;//弹幕实际位置的高度

        var haveDiv = $(".danmu");
        out:
            for (var i = num; i < id; i++) {
                if ($("#" + div_pre + i).length > 0) {
                    var left = $("#" + div_pre + i).offset().left;
                    if (left <= ($("#video").width() - $("#" + div_pre + i).width())) {
                        top = toptxt[0];
                        break out;
                    } else {
                        index = index + 1;
                        top = toptxt[index];
                    }
                } else {
                    top = toptxt[0];
                }
            }

        var resultDanmaku = "<div id='" + div_pre + id + "' class='danmu' style='color:" + color + ";top:" + top + "px;'>" + content + "</div>";//生成danmu div
        $("#video").before(resultDanmaku); //添加弹幕到视频上

        num = id - index;
        id = id + 1;
        if (index >= toptxt.length) { //弹幕位置高度重置
            index = 0;
        }

        var danmakuWidth = "400"; //获取弹幕最大宽度
        $(".danmu").animate({left: '-' + danmakuWidth + "px"}, 15000,'linear', function () { //滚动弹幕动画样式
            $(this).remove();

            if (haveDiv.length > 0) { //视频框里没有弹幕时，重置弹幕位置的高度
            } else {
                index = 0;
            }
        });
    }
}

//设置弹幕颜色
function addDanmakuColor(colorSelete) {
    if (colorSelete == 1) {
        return "white";
    } else if (colorSelete == 2) {
        return "red";
    } else if (colorSelete == 3) {
        return "green";
    } else if (colorSelete == 4) {
        return "blue";
    } else {
        return "yellow";
    }
}

//设置弹幕位置
// function addDanmakuPosition(position){

// }

//转换视频时间
function changeVideoCurrentTime(currenttime) {
    var l = currenttime;     //计算奔视频有多少秒  
    var hour = Math.floor(l / 3600);                //计算有多少个小时  
    var min = Math.floor(((l - hour * 3600) / 60));     //计算有多少分钟  
    var sec = l % 60;     //计算有多少秒  
    var h1 = "" + hour;
    var m1 = "" + min;
    var s1 = "" + sec;
    if (hour < 10) {
        h1 = "0" + hour;
    }
    if (min < 10) {
        m1 = "0" + min;
    }
    if (sec < 10) {
        s1 = "0" + sec;
    }
    var normalTime = h1 + ":" + m1 + ":" + s1;

    return normalTime;
}

//添加弹幕列表
function addDanmakuList(danmakus) {
    // $(".danmaku-items").empty();
    for (var index = 0; index < danmakus.length; index++) {
        var id = danmakus[index].did; //获取弹幕did
        var content = danmakus[index].content; //获取弹幕内容
        var duration = changeVideoCurrentTime(danmakus[index].currenttime); //获取弹幕对应视频播放时间
        var date = danmakus[index].date; //获取弹幕发布时间
        var color = danmakus[index].color; //获取弹幕颜色
        //生成danmaku div
        var resultDivHead = "<div id=" + id + " class=danmaku-div nav>";
        var resultDanmakuTime = "<div class='danmaku danmakuHind danmaku-time'>&nbsp;" + duration + "</div>";
        var resultDanmakuContent = "<div class='danmaku danmakuHind danmaku-content'>&nbsp;" + content + "<input type='button' class='like likeHind nav' value='点赞'></div>";
        var resultDanmakuDate = "<div class='danmaku danmakuHind danmaku-date'>&nbsp;" + date + "<input type='button' class='inform informHind nav' value='举报'></div>";
        var resultDivEnd = "</div>";

        $(".danmaku-items").append(resultDivHead + resultDanmakuTime + resultDanmakuContent + resultDanmakuDate + resultDivEnd);
    }
    $(".danmaku-items").scrollTop($(".danmaku-items")[0].scrollHeight);
    newDanmku = [];
}

//弹幕选中样式1
// $(".danmaku-div").hover(
//     function () {
//         $(this).find(".danmaku").removeClass("danmakuHind").addClass("danmakuHover");
//         $(this).find(".inform").removeClass("informHind").addClass("informHover");
//         $(this).find(".like").removeClass("likeHind").addClass("likeHover");
//         $(this).find(".danmaku-date").removeClass("danmaku-date-hind").addClass("danmaku-date-hover");
//     },
//     function () {
//         $(this).find(".danmaku").removeClass("danmakuHover").addClass("danmakuHind");
//         $(this).find(".inform").removeClass("informHover").addClass("informHind");
//         $(this).find(".like").removeClass("likeHover").addClass("likeHind");
//         $(this).find(".danmaku-date").removeClass("danmaku-date-hover").addClass("danmaku-date-hind");
//     }
// );

$(document).on("mouseover mouseout", ".danmaku-div", function (event) {
    if (event.type == "mouseover") {
        $(this).find(".danmaku").removeClass("danmakuHind").addClass("danmakuHover");
        $(this).find(".inform").removeClass("informHind").addClass("informHover");
        $(this).find(".like").removeClass("likeHind").addClass("likeHover");
        $(this).find(".danmaku-date").removeClass("danmaku-date-hind").addClass("danmaku-date-hover");
    } else if (event.type == "mouseout") {
        $(this).find(".danmaku").removeClass("danmakuHover").addClass("danmakuHind");
        $(this).find(".inform").removeClass("informHover").addClass("informHind");
        $(this).find(".like").removeClass("likeHover").addClass("likeHind");
        $(this).find(".danmaku-date").removeClass("danmaku-date-hover").addClass("danmaku-date-hind");
    }
});


//弹幕点赞
// $(".like").click(function () {
$(document).on("click", ".like", function () {
    //获取点击弹幕id
    var danmakuId = Number($(this).closest('.danmaku-div').attr('id'));
    var danmakuLikeBtn = $(this);
    var result = isLogin(loginUid);
    if (result == 1) {
        $.ajax({
            type: "POST",
            url: "/Video/likeDanmaku",
            contentType: "application/json",
            data: JSON.stringify({
                "vid": videoDemonId,
                "did": danmakuId,
                "uid": loginUid
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {
                //操作判断
                if (data[0].result == 1) {
                    alert("点赞成功！");
                    danmakuLikeBtn.attr("disabled", true);
                }
                if (data[0].result == 0) {
                    alert("点赞失败！");
                }
            },
            error: function (json) {
                alert("点赞出错!");
            }
        });
    } else {
        alert("请先登录再对弹幕点赞!");
    }
});

//弹幕举报
// $(".inform").click(function () {
$(document).on("click", ".inform", function () {
    //获取点击弹幕id
    var danmakuId = Number($(this).closest('.danmaku-div').attr('id'));
    var result = isLogin(loginUid);
    if (result == 1) {
        $.ajax({
            type: "POST",
            url: "/Video/reportDanmaku",
            contentType: "application/json",
            data: JSON.stringify({
                "vid": videoDemonId,
                "did": danmakuId
            }),
            dataType: "json",
            async: true,
            cache: false,
            success: function (data) {
                //操作判断
                if (data[0].result == 1) {
                    alert("举报成功！");
                }
                if (data[0].result == 0) {
                    alert("举报失败！");
                }
            },
            error: function (json) {
                alert("举报出错!");
            }
        });
    } else {
        alert("请先登录再对弹幕举报!");
    }
});

function danmakuLikeStyle(danmakuLikeArray) {
    for (var index = 0; index < danmakuLikeArray.length; index++) {
        $(".like").each(function () {
            if (Number($(this).parents(".danmaku-div").attr("id")) == danmakuLikeArray[index]) {
                $(this).val("已点赞");
                $(this).attr("disabled", true);
            }
        })

    }
}

//视频点赞
// $(".video-like").click(function () {
$(document).on("click", ".video-like", function () {
    var vid = videoDemonId;//视频ID
    var uid = loginUid;//用户id
    var videoLikeBtn = $(this);
    var result = isLogin(loginUid);
    if (result == 1) {
        var value = videoLikeBtn.attr("alt");
        if (value == "点赞") {
            $.ajax({
                type: "POST",
                url: "/Video/likeVideo",
                contentType: "application/json",
                data: JSON.stringify({
                    "vid": vid,
                    "uid": uid
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    //操作判断
                    if (data[0].result == 1) {
                        alert("点赞成功！");
                        // videoLikeBtn.val("已点赞");
                        videoLikeBtn.attr("alt", "已点赞");
                        videoLikeBtn.attr("src", "images/like2.png")
                        videoLikeBtn.attr('disabled', true);
                    }
                    if (data[0].result == 0) {
                        alert("点赞失败！");
                    }
                },
                error: function (json) {
                    alert("点赞出错!");
                }
            });
        }
    } else {
        alert("请先登录再对视频进行点赞！");
    }
});

//视频收藏
// $(".video-collect").click(function () {
$(document).on("click", ".video-collect", function () {
    var vid = videoDemonId;//视频ID
    var uid = loginUid;//用户id
    var videoCollectBtn = $(this);
    var result = isLogin(loginUid);
    if (result == 1) {
        var value = videoCollectBtn.attr("alt");
        if (value == "收藏") {
            $.ajax({
                type: "POST",
                url: "/Video/addFavorite",
                contentType: "application/json",
                data: JSON.stringify({
                    "vid": vid,
                    "uid": uid
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    //操作判断
                    if (data[0].result == 1) {
                        alert("收藏成功！");
                        // videoCollectBtn.val("已收藏");
                        videoCollectBtn.attr("alt", "已收藏");
                        videoCollectBtn.attr("src", "images/collection2.png")
                    }
                    if (data[0].result == 2) {
                        alert("收藏失败！");
                    }
                },
                error: function (json) {
                    alert("收藏出错!");
                }
            });
        }
        if (value == "已收藏") {
            var result = confirm("是否要取消收藏该视频?");
            if (result == true) {
                $.ajax({
                    type: "POST",
                    url: "/Video/removeFavorite",
                    contentType: "application/json",
                    data: JSON.stringify({
                        "vid": vid,
                        "uid": uid
                    }),
                    dataType: "json",
                    async: true,
                    cache: false,
                    success: function (data) {
                        //操作判断
                        if (data[0].result == 1) {
                            alert("取消收藏成功！");
                            // videoCollectBtn.val("收藏");
                            videoCollectBtn.attr("alt", "收藏");
                            videoCollectBtn.attr("src", "images/collection1.png")
                        }
                        if (data[0].result == 2) {
                            alert("取消收藏失败！");
                        }
                    },
                    error: function (json) {
                        alert("取消收藏出错!");
                    }
                });
            }
        }
    } else {
        alert("请先登录再对视频进行收藏!");
    }
});

//textarea高度自适应文本
function commentTextareaHeight() {
    $(".videoComment,.replyComment").each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    }).on('input', function () {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    });
}

//回复
// $(".commentReplyBtn").click(function () {
$(document).on("click", ".commentReplyBtn", function () {
    var result = isLogin(loginUid);
    if (result == 1) {
        var cid = $(this).attr("name");//获取被回复的评论的cid

        var replyName = $(this).parents(".comment-reply-item").find(".replyName").text();
        if (replyName == "") {
            replyName = $(this).parents(".comment-item").find(".videoReplyName").text();
        }
        var resultReplyName = "当前回复: " + replyName;
        //显示对应回复栏
        $(this).parents(".video-comment-item").children(".video-comment-submit-reply").removeClass("video-comment-submit-reply-hind").addClass("video-comment-submit-reply-show").attr("name", cid);
        $(this).parents(".video-comment-item").children(".video-comment-submit-reply").find(".replyComment").val("").attr('placeholder', resultReplyName);
        //隐藏其他无关回复栏
        $(this).parents(".video-comment-item").siblings().children(".video-comment-submit-reply").removeClass("video-comment-submit-reply-show").addClass("video-comment-submit-reply-hind");

        // commentTextareaHeight();
    } else {
        alert("请先登录再对评论进行回复!");
    }
});

//发布回复评论
// $(".commentBtn").click(function () {
$(document).on("click", ".commentBtn", function () {
    var result = isLogin(loginUid);
    if (result == 1) {
        var rcid = $(this).parents(".video-comment-submit-reply").attr("name");//被回复的评论的id
        var uid = loginUid; //回复者的id
        var vid = videoDemonId; //视频id
        var rid = 0;//被回复的评论者的id
        var content = $(this).parents(".video-comment-submit-reply").find(".replyComment").val();
        var replyComment = $(this).parents(".video-comment-submit-reply").find(".replyComment");

        var comments = commentArray.concat(commentReplyArray);
        for (var index = 0; index < comments.length; index++) {
            var cid = comments[index].cid;
            if (rcid == cid) {
                rid = comments[index].uid;
            }
        }

        // alert(vid + "," + uid + ",回复，" + rid + ",的评论," + rcid);

        if (content != "") {
            var result = changeCommentContent(content);//转换换行符
            // $(".video-intro-content").html(result);
            $.ajax({
                type: "POST",
                url: "/Video/addComment",
                contentType: "application/json",
                data: JSON.stringify({
                    "uid": uid,
                    "vid": vid,
                    "rcid": rcid, //被回复的评论的id
                    "rid": rid,//被回复的评论者的id
                    "content": result,
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    alert("回复成功！");
                    var comment = data;
                    addReplyComment(comment);
                    replyComment.val("");
                    replyComment.css("height", "65px");
                },
                error: function (json) {
                    alert("回复出错！");
                }
            });
        } else {
            alert("评论不能为空！");
        }
    } else {
        alert("请先登录再对评论进行回复!");
    }
});

//评论内容转换
function changeCommentContent(content) {
    var i;
    var c;
    var result = "";
    for (i = 0; i < content.length; i++) {
        c = content.substr(i, 1);
        if (c == "\n")
            result = result + "<br/>";
        else if (c != "\n")
            result = result + c;
    }
    return result;
};

//发布视频评论
// $(".videoCommentBtn").click(function () {
$(document).on("click", ".videoCommentBtn", function () {
    var vid = videoDemonId;//获取视频id
    var uid = loginUid; //获取用户id
    var content = $(".videoComment").val();
    var videoComment = $(".videoComment");
    var result = isLogin(loginUid);
    if (result == 1) {
        if (content != "") {
            var result = changeCommentContent(content);//转换换行符
            // $(".video-intro-content").html(result);
            $.ajax({
                type: "POST",
                url: "/Video/addComment",
                contentType: "application/json",
                data: JSON.stringify({
                    "uid": uid,
                    "vid": vid,
                    "rcid": 0, //回复视频的评论，rcid = 0
                    "content": result,
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    alert("评论成功！");
                    var comment = data;
                    addVideoComment(comment);
                    videoComment.val("");
                    videoComment.css("height", "65px");
                },
                error: function (json) {
                    alert("评论出错！");
                }
            });
        } else {
            alert("评论不能为空！");
        }
    } else {
        alert("请先登录再对视频进行评论!");
    }
});

function commentLikeStyle(commentLikeArray) {
    console.log(commentLikeArray);
    for (var index = 0; index < commentLikeArray.length; index++) {
        $(".commentLikeBtn").each(function () {
            if (Number($(this).attr("name")) == commentLikeArray[index]) {
                // $(this).val("已点赞");
                // $(this).attr("disabled", true);
                $(this).attr("alt", "已点赞");
                $(this).attr("src", "images/like2.png");
            }
        });
    }
}

function userFocuesStyle(userFocuesArray) {
    for (var index = 0; index < userFocuesArray.length; index++) {
        $(".attentionBtn").each(function () {
            if (Number($(this).attr("name")) == userFocuesArray[index]) {
                $(this).val("已关注");
            }
        });
    }
}

//关注用户
// $(".attentionBtn").click(function () {
$(document).on("click", ".attentionBtn", function () {
    var uid = Number($(this).attr("name")); //获取被关注者的id
    var fid = loginUid; //获取关注者（粉丝）的id
    var attentionBtn = $(this);
    var value = attentionBtn.val();
    // alert(uid);
    var result = isLogin(loginUid);
    if (result == 1) {
        if (value == "关注") {
            $.ajax({
                type: "POST",
                url: "/Video/focusUser",
                contentType: "application/json",
                data: JSON.stringify({
                    "uid": uid,
                    "fid": fid
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    if (data[0].result == 1) {
                        alert("关注成功！");
                        attentionBtn.val("已关注");
                    }
                    if (data[0].result == 0) {
                        alert("关注失败！")
                    }
                },
                error: function (json) {
                    alert("关注出错！");
                }
            });
        }
        if (value == "已关注") {
            var result = confirm("是否要取消关注该用户?");
            if (result == true) {
                $.ajax({
                    type: "POST",
                    url: "/Video/unfocusUser",
                    contentType: "application/json",
                    data: JSON.stringify({
                        "uid": uid,
                        "fid": fid
                    }),
                    dataType: "json",
                    async: true,
                    cache: false,
                    success: function (data) {
                        if (data[0].result == 1) {
                            alert("取消关注成功！");
                            attentionBtn.val("关注");
                        }
                        if (data[0].result == 0) {
                            alert("取消关注失败！")
                        }
                    },
                    error: function (json) {
                        alert("取消关注出错！");
                    }
                });
            }
        }
    } else {
        alert("请先登录再关注用户!")
    }
});

//评论点赞
// $(".commentLikeBtn").click(function () {
$(document).on("click", ".commentLikeBtn", function () {
    var cid = Number($(this).attr("name")); //获取被点赞的评论的id
    var commentLikeNum = $(this).parent().children(".comment-like-num");
    var commentLikeBtn = $(this);
    var value = commentLikeBtn.attr("alt");
    var result = isLogin(loginUid);
    if (value == "点赞") {
        if (result == 1) {
            $.ajax({
                type: "POST",
                url: "/Video/likeComment",
                contentType: "application/json",
                data: JSON.stringify({
                    "cid": cid,
                    "uid": loginUid
                }),
                dataType: "json",
                async: true,
                cache: false,
                success: function (data) {
                    if (data == -1) {
                        alert("点赞失败！")
                    } else {
                        alert("点赞成功！");
                        // commentLikeBtn.val("已点赞");
                        // commentLikeBtn.attr("disabled", true);
                        commentLikeBtn.attr("alt", "已点赞");
                        commentLikeBtn.attr("src", "images/like2.png");
                        var likenum = data; //获取当前点赞数
                        commentLikeNum.html(likenum); //渲染点赞数
                    }
                },
                error: function (json) {
                    alert("点赞出错！");
                }
            });
        }
    }
});

//评论举报
// $(".commentInformBtn").click(function () {
$(document).on("click", ".commentInformBtn", function () {
    var cid = Number($(this).attr("name")); //获取被举报的评论的id
    var commentInformBtn = $(this);
    $.ajax({
        type: "POST",
        url: "/Video/reportComment",
        contentType: "application/json",
        data: JSON.stringify({
            "cid": cid
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            if (data[0].result == 1) {
                alert("举报成功！");
                // commentInformBtn.val("已举报");
                // commentInformBtn.attr("disable","true");
            }
            if (data[0].result == 0) {
                alert("举报失败！")
            }
        },
        error: function (json) {
            alert("举报出错！");
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

//私信
// $(".headShot-send,.comment-uname").click(function () {

//评论栏初始化样式(收缩)
function commentReplyHindAndShow() {
    $(".video-comment-item").each(function () {
        var replyNum = $(this).find(".comment-reply-item").length;
        if (replyNum <= 2) {
            $(this).find(".video-comment-reply-hind").css("display", "none");
        }
        if (replyNum > 2) {
            for (var index = 2; index < replyNum; index++) {
                $(this).find(".comment-reply-item").eq(index).css("display", "none");
            }
        }
    });
}

//私信用户
$(document).on("click", ".headShot-send", function () {
    var rid = Number($(this).attr("name")); //接收者id
    var result = isLogin(loginUid);
    if (result == 1) {
        var sid = Number(loginUid); //发送者id
        var privateMsgUser = "";
        var pmuname1 = $(this).parent().parent().parent().find(".videoReplyName").text();
        var pmuname2 = $(this).parents(".comment-reply-item").find(".replyName").text();
        if (pmuname1 != undefined && pmuname1 != null && pmuname1 != "") {
            privateMsgUser = pmuname1;
        } else {
            privateMsgUser = pmuname2;
        }
        var privateMsgUserHeadshot = $(this).attr("src");
        if (loginUid != null) {
            // window.localStorage.setItem("rid", rid);
            // window.localStorage.setItem("sid", sid);
            setCookie("rid", rid, 1);
            setCookie("sid", sid, 1);
            setCookie("privateMsg", "privateMsg", 1);
            //需要存储对方的头像，姓名
            setCookie("privateMsgUser", privateMsgUser, 1);
            setCookie("privateMsgUserHeadshot", privateMsgUserHeadshot, 1);
            window.location.href = '/goToMessage'; //跳转消息发送页面
            // alert(rid + "," + sid);
        } else {
            alert("请先登录再私信用户!");
        }
    }
});

// 评论框收起展开
$(document).on("click", ".video-comment-reply-hind", function () {
    if ($(this).parent(".video-comment-item").find(".video-comment-reply-hind").text() == "展开评论") {
        $(this).parent(".video-comment-item").find(".comment-reply-item").css("display", "inline");
        $(this).parent(".video-comment-item").find(".video-comment-reply-hind").text("收起评论");
    } else if ($(this).parent(".video-comment-item").find(".video-comment-reply-hind").text() == "收起评论") {
        var replyNum = $(this).parent(".video-comment-item").find(".comment-reply-item").length;
        for (var index = 2; index < replyNum; index++) {
            $(this).parent(".video-comment-item").find(".comment-reply-item").eq(index).css("display", "none");
        }
        $(this).parent(".video-comment-item").find(".video-comment-reply-hind").text("展开评论");
    }
});

//添加评论
function addComment(comments) {
    for (var index = 0; index < comments.length; index++) {
        var rcid = comments[index].rcid; //被回复的评论的id
        if (rcid == null) { //筛选出视频评论
            var comment = comments[index];
            addVideoComment(comment);
        } else {//回复评论的评论
            var comment = comments[index];
            addReplyComment(comment);
        }
    }
    commentTextareaHeight();
}

//添加视频评论
function addVideoComment(comment) {
    var uid = comment.uid; //评论者的id
    var cid = comment.cid; //评论的id
    var uname = comment.sUname; //评论者的用户名
    var headshot = comment.sHeadshot; //评论者的头像
    var content = comment.content; //评论内容
    var date = comment.date; //评论发布的时间
    var likenum = comment.likenum; //评论的点赞数

    commentArray.push({
        cid: cid,
        uid: uid,
        uname: uname
    });

    //生成评论栏
    var resultCommentHead = "<div id='comment" + cid + "' class='video-comment-item'>";
    var resultHeadShot = "<div class='headShot-div'><div class='headShot'><img id='headShot' name='" + uid + "' class='nav headShot-send' src='" + headshot + "'title='点击头像与TA聊天' /></div><div class='attention'><input type='button' name='" + uid + "' class='attentionBtn up-attention nav' value='关注'></div></div>"
    var resultCommentItemHead = "<div class='comment-item'>";
    var resultCommentUnameAndContent = "<div class='comment-detail comment-uname' name='" + uid + "'>" + "<span class='videoReplyName'>" + uname + "</span>" + "</div><div class='comment-detail comment-content video-user-comment-content'>" + content + "</div>";
    var resultCommentDateHead = "<div class='comment-detail comment-date'>";
    var resultCommentDateContent = "<div class='date-content'>" + date + "</div>";
    var resultCommentLike = "<div class='comment-like'><img name='" + cid + "' src='images/like1.png' class='commentLikeBtn nav' alt='点赞'><span class='comment-like-num'>" + likenum + "</span></div>";
    var resultCommentInformAndReply = "<div class='comment-inform'><img name='" + cid + "'  src='images/form.png' class='commentInformBtn nav' alt='举报'></div><div class='comment-reply'><input type='button' name='" + cid + "' class='commentReplyBtn nav' value='回复'></div>";
    var resultCommentDateEnd = "</div>";
    var resultCommentItemEnd = "</div>";

    //生成回复栏
    var resultReplyHead = "<div class='video-comment-submit video-comment-submit-reply video-comment-submit-reply-hind'>";
    var resultReplyHeadShot = "<div class='headShot-div'><div class='headShot headShot-submit-reply'><img id='UheadShot' class='nav' src='" + Uheadshot + "' /></div></div>";
    var resultReplyContentAndBtn = "<div class='video-comment-content'><textarea class='replyComment' name='replyComment'></textarea></div><div class='video-comment-btn'><input type='button' class='commentBtn nav' value='发布评论'></div>";
    var resultReplyEnd = "</div>";
    var resultReplyHind = "<div class='video-comment-reply-hind nav'>展开评论</div>";
    var resultCommentEnd = "</div>";

    //渲染评论区
    $(".video-comment").append(resultCommentHead + resultHeadShot + resultCommentItemHead + resultCommentUnameAndContent + resultCommentDateHead +
        resultCommentDateContent + resultCommentLike + resultCommentInformAndReply + resultCommentDateEnd + resultCommentItemEnd +
        resultReplyHead + resultReplyHeadShot + resultReplyContentAndBtn + resultReplyEnd + resultReplyHind + resultCommentEnd);
}

//添加回复评论
function addReplyComment(comment) {
    // console.log(comment);
    var uid = comment.uid; //评论者的id
    var cid = comment.cid; //评论的id
    var rcid = comment.rcid; //被回复的评论的id
    var uname = comment.sUname; //评论者的用户名
    var headshot = comment.sHeadshot; //评论者的头像
    var content = comment.content; //评论内容
    var date = comment.date; //评论发布的时间
    var likenum = comment.likenum; //评论的点赞数

    commentReplyArray.push({
        cid: cid,
        uid: uid,
        uname: uname
    });

    // console.log(commentReplyArray);

    //生成回复评论栏
    var resultCommentHead = "<div id='comment" + cid + "' class='comment-reply-item video-comment-item'>";
    var resultHeadShot = "<div class='headShot-div headShot-div-reply'><div class='headShot headShot-reply'><img id='headShot' name='" + uid + "' class='nav headShot-send' src='" + headshot + "' title='点击头像与TA聊天'/></div></div>"
    var resultCommentItemHead = "<div class='comment-item comment-item-reply'>";
    var resultCommentUnameAndContent = "<div class='comment-detail comment-uname comment-uname-reply' name='" + uid + "'>" + "<span class='replyName'>" + uname + "</span>" + "</div><div class='comment-detail comment-content comment-content-reply'>" + content + "</div>";
    var resultCommentDateHead = "<div class='comment-detail comment-date comment-date-reply'>";
    var resultCommentDateContent = "<div class='date-content'>" + date + "</div>";
    var resultCommentLike = "<div class='comment-like'><img name='" + cid + "' src='images/like1.png' class='commentLikeBtn nav' alt='点赞'><span class='comment-like-num'>" + likenum + "</span></div>";
    var resultCommentInformAndReply = "<div class='comment-inform'><img name='" + cid + "'  src='images/form.png' class='commentInformBtn nav' alt='举报'></div><div class='comment-reply'><input type='button' name='" + cid + "' class='commentReplyBtn nav' value='回复'></div>";
    var resultCommentDateEnd = "</div>";
    var resultCommentItemEnd = "</div>";
    var resultCommentEnd = "</div>";

    for (var index = 0; index < commentArray.length; index++) {
        if (rcid == commentArray[index].cid) {
            // console.log(commentArray.length);
            // console.log(rcid);
            // console.log(comment);
            var id = "comment" + rcid;
            var unameReply = commentArray[index].uname;
            // console.log(id);
            $("#" + id).children(".comment-item").append(resultCommentHead + resultHeadShot + resultCommentItemHead + resultCommentUnameAndContent + resultCommentDateHead + resultCommentDateContent +
                resultCommentLike + resultCommentInformAndReply + resultCommentDateEnd + resultCommentItemEnd + resultCommentEnd);
        }
    }

    for (var index = 0; index < commentReplyArray.length; index++) {
        if (rcid == commentReplyArray[index].cid) {
            var id = "comment" + rcid;
            var unameReply = commentReplyArray[index].uname;
            var resultCommentUnameAndContent = "<div class='comment-detail comment-uname comment-uname-reply' name='" + uid + "'>" + "<span class='replyName'>" + uname + "</span>" + "<span class='replySpan'>&nbsp回复:&nbsp</span>" + unameReply + "</div><div class='comment-detail comment-content comment-content-reply'>" + content + "</div>";
            $("#" + id).parent().append(resultCommentHead + resultHeadShot + resultCommentItemHead + resultCommentUnameAndContent + resultCommentDateHead + resultCommentDateContent +
                resultCommentLike + resultCommentInformAndReply + resultCommentDateEnd + resultCommentItemEnd + resultCommentEnd);
        }
    }
}

//判断用户是否登录
function isLogin(uid) {
    var result = 0;
    if (uid != 0 && uid != null && uid != undefined && uid != "") {
        result = 1;
    }
    return result;
}

//全屏与退出全屏按钮样式
$(".video-div").mouseenter(function () {
    $(".video-changeScreen").css("display", "inline");
});
$(".video-div").mouseleave(function () {
    $(".video-changeScreen").css("display", "none");
});

//模拟视频全屏/退出全屏
$(document).on("click", ".video-changeScreen", function () {
    var num = $(this).attr("name");
    if (num == "1") {
        var docElm = document.documentElement;
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        } else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        } else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        } else if (docElm.msRequestFullscreen) {
            docElm.msRequestFullscreen();
        }
        $("#mainDiv").css("position", "static");
        $("#mainDiv").css("height", "0");
        $("#mainDiv").css("overflow", "hidden");
        $(".video-div").addClass("video-div-fullScreen");
        $(".video-danmaku").css("display", "none");
        $(".fixed-list").css("display", "none");
        $(this).find(".video-changeScreen-img").attr("src", "images/returnScreen.png");
        $(this).attr("name", "2");
    } else {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        } else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
        $("#mainDiv").css("position", "relative");
        $("#mainDiv").css("height", "auto");
        $("#mainDiv").css("overflow", "");
        $(".video-div").removeClass("video-div-fullScreen");
        $(".video-danmaku").css("display", "inline");
        $(".fixed-list").css("display", "inline");
        $(this).find(".video-changeScreen-img").attr("src", "images/fullScreen.png");
        $(this).attr("name", "1");
    }
});
