<%--
  Created by IntelliJ IDEA.
  User: 15866
  Date: 2019/4/18
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>个人主页-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main">
    <div class="bigContainer">

        <div class="col-sm-12 col-md-8" style="padding: 0 5%">
            <h1 style="margin: 0;"><s:property value="#request.aimUser.username"/></h1>
            <hr>
            <p>身份：<s:property value="@dao.UserDaoImpl@tagStr[#request.aimUser.tag]"/></p>
            <p>本科专业：<s:property value="#request.aimUser.major"/></p>
            <p>年级:<s:property value="#request.aimUser.grade"/></p>
            <p>姓名:${aimUser.username}</p>
            <p>学校/工作:${aimUser.work}</p>
            <p>邮箱:${aimUser.email}</p>
            <p>博客:<a href="${aimUser.blogUrl}">${aimUser.blogUrl}</a> </p>
        </div>

        <%--        照片--%>
        <div class="col-sm-12 col-md-4">
            <div class="bkcolorhalf">
                <s:if test="#request.photo_path!=null">
                    <img src="${pageContext.request.contextPath}/<s:property value="#request.photo_path"/>" class="img-thumbnail" alt="头像">
                </s:if>
            </div>
        </div>

    </div>
    <hr>
    <div class="bigContainer">
        <div class="bkcolorhalf col-xs-12" style="padding: 0 5%;">
            ${aimUser.resume}
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
