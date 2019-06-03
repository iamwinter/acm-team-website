
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>新闻-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <form action="<%=rootPath%>/news_news" method="get">

            <div class="form-group col-xs-9 col-sm-3">
                <input type="text" name="key" class="form-control" placeholder="模糊查询" required>
            </div>

            <button type="submit" class="btn btn-success">搜索</button>
        </form>

        <table class="table">
            <thead>
                <tr>
                    <th>标题</th>
                    <th class="text-right">浏览量</th>
                    <th class="text-right">时间</th>
                </tr>
            </thead>
            <tbody>
            <s:iterator value="#request.newsList" var="news">
                <tr>
                    <td>
                        <a href="<%=rootPath%>/news_show?id=<s:property value="#request.news.id"/>">
                            <s:property value="#news.title"/>
                        </a>
                    </td>
                    <td class="text-right"><s:property value="#news.views"/></td>
                    <td class="text-right"><s:property value="#news.created"/></td>
                    <s:if test="#session.user.isSuper==1">
                        <td>
                            <a href="<%=rootPath%>/admin_editNews?id=<s:property value="#news.id"/>">编辑</a>
                        </td>
                    </s:if>
                </tr>
            </s:iterator>
            </tbody>
        </table>

    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
