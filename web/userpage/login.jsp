<%--
  Created by IntelliJ IDEA.
  User: 15866
  Date: 2019/4/15
  Time: 20:30
  To change this template use SqlFile | Settings | SqlFile Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>登录-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>

<div class="main">
    <div class="bigContainer">
        <form action="${pageContext.request.contextPath}/user_login.action" class="panel-log" method="post" role="form">

            <s:if test="res==false">
                <div class="alert alert-warning">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <strong>警告！</strong>
                    <font>${msg} </font>
                </div>
            </s:if>

            <div class="form-group">
                <input name="username" type="text" class="form-control" required
                       value="<s:property value="#request.username"/>"
                       minlength="4" maxlength="30" placeholder="请输入用户名或邮箱">
            </div>

            <div class="form-group">
                <input name="password" type="password" class="form-control" required minlength="4" maxlength="16" placeholder="请输入密码">
            </div>

            <div class="form-group">
                <a href="${pageContext.request.contextPath}/userpage/user_register"><span class="glyphicon glyphicon-log-in"></span>&nbsp;前往注册</a>
                /
                <a href="#">忘记密码</a>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-block btn-success">登录</button>
            </div>
        </form>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
