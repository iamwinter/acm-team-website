
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>执行结果</title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main">
    <div class="bigContainer">
        <div class="panel-log center-block">
            <div class="alert alert-success">
                <h3 style="margin: 0;">${msg}</h3>
            </div>
            <h4>
                <font id="downT">3</font>秒后自动跳转到<a id="backHost">上一页</a>
            </h4>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>

<script type="text/javascript">
    //倒计时
    var lastUrl = document.referrer
    if(lastUrl.indexOf("login")>=0 || lastUrl.indexOf("register")>=0)
        lastUrl = "${pageContext.request.contextPath}/"
    $('#backHost').attr("href",lastUrl)
    var downTime=function () {
        var re = document.getElementById('downT').innerHTML;
        document.getElementById('downT').innerHTML=re-1;
        if(re==1)
            window.location.href=$('#backHost').attr("href")
        return downTime; //若不返回，此函数就无法多次执行
    }
    setInterval(downTime(),1000); //每秒刷新时间
</script>
</body>
</html>
