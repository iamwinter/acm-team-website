
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>新闻-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <form action="${pageContext.request.contextPath}/news_news" method="get">

            <div class="form-group col-xs-9 col-sm-3">
                <input type="text" name="key" value="<s:property value="#request.key"/>"
                       class="form-control" placeholder="模糊查询">
            </div>

            <button type="submit" class="btn btn-success">搜索</button>
        </form>


        <table class="table">
            <thead>
                <tr>
                    <th>标题</th>
                    <th class="text-right">点击量</th>
                    <th class="text-right">时间</th>
                </tr>
            </thead>
            <tbody>
            <s:iterator value="dataList" var="news">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/news_show?id=<s:property value="#news.id"/>">
                            <s:property value="#news.title"/>
                        </a>
                    </td>
                    <td class="text-right"><s:property value="#news.views"/></td>
                    <td class="text-right"><s:property value="#news.created"/></td>
                    <s:if test="#session.user.isSuper==1">
                        <td>
                            <a href="${pageContext.request.contextPath}/news_editNews?id=<s:property value="#news.id"/>">编辑</a>
                        </td>
                    </s:if>
                </tr>
            </s:iterator>
            </tbody>
        </table>

        <div class="text-center">
            <ul class="pagination">
                <li><a href="${pageContext.request.contextPath}/news_news?key=${key}&page=<s:property value="#request.page-(#request.page==1?0:1)"/>">&laquo;</a></li>
                <s:iterator begin="1" end="#request.pageCount" status="sta">
                    <li class="<s:property value="#request.page==#sta.index+1?'active':''"/>">
                        <a href="${pageContext.request.contextPath}/news_news?page=${sta.index+1}&key=${key}">
                            ${sta.index+1}
                        </a>
                    </li>
                </s:iterator>
                <li><a href="${pageContext.request.contextPath}/news_news?key=${key}&page=<s:property value="#request.page+(#request.page==#request.pageCount?0:1)"/>">&raquo;</a></li>
            </ul>
        </div>

    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
