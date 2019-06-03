
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>学习专区-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <form action="">
            <ul class="breadcrumb">
                <li><a href="#">2020</a></li>
                <li><a href="#">2019</a></li>
            </ul>
            <ul class="breadcrumb">
                <li><a href="#">高等数学</a></li>
                <li><a href="#">线性代数</a></li>
            </ul>
        </form>
        <div class="">
            <div class="col-xs-12 col-sm-3" style="background-color: red;height: 60px">
                这是一个文件夹
            </div>
            <div class="col-xs-12 col-sm-3" style="background-color: red;height: 60px">
                这是一个文件夹
            </div>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
