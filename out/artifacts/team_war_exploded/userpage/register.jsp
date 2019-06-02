
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>注册-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>

<div class="main">
    <div class="bigContainer">
        <form id="form_reg" action="user_register" method="post" onsubmit="return check_reg()" class="panel-log" >

            <s:if test="#request.res==false">
                <div class="alert alert-warning">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <strong>警告！</strong>
                    <font><s:property value="#request.msg"/> </font>
                </div>
            </s:if>
            <div id="password_alert" class="alert alert-warning" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>警告！</strong>
                <font></font>
            </div>

            <div class="form-group">
                <input name="username" type="text" class="form-control"
                       required minlength="4" maxlength="30"
                       onkeyup="value=value.replace(/[^\w]/ig,'')"
                       value="<s:property value="#request.username"/>"
                       placeholder="*请输入用户名（仅允许使用英文+数字）">
            </div>

            <div class="form-group">
                <input name="password" type="password" class="form-control" required
                       minlength="4" maxlength="16" placeholder="*请输入密码">
            </div>

            <div class="form-group">
                <input name="password2" type="password" class="form-control" required
                       minlength="4" maxlength="16" placeholder="*请再次输入密码">
            </div>

            <div class="form-group">
                <input name="email" type="email" class="form-control"
                       value="<s:property value="#request.email"/>"
                       placeholder="请输入邮箱，可用于登录">
            </div>

            <div class="form-group">
                <a href="<%=rootPath%>/user_login"><span class="glyphicon glyphicon-log-in"></span>&nbsp;前往登录</a>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-block btn-success">注册</button>
            </div>
        </form>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
<script type="text/javascript">
    function check_reg() {
        $("#password_alert").hide()
        var p1=$("input[name='password']").val()
        var p2=$("input[name='password2']").val()
        if(p1!=p2){
            $("#password_alert font").text("两次输入的新密码不一致！"+new Date())
            $("#password_alert").show()
            return false
        }
        return true;
    }
</script>
</body>
</html>
