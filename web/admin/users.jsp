
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>用户列表-管理员-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-xs-12 col-sm-2">
            <%@include file="menu.jsp"%>
        </div>
        <div class="col-xs-12 col-sm-10">
            <form action="<%=rootPath%>/user_admin_users" method="get">

                <div class="form-group col-xs-9 col-sm-3">
                    <input type="text" name="key" class="form-control" placeholder="模糊查询" required>
                </div>

                <button type="submit" class="btn btn-success">搜索</button>
            </form>

            <table class="table text-nowrap" style="overflow: auto">
                <caption>用户列表</caption>
                <thead>
                    <tr>
                        <th>用户名</th>
                        <th>姓名</th>
                        <th>年级</th>
                        <th>权限</th>
                        <th>公开</th>
                        <th>博客url</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="dataList" var="user">
                        <tr>
                            <td><a href="<%=rootPath%>/user_user?username=<s:property value="#user.username"/>" target="_blank"><s:property value="#user.username"/></a> </td>
                            <td><s:property value="#user.nickName"/> </td>
                            <td><s:property value="#user.grade"/> </td>
                            <td><s:property value="#user.isSuper"/> </td>
                            <td><s:property value="#user.isPublic"/> </td>
                            <td><a href="<s:property value="#user.blogUrl"/>" target="_blank">博客</a></td>
                            <td>
                                <a href="<%=rootPath%>/user_modify?username=<s:property value="#user.username"/>" target="_blank">修改更多</a>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>