<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="nav navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <!--在移动端的时候导航条折叠起来，三横的样式出现，点击该样式可以显示或隐藏导航条上的内容-->
            <button class="navbar-toggle" data-toggle="collapse" data-target="#menu">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div style="padding: 5px">
                <a href="<%=rootPath%>/">
                    <font style="color: white;font-size: 1.3em"><%=homeName%></font>
                </a>
            </div>
        </div>
        <div id="menu" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="<%=rootPath%>/"><span class="glyphicon glyphicon-home"></span>&nbsp;首页</a></li>
                <li><a href="<%=rootPath%>/news_news">新闻</a></li>
                <li><a href="<%=rootPath%>/#">比赛成绩</a></li>
                <li><a href="<%=rootPath%>/#">竞赛综合</a></li>
                <li><a href="<%=rootPath%>/user_members">团队之星</a></li>
                <li><a href="<%=rootPath%>/#">学习专区</a></li>
                <li><a href="http://icpc.ldu.edu.cn" target="_blank">Online Judge&nbsp;<span class="glyphicon glyphicon-new-window"></span></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <s:if test="#session.user==null">
                    <li><a href="<%=rootPath%>/user_login">登录</a></li>
                    <li><a href="<%=rootPath%>/user_register">注册</a></li>
                </s:if>
                <s:else>
<%--                    此处可以考虑现实头像--%>
                    <li class="dropdown">
                        <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                            ${sessionScope.user.username}<span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=rootPath%>/user_user">我的主页</a></li>
                            <li><a href="<%=rootPath%>/user_modify">修改信息</a></li>
                            <s:if test="#session.user.isSuper==1">
                                <li><a href="<%=rootPath%>/admin_admin">管理员</a></li>
                            </s:if>
                            <li class="divider"></li>
                            <li><a href="javascript:logout()"><span class="glyphicon glyphicon-log-out"> Log-out</span></a></li>
                        </ul>
                    </li>
                </s:else>

            </ul>
        </div>
    </div>
</nav>


<script type="text/javascript">
    //登出
    function logout() {
        //var data=$("#form1").serialize()
        $.ajax({
            type : "post",
            url : "<%=rootPath%>/user_logout",
            success : function(ret) {
                console.log(ret)
                //window.location='<%=rootPath%>/'
                window.location.reload()
            },
            error: function (ret) {
                alert("系统出错"+ret)
            }
        });
    }
</script>