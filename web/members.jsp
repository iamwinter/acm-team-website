
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>团队成员-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <div class="panel-body">
            <form action="${pageContext.request.contextPath}/user_members" method="get">

                <div class="form-group col-xs-12 col-sm-3">
                    <input type="text" name="key" class="form-control" placeholder="用户名、姓名查找"
                           value="<s:property value="#request.key"/>">
                </div>

                <button type="submit" class="btn btn-success">搜索</button>
            </form>
        </div>

        <s:if test="#request.teacher.size>0">
            <div class="table-responsive">
                <table class="table">
                    <caption>指导教师</caption>
                    <thead>
                        <tr>
                            <th>用户名</th>
                            <th>姓名</th>
                            <th>博客</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="#request.teacher" var="user">
                            <tr>
                                <td><a href="${pageContext.request.contextPath}/user_user?username=<s:property value="#user.username"/>"><s:property value="#user.username"/></a> </td>
                                <td><s:property value="#user.nickName"/> </td>
                                <td><a href="<s:property value="#user.blogUrl"/>">博客主页</a></td>
                                <s:if test="#session.user.isSuper==1">
                                    <td>
                                        <a href="${pageContext.request.contextPath}/user_modify?username=<s:property value="#user.username"/>">修改</a>
                                    </td>
                                </s:if>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </div>
        </s:if>


        <s:iterator value="#request.student.keySet()" var="key">
            <div class="table-responsive">
                <table class="table">
                    <caption><s:property value="key"/></caption>
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
                    <s:iterator value="#request.student.get(#key)" var="user">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/user_user?username=<s:property value="#user.username"/>"><s:property value="#user.username"/></a> </td>
                            <td><s:property value="#user.nickName"/> </td>
                            <td><s:property value="#user.grade"/> </td>
                            <td><s:property value="#user.major"/> </td>
                            <td><s:property value="#user.work"/> </td>
                            <td><a href="<s:property value="#user.blogUrl"/>">博客主页</a></td>
                            <s:if test="#session.user.isSuper==1">
                                <td>
                                    <a href="${pageContext.request.contextPath}/user_modify?username=<s:property value="#user.username"/>">修改</a>
                                </td>
                            </s:if>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
        </s:iterator>


    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
