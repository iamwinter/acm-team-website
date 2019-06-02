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

<%--<%!--%>

<%--    //请不要更改数组元素的顺序！数据库中存的是下标--%>
<%--    String[] OJplatform={"常规","codeforces","牛客","atcoder","vjudge","upcOJ","lduOJ","HDU"};--%>
<%--    String[] matchName={"icpc亚洲区域赛&邀请赛","山东省ACM程序设计大赛","ccpc中国大学生程序设计竞赛",--%>
<%--            "蓝桥杯系列","中国计算机团体程序设计天梯赛","其它竞赛"};--%>
<%--%>--%>

<link rel="shortcut icon" href="<%=rootPath%>/template/images/background/favicon.ico" type="image/x-icon">
<link type="text/css" rel="stylesheet" href="<%=rootPath%>/template/css/main.css">
<link rel="stylesheet" href="<%=rootPath%>/template/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
