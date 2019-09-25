$(function () {
    var receiveLike = [{
        "likeuname": "eiji",
        "type": "视频",
        "date": "3-1 10:10",
        "content": "阿萨打算",
        "status": "0"
    }, {
        "likeuname": "lee",
        "type": "弹幕",
        "date": "3-6 10:10",
        "content": "啊实打实打算",
        "status": "0"
    }, {
        "likeuname": "eiji",
        "type": "评论",
        "date": "3-18 10:10",
        "content": "asdasdasd",
        "status": "1"
    }];

    //显示当前登录用户信息
    // var uname = window.localStorage.getItem("uname");
    // var password = window.localStorage.getItem("password");
    // var headshot = window.localStorage.getItem("headshot");
    // var uid = window.localStorage.getItem("uid");
    var uname = getCookie("uname");
    var uid = Number(getCookie("uid"));
    var headshot = getCookie("headshot");
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

    //获取收到的赞的信息
    var mainDiv = $(".mainDiv");
    $.ajax({
        type: "POST",
        url: "/Message/getMyLike",
        contentType: "application/json",
        data: JSON.stringify({
            "uid": uid
        }),
        dataType: "json",
        async: true,
        cache: false,
        success: function (data) {
            var receiveLike = data;
            if(receiveLike.length != 0){
                mainDiv.removeClass("mainDivBg");
            }
            addReceiveLike(receiveLike);
        },
        error: function (json) {
            alert("获取收到的赞出错！");
        }
    });

    //渲染系统消息
    function addReceiveLike(receiveLike) {
        for (var index = 0; index < receiveLike.length; index++) {
            var likeuname = receiveLike[index].likeuname; //得到点赞人的用户名
            var date = receiveLike[index].date; //得到点赞时间
            var type = receiveLike[index].type;//得到点赞的类型(视频，弹幕，评论)
            var content = receiveLike[index].context; //得到点赞内容

            //receiveLike div
            var resultDiv = "<div class='receiveLike nav'>" +
                "<div class='receiveLike-logo'><img src='images/1.jpg'></div>" +
                "<div class='receiveLike-detail'>" +
                "<div class='receiveLike-title'>" +
                "<div class='receiveLike-name'>" + likeuname + "</div>" +
                "<div class='receiveLike-text'>赞了你的</div>" +
                "<div class='receiveLike-item'>" + type + "</div>" +
                "<div class='receiveLike-date'>" + date + "</div>" +
                "</div>" +
                "<div class='receiveLike-content'>" +
                content +
                "</div>" +
                "</div>" +
                "</div>";

            $(".mainDiv").append(resultDiv);
        }
    }

});