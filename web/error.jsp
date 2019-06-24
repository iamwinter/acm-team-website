
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title><s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main">
    <div class="bigContainer">
        <div class="panel-log center-block">
            <center><h1>出错了~~~~</h1></center>
            <div class="alert alert-warning">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <h4>${msg}</h4>
            </div>
            <h3 class="text-center">
                <a href="${pageContext.request.contextPath}/">主页</a>
            </h3>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
