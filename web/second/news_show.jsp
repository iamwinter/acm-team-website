<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title><s:property value="#request.news.title"/>-新闻-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <h1><s:property value="#request.news.title"/></h1>
        这里是新闻展示
        <h4>浏览量：<s:property value="#request.news.views"/> </h4>
        <p>本文最后更新于<s:property value="#request.news.modified"/></p>
        <p><s:property value="#request.news.content" escapeHtml="false"/></p>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
