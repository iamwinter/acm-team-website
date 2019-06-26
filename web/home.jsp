<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title><s:property value="#request.homeName"/></title>

    <style>
        /* Make the image fully responsive */
        .carousel-inner img {
            width: 100%;
            height: 100%;
        }
        .text-ellipsis{
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        }
    </style>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <div class="col-xs-12 col-sm-6">
            <div id="myCarousel" class="carousel slide">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                    <li data-target="#myCarousel" data-slide-to="3"></li>
                </ol>
                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active">
                        <img src="${pageContext.request.contextPath}/template/images/carousel/1.jpg" alt="First slide">
                    </div>
                    <div class="item">
                        <img src="${pageContext.request.contextPath}/template/images/carousel/2.jpg" alt="Second slide">
                    </div>
                    <div class="item">
                        <img src="${pageContext.request.contextPath}/template/images/carousel/3.jpg" alt="Third slide">
                    </div>
                    <div class="item">
                        <img src="${pageContext.request.contextPath}/template/images/carousel/4.jpg" alt="4">
                    </div>
                </div>
                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>


        <div class="col-xs-12 col-sm-6">
            <table class="table" style="table-layout:fixed;font-size: 1em">
                <thead>
                    <tr>
                        <th>近期新闻</th>
                        <th class="text-right">更多</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="#request.recent_news" var="news">
                        <tr>
                            <td class="text-ellipsis" width="60%">
                                <a href="${pageContext.request.contextPath}/news_show?id=<s:property value="#news.id"/>">
                                    <s:property value="#news.title"/>
                                </a>
                            </td>
                            <td class="text-right"><s:property value="#news.created.substring(0,11)"/></td>
                            <s:if test="#session.user.isSuper==1">
                                <td>
                                    <a href="${pageContext.request.contextPath}/news_editNews?id=<s:property value="#news.id"/>">编辑</a>
                                </td>
                            </s:if>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
        </div>

        <div class="col-xs-12">
            <div class="panel-body">
                ${mainText.content}
            </div>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
</body>
</html>
