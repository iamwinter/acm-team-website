
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title><%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main">
    <div class="bigContainer">
        <div class="panel-log center-block">
            <div class="alert alert-success">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong><s:property value="#request.msg"/></strong>
            </div>
            <h3>
                您可以前往
                <a href="<%=rootPath%>/">主页</a>
            </h3>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
