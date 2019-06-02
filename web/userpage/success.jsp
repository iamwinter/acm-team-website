<%--
  Created by IntelliJ IDEA.
  User: 15866
  Date: 2019/4/15
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>登录成功-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main">
    <div class="bigContainer">
        <div class="panel-log center-block">
            <div class="alert alert-success">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <h4><s:property value="#request.msg"/> ！欢迎您 <s:property value="username"/> </h4>
            </div>
            <h3>
                您可以前往
                <a href="<%=rootPath%>/">主页</a>
                /
                <a href="<%=rootPath%>/user_modify">修改个人信息</a>
            </h3>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
