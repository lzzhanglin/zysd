$().ready(function() {
    $("#gotoBtn").click(function () {
        var gotoPageNo = $("#gotoPageNo").val();
        if (gotoPageNo != null && gotoPageNo != undefined && gotoPageNo != "") {
            console.log("跳转的值为：" + gotoPageNo);
            window.location.href = "/showData?pageNo=" + gotoPageNo + "&pageSize=10";
        }
    });

});
