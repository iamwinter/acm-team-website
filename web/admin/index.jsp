
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>管理员-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-xs-12 col-sm-2">
            <%@include file="menu.jsp"%>
        </div>
        <div class="col-xs-12 col-sm-10">
            <p>管理员:</p>
            <p>1.用户管理，管理用户的权限，修改用户的信息</p>
            <p>2.新闻管理，对新闻信息进行增删改查</p>
            <p>3.编辑新闻，修改或增加新闻。也可以<a href="news_editNews?id=1">编辑网站首页</a></p>
            <p>4.管理科目，移动顺序以及增删改查</p>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
