<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title><s:property value="#request.news.title"/>-新闻-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main">
    <div class="bigContainer">

        <div class="text-center">
            <h1><s:property value="#request.news.title"/></h1>
        </div>
        <p class="text-center" style="margin: 0;">
            时间：${news.created}
            &nbsp;&nbsp;&nbsp;&nbsp;
            点击：${news.views}
        </p>
        <hr style="margin-top:5px">
        <div class="panel-body" style="text-indent:28px;padding: 0 8%;font-size: 1.2em">
            ${news.content}
        </div>
        <hr>
        <h5>本文最后更新于<s:property value="#request.news.modified"/></h5>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
