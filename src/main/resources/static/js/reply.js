//用户信息
// var loginUname = window.localStorage.getItem("username");
// var loginUid = Number(window.localStorage.getItem("uid"));
// var Uheadshot = window.localStorage.getItem("headshot");
var loginUname = getCookie("uname");
var loginUid = Number(getCookie("uid"));
var Uheadshot = getCookie("headshot");
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

$(function () {
    var reply = [{
        "cid": 1,
        "vid": 1,
        "date": "3-1 10:10",
        "headshot": "images/2.jpg",
        "uid": 1,
        "uname": "eiji1",
        "comment": "11111111111",
        "rcid": 20,
        "date": "3-1 10:10",
        "content": "阿萨打算sadsadsa阿斯顿撒",
        "status": "0"
    }, {
        "cid": 2,
        "vid": 2,
        "date": "3-5 10:10",
        "headshot": "images/6.jpg",
        "uid": 2,
        "uname": "cyx",
        "comment": "11111111111",
        "rcid": 15,
        "date": "3-1 10:10",
        "content": "阿萨打算sadsadsa阿斯顿撒",
        "status": "0"
    }, {
        "cid": 10,
        "vid": 3,
        "date": "3-2 10:10",
        "uid": 3,
        "uname": "lee",
        "headshot": "images/3.jpg",
        "comment": "alize",
        "rcid": "null",
        "date": "3-6 10:10",
        "content": "啊实打实打算",
        "status": "0"
    }, {
        "cid": 1,
        "vid": 1,
        "date": "3-1 10:10",
        "uid": 2,
        "uname": "eiji",
        "headshot": "images/4.jpg",
        "comment": "00000000",
        "rcid": 20,
        "date": "3-18 10:10",
        "content": "asdasdasd",
        "status": "1"
    }];

    // var loginUname = "yozora";
    // var loginUid = 1;
    // var Uheadshot = "images/6.jpg";

    //获取收到的评论的信息
    var mainDiv = $(".mainDiv");
    $.ajax({
        type: "POST",
        url: "/Message/getMyReply",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": loginUid
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            var reply = data;
            if(reply.length != 0){
                mainDiv.removeClass("mainDivBg");
            }
            addReply(reply);
        },
        error: function (json) {
            alert("获取收到的评论的信息出错！");
        }
    });

    //渲染评论信息
    function addReply(reply) {
        for (var index = 0; index < reply.length; index++) {
            var uname = reply[index].sUname;//得到回复者的用户名
            var uid = reply[index].uid;//得到回复者的id
            var vid = reply[index].vid;//得到视频id
            var cid = reply[index].cid;//得到评论的id
            var rcid = reply[index].rcid; //得到被回复的评论id（若是null ，则是视频的评论）
            var headshot = reply[index].sHeadshot; //得到回复者的头像
            var date = reply[index].date; //得到回复时间
            var comment = "666";
            if (reply[index].videoTitle != null) {
                comment = reply[index].videoTitle;//得到视频标题
            }
            if (reply[index].rContent != null) {
                comment = reply[index].rContent;//得到被回复的评论内容
            }
            var status = reply[index].status;//得到评论的状态(0)
            var content = reply[index].content; //得到回复内容

            //reply div
            var resultDiv = "<div name=" + vid + " class='reply nav'>" +
                "<div name=" + uid + " class='reply-logo'>" +
                "<img src=" + headshot + ">" +
                "</div>" +
                "<div class='reply-detail'>" +
                "<div class='reply-title'>" +
                "<div class='reply-name'>" + uname + "</div>" +
                "<div class='reply-date'>" + date + "</div>" +
                " <div class='reply-btn'>" +
                "  <input name=" + cid + " class='replyBtn nav' type='button' value='回复'> " +
                " </div>" +
                " </div>" +
                " <div class='reply-content'>" +
                content +
                "</div>" +
                "<div class='reply-myComment'>" +
                comment +
                " </div>" +
                " </div>" +
                "<div class='video-comment-submit video-comment-submit-hind'>" +
                "  <div class='headShot-div'>" +
                "  <div class='headShot'>" +
                "     <img id='UheadShot' class='nav' src=" + Uheadshot + " />" +
                " </div>" +
                "   </div>" +
                "<div class='video-comment-content'>" +
                "   <textarea class='replyComment' name='videoComment'></textarea>" +
                " </div>" +
                "<div class='video-comment-btn'>" +
                "   <input type='button' class='replyCommentBtn nav' value='发布评论'>" +
                " </div>" +
                "</div>" +
                " </div>";

            $(".mainDiv").append(resultDiv);
        }
    }

});

//回复
$(document).on('click', '.replyBtn', function () {
    // alert("545454")
    var cid = $(this).attr("name");//获取被回复的评论的cid
    //显示对应回复栏
    $(this).parents(".reply").children(".video-comment-submit").removeClass("video-comment-submit-hind").addClass("video-comment-submit-show").attr("name", cid);
    $(this).parents(".reply").children(".video-comment-submit").find(".replyComment").val("");
    //隐藏其他无关回复栏
    $(this).parents(".reply").siblings().children(".video-comment-submit").removeClass("video-comment-submit-show").addClass("video-comment-submit-hind");

});

//发布回复评论
$(document).on('click', '.replyCommentBtn', function () {
    var rcid = Number($(this).parents(".video-comment-submit").attr("name"));//被回复的评论的id
    var vid = Number($(this).parents(".reply").attr("name"));//对应视频的id
    var rid = Number($(this).parents(".reply").find(".reply-logo").attr("name"));//被回复的用户的id
    var uid = loginUid; //回复者的id
    var content = $(this).parents(".video-comment-submit").find(".replyComment").val();

    // alert(vid + "," + uid + ",回复，" + rid + ",的评论," + rcid + ",内容为：" + content);
    if (content != "") {
        var result = changeCommentContent(content);//转换换行符
        var replyComment = $(this).parents(".video-comment-submit").find(".replyComment"); //获取回复textarea dom
        var videoCommentSubmit = $(this).parents(".video-comment-submit"); //获取回复框 dom
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
                replyComment.val(""); //清空回复框内容
                videoCommentSubmit.removeClass("video-comment-submit-show").addClass("video-comment-submit-hind"); //隐藏当前评论的回复框

            },
            error: function (json) {
                alert("回复出错！");
            }
        });
    } else {
        alert("评论不能为空！");
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
