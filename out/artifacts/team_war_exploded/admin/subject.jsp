
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>学习科目-管理员-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-xs-12 col-sm-2">
            <%@include file="menu.jsp"%>
        </div>
        <div class="col-xs-12 col-sm-10">
            <form action="${pageContext.request.contextPath}/#" method="get">

                <div class="form-group col-xs-9 col-sm-3">
                    <input type="text" name="key" class="form-control" placeholder="模糊查询" required>
                    这里添加新科目
                </div>

                <button type="submit" class="btn btn-success">搜索</button>
            </form>
            <table class="table text-nowrap" style="overflow: auto">
                <thead>
                    <tr>
                        <th>顺序</th>
                        <th>科目名称</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                <s:iterator value="dataList" var="subject" status="sta">
                    <tr>
                        <td><s:property value="#sta.index+1"/> </td>
                        <td><s:property value="#subject.name"/> </td>
                        <td>
                            <a href="#">上移</a>
                            /
                            <a href="#">下移</a>
                            /
                            <a href="#">修改</a>
                            /
                            <a href="#">删除</a>
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