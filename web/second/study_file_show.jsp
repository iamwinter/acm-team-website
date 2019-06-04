
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>考研资料-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="form-group">
            <a href="javascript:history.back()">返回上一级</a>
        </div>
        <div class="text-center">
            <video id="id_video" width="80%" height="65%" controls style="display: none;">
                <source type="video/mp4">
                您的浏览器不支持 HTML5 video 标签。
            </video>
        </div>
        <hr>
        <div>
            <button class="btn btn-group">下载所有文件</button>
        </div>
        <table class="table text-nowrap">
            <thead>
                <tr>
                    <th>文件名</th>
                    <th>分享者</th>
                    <th>上传时间</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="#request.files" var="file">
                    <tr>
                        <td><s:property value="#file.name"/> </td>
                        <td><s:property value="#file.username"/> </td>
                        <td><s:property value="#file.created"/></td>
                        <td>
                            <a href="javascript:playVideo('<%=rootPath%>/${file.path}')">播放</a>
                            /
                            <a href="<%=rootPath%>/${file.path}" download="${file.name}">下载</a>
                            <s:if test="#session.user.isSuper==1">
                                /
                                <a href="#">修改</a>
                            </s:if>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>

    </div>
</div>
<%@include file="/template/footer.jsp"%>
<script type="text/javascript">
    function playVideo(path) {
        $("#id_video").attr("src",path)
        $("#id_video").fadeIn(1000)
        $('body,html').animate({scrollTop:0},1000);
    }
</script>
</body>
</html>
