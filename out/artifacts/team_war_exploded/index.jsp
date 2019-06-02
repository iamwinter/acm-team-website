<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="/template/headTag.jsp"%>
  <title><%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-sm-12 col-md-8">
            <p>这里是主页面,待解决的bug：</p>
            <p>模糊查询中文不行</p>
            <p>当前用户:${sessionScope.user.username}</p>
            <s:debug/>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
