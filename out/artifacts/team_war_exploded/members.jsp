
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>团队成员-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <form action="<%=rootPath%>/user_members" method="get">

            <div class="form-group col-xs-9 col-sm-3">
                <input type="text" name="key" class="form-control" placeholder="模糊查询" required>
            </div>

            <button type="submit" class="btn btn-success">搜索</button>
        </form>

        <table class="table">
            <caption>团队成员</caption>
            <thead>
            <tr>
                <th>用户名</th>
                <th>姓名</th>
                <th>年级</th>
                <th>专业</th>
                <th>学校/工作</th>
                <th>博客</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="#request.users" var="user">
                <tr>
                    <td><a href="<%=rootPath%>/user_user?username=<s:property value="#user.username"/>"><s:property value="#user.username"/></a> </td>
                    <td><s:property value="#user.nickName"/> </td>
                    <td><s:property value="#user.grade"/> </td>
                    <td><s:property value="#user.major"/> </td>
                    <td><s:property value="#user.work"/> </td>
                    <td><a href="<s:property value="#user.blogUrl"/>">博客主页</a></td>
                    <s:if test="#session.user.isSuper==1">
                        <td>
                            <a href="<%=rootPath%>/user_modify?username=<s:property value="#user.username"/>">修改</a>
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
