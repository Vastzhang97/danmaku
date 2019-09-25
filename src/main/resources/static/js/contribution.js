$(function () {
    //显示当前登录用户信息
    // var uname = window.localStorage.getItem("uname");
    // var password = window.localStorage.setItem("password");
    // var uid = window.localStorage.setItem("uid");

    // var uid = 1;
    // var uname = 555;
    // var password = 666;

    //用户信息
// var loginUname = window.localStorage.getItem("username");
// var loginUid = Number(window.localStorage.getItem("uid"));
// var Uheadshot = window.localStorage.getItem("headshot");
    var uname = getCookie("uname");
    var uid = Number(getCookie("uid"));
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

    //上传视频封面/视频资源
    $("#submitCoverResource").click(function () {
        $("#coverResource").click();
    });
    $("#coverResource").change(function () {
        $(".contribution-img").text($("#coverResource").val());
    });

    $("#submitVidoeResource").click(function () {
        $("#vidoeResource").click();
    });

    $("#vidoeResource").change(function () {
        $(".contribution-video").text($("#vidoeResource").val());
    });

    //textarea高度自适应文本
    $(".videoIntro").each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    }).on('input', function () {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    });


});

var xmlhttp;
if (window.XMLHttpRequest) {
    xmlhttp = new XMLHttpRequest();
} else {
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

function doUpload() {
    var formData = new FormData($("#form")[0]);
    var videoIntro = changeCommentContent($(".videoIntro").val());
    formData.append("videoIntro", videoIntro);
    //监听事件
    xmlhttp.upload.addEventListener("progress", uploadProgress, false);
    //发送文件和表单自定义参数
    xmlhttp.open("POST", "/PersonalCenter/uploadVideo", true);
    xmlhttp.send(formData);
}

xmlhttp.onreadystatechange = function () {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        $(".contributionProgress").empty();
        var result = "上传成功!请等待管理员审核。<a href='/goToIndex'>返回首页</a>";
        var result1 = JSON.parse(xmlhttp.responseText);
        if (result1[0].result == 1) {
            $(".contributionProgress").append(result);
        }

        // $("#result").html("上传成功！" + "<a href='index.jsp'>返回首页</a>");
    }
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {
        //evt.loaded：文件上传的大小   evt.total：文件总的大小
        var percentComplete = Math.round(((evt.loaded) * 100 / evt.total) - 1);
        //加载进度条，同时显示信息
        $("#percent").html(percentComplete + '%');
        $("#progressNumber").css("width", percentComplete + "%");
    }
}

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
