var PageNum = Number(window.localStorage.getItem("PageNum"));
window.onload = function () {
    // window.localStorage.clear();
    console.log(PageNum);
    if (PageNum == 0) {
        PageNum = PageNum + 1;
        console.log(PageNum);
    } else {
        if ($(document).attr("title") != "登录" && $(document).attr("title") != "注册") {
            PageNum = PageNum + 1;
        }
        console.log(PageNum);
    }
}

window.onunload = function () {
    // if (event.clientX > document.body.clientWidth && event.clientY < 0 || event.altKey) {
    //     alert("你关闭了浏览器");
    // } else {
    //     alert("你正在刷新页面");
    // }
    if ($(document).attr("title") != "登录" && $(document).attr("title") != "注册") {
        PageNum = PageNum - 1;
    }
    if (PageNum == 0) {
        window.localStorage.clear();
    }
    window.localStorage.setItem("PageNum", PageNum);
}

