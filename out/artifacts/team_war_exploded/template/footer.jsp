<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="Tools.MyConfig" %><%--
  Created by IntelliJ IDEA.
  User: winter
  Date: 2018/9/25
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="footContainer" class="text-center">
    <hr>

    <div id="time1"><!-- 时间 --> </div>

    <div>
        <p style="margin-bottom: 0">
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
            if ($(a).attr("href") === url) {
                $(this).addClass("active");
            } else {
                $(this).removeClass("active");
            }
        });
    })
</script>