$(function () {
    var sysMessage = [{
        "date": "3-1 10:10",
        "content": "222222222"
    }, {
        "date": "3-5 10:10",
        "content": "66666666"
    }, {
        "date": "3-9 10:10",
        "content": "55555555"
    }];

    // var loginUid = Number(window.localStorage.getItem("uid"));
    // var loginUid = 1;s
    var loginUid = Number(getCookie("uid"));
    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = unescape(ca[i].trim());
            if (c.indexOf(name) == 0) return decodeURI(c.substring(name.length, c.length));
        }
        return "";
    }

    //获取系统消息
    var mainDiv = $(".mainDiv");
    $.ajax({
        type: "POST",
        url: "/Message/getNotification",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": loginUid
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            var sysMessage = data;
            if(sysMessage.length != 0){
                mainDiv.removeClass("mainDivBg");
            }
            addSysMessage(sysMessage);
        },
        error: function (json) {
            alert("获取系统消息出错！");
        }
    });

    //渲染系统消息
    function addSysMessage(sysMessage) {
        for (var index = 0; index < sysMessage.length; index++) {
            var title = "系统消息";
            var date = sysMessage[index].date; //得到消息时间
            var content = sysMessage[index].context; //得到消息内容

            //生成sysMsg div
            var resultDiv = "<div class='sysMsg nav'><div class='sysMsg-head'><div class='sysMsg-title'> " + title + " </div><div class='sysMsg-date'> " + date + " </div></div><div class='sysMsg-content'> " + content + " </div></div>"

            $(".mainDiv").append(resultDiv);
        }
    }

});