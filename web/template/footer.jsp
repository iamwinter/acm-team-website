<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="Tools.MyConfig" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="footContainer" class="text-center">
    <hr>

    <div>
        <p style="margin-bottom: 0;">
            <a href="https://github.com/iamwinter/acm-team-website">GitHub</a>
            &nbsp;|&nbsp;
            <a href="https://blog.csdn.net/winter2121">作者博客</a>
            &nbsp;|&nbsp;
            <a href="${pageContext.request.contextPath}/user_user?username=winter2121">联系作者</a>
            &nbsp;|&nbsp;
            <a href="#">软工1602 赵京龙 20162203477</a>
        </p>
    </div>

    <div id="time1"><!-- 时间 --> </div>

    <div>
        <p>
            Copyright © 2019 <%=MyConfig.get("siteAuthor")%> All rights reserved.
        </p>
    </div>
</div>

<script type="text/javascript">
    //自动更新页脚时间
    var nowTime=function () {
        document.getElementById('time1').innerHTML='Server time: '+new Date();
        return nowTime; //若不返回，此函数就无法多次执行
    }
    setInterval(nowTime(),1000); //每秒刷新时间

    // 遍历导航栏按钮，如果href与当前位置相等，就active
    $(function () {
        $("ul.nav").find("li").each(function () {
            var a = $(this).find("a:first")[0];
            var url = location.pathname
            url = url.split('?')[0]     //去掉参数
            url = url.substring(url.lastIndexOf('${pageContext.request.contextPath}'))
            if ($(a).attr("href")==url) {
                $(this).addClass("active");
            } else {
                $(this).removeClass("active");
            }
        });
    })
</script>
