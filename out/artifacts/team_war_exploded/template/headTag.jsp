<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta name="force-rendering" content="webkit"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<%
    String rootPath = request.getContextPath();
    String homeName = MyConfig.get("siteName");
%>

<%--<link rel="shortcut icon" href="<%=rootPath%>/template/images/background/favicon.ico" type="image/x-icon">--%>
<link type="text/css" rel="stylesheet" href="<%=rootPath%>/template/css/main.css">
<link rel="stylesheet" href="<%=rootPath%>/template/bootstrap-3.3.7-dist/css/bootstrap.min.css">

<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
