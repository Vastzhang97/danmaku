//显示当前登录用户信息
// var uname = window.localStorage.getItem("uname");
// var password = window.localStorage.getItem("password");
// var headshot = window.localStorage.getItem("headshot");
// var uid = window.localStorage.getItem("uid");
var uname = getCookie("uname");
var loginUid = Number(getCookie("uid"));
var headshot = getCookie("headshot");
var password = getCookie("password");
var rid = getCookie("rid");
var sid = getCookie("sid");
var privateMsgUserName = getCookie("privateMsgUser");
var privateMsgUserHeadshot = getCookie("privateMsgUserHeadshot");
var result = getCookie("privateMsg");
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

var privateMsgUser = [];
var privateMsg = [];

var newPrivateMsg = [];//新发送的消息

$(function () {

    $("#UheadShot").attr("src", headshot);

    //获取私信用户信息
    $.ajax({
        type: "POST",
        url: "/Message/getPrivateMsgUser",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": loginUid
        }),
        dataType: "json",
        async: false,
        cache: false,
        success: function (data) {
            var privateMsgUser = data;
            //当前私信用户
            if (privateMsgUserName != "" && privateMsgUserName != null && privateMsgUserName != undefined) {
                out:
                    for (var index = 0; index < privateMsgUser.length; index++) {
                        var uid = privateMsgUser[index].uid; //私信用户
                        if (uid == rid) {
                            break out;
                        }
                        privateMsgUser.push({
                            uid: rid,
                            uname: privateMsgUserName,
                            headshot: privateMsgUserHeadshot,
                            hasUnRead: 0
                        });

                    }
            }
            if (privateMsgUser.length == 0) {
                if (result == "privateMsg") {
                    privateMsgUser.push({
                        uid: rid,
                        uname: privateMsgUserName,
                        headshot: privateMsgUserHeadshot,
                        hasUnRead: 0
                    });
                    setCookie("privateMsg", "", 1);
                }
            }
            addPrivateMsgUser(privateMsgUser);
        },
        error: function (json) {
            alert("获取私信用户信息出错！");
        }
    });

    if (privateMsgUserName != "" && privateMsgUserName != null && privateMsgUserName != undefined) {
        $(".privateMsg-user-item").each(function () {
            if ($(this).attr("name") == rid) {
                $(this).click();
            }
        });
    }

    // setCookie("rid", "", 1);
    // setCookie("sid", "", 1);
    // setCookie("privateMsgUser", "", 1);
    // setCookie("privateMsgUserHeadshot", "", 1);

});

//设置cookie
function setCookie(name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getTime() + (expiredays * 24 * 60 * 60 * 1000))
    document.cookie = name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}

//添加私信用户
function addPrivateMsgUser(privateMsgUser) {
    if (privateMsgUser.length != 0) {
        $(".mainDiv").removeClass("mainDivBg");
        $(".privateMsg-right").css("display", "inline");
        $(".privateMsg-left").css("display", "inline");
        $(".mainDiv").css("min-height", 680);
    }
    for (var index = 0; index < privateMsgUser.length; index++) {
        var uid = privateMsgUser[index].uid; //私信用户
        var pUname = privateMsgUser[index].uname;
        var pHeadshot = privateMsgUser[index].headshot;
        var status = privateMsgUser[index].hasUnRead;//消息状态1已读，0未读
        var resultTip = "";

        if (status == 0) {
            resultTip = "<div class='privateMsg-tip'>" +
                "    <img src='images/msg.png'>" +
                "</div>";
        }

        var result = "<div class='privateMsg-user-item privateMsg-user-item-blur nav' name='" + uid + "'>" +
            "<div class='privateMsg-user-headshot'>" +
            "    <img src=" + pHeadshot + " >" +
            "</div>" +
            "<div class='privateMsg-user-name'>" +
            pUname +
            resultTip +
            "</div>" +
            "</div>";

        $(".privateMsg-left").append(result);
    }
}

//点击用户头像进入聊天室
$(document).on("click", ".privateMsg-user-item", function () {
    $(".privateMsg-reply-item").removeClass("privateMsg-reply-item-hind");
    $(this).addClass("privateMsg-user-item-focus").removeClass("privateMsg-user-item-blur");
    $(this).siblings().addClass("privateMsg-user-item-blur").removeClass("privateMsg-user-item-focus");
    $(this).find(".privateMsg-tip").remove();
    var uid = Number($(this).attr("name"));
    $(".replyCommentBtn").attr("name", uid);
    $(".privateMsg-content-item").empty();
    // alert(uid);

    //获取与私信用户的聊天消息
    $.ajax({
        type: "POST",
        url: "/Message/readPrivateMsg",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": loginUid,
            "oUid": uid
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            var privateMsg = data;
            addPrivateMsg(privateMsg);
        },
        error: function (json) {
            alert("获取与私信用户的聊天消息出错！");
        }
    });
});

//添加与私信用户的聊天消息
function addPrivateMsg(privateMsg) {
    for (var index = 0; index < privateMsg.length; index++) {
        var mid = privateMsg[index].mid;//评论id
        var sid = privateMsg[index].sid;//发送者id
        var rid = privateMsg[index].rid;//接收者id
        var pUname = privateMsg[index].pUname;//对方用户名
        var context = privateMsg[index].context;//内容
        var date = privateMsg[index].date;//时间

        var resultConetntClass = "left-conetnt";
        var resultDetailClass = "detail-left";
        if (sid == loginUid) {//己方消息靠右
            resultConetntClass = "right-conetnt";
            var resultDetailClass = "detail-right";
            pUname = uname;
        }

        var result = "<div class='privateMsg-content-row'>" +
            "<div class='privateMsg-content-detail " + resultDetailClass + " '>" +
            " <div class='privateMsg-content-title'>" +
            " <div class='privateMsg-content-name'>" + pUname + "</div>" +
            " <div class='privateMsg-content-date'>" + date + "</div>" +
            " </div>" +
            "<div class='privateMsg-content-context " + resultConetntClass + "'>" +
            context +
            " </div>" +
            " </div>" +
            "</div>";

        $(".privateMsg-content-item").append(result);
    }
    $(".privateMsg-content-item").scrollTop($(".privateMsg-content-item")[0].scrollHeight);
}

var localhost = window.location.host;
var ws = new WebSocket("ws://" + localhost + "/MessageWebSocket");

//连接发生错误的回调方法
ws.onerror = function () {
    // setMessageInnerHTML("服务器连接发生错误");
};

//连接成功建立的回调方法
ws.onopen = function () {
    // setMessageInnerHTML("服务器连接成功");
}

//接收到消息的回调方法
ws.onmessage = function (event) {
    var message = JSON.parse(event.data)
    console.log(message);
    newPrivateMsg = newPrivateMsg.concat(message);
    addPrivateMsg(newPrivateMsg);
    newPrivateMsg = [];
}
//连接关闭的回调方法
ws.onclose = function () {
    // setMessageInnerHTML("服务器连接关闭");
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

//发送消息
$(document).on("click", ".replyCommentBtn", function () {
    var rid = Number($(this).attr("name"));
    var content = $(".replyComment").val();
    var sid = loginUid;

    if (content != "") {
        var result = changeCommentContent(content);
        $(".replyComment").val("");
        // alert(loginUid + ",发送了：" + result + "给," + uid);

        //发送消息
        // $.ajax({
        //     type: "POST",
        //     url: "",
        //     contentType: "application/json",
        //     data: JSON.stringify({
        //         "rid": loginUid,
        //         "sid": uid,
        //         "content": content
        //     }),
        //     dataType: "json",
        //     async: true,
        //     cache: false,
        //     success: function (data) {
        //         var privateMsg = data;
        //         addPrivateMsg(privateMsg);
        //     },
        //     error: function (json) {
        //         alert("发送消息出错！");
        //     }
        // });
        var msg = '{' + '"rid":' + rid + ',' + '"sid":' + sid + ',' + '"context":' + '"' + content + '"' + '}';

        ws.send(msg);

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

//textarea高度自适应文本
// $(".replyComment").each(function () {
//     this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
// }).on('input', function () {
//     this.style.height = 'auto';
//     this.style.height = (this.scrollHeight) + 'px';
// });
