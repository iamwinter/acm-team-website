
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>编辑新闻-管理员-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-xs-12 col-sm-2">
            <%@include file="menu.jsp"%>
        </div>
        <div class="col-xs-12 col-sm-10">
            <form action="news_saveNews" onsubmit="return check_news();" class="" method="post">

                <input name="id" type="number" value="${news.id}" hidden>
                <textarea id="id_content" name="content" class="form-control hidden">${news.content}</textarea>
                <input name="userId" type="number" value="${sessionScope.user.id}" hidden>

                <div class="form-group col-xs-12">
                    <input name="title" type="text" class="form-control" value="${news.title}" maxlength="48" placeholder="标题" required>
                </div>
                <div class="form-group col-xs-12">
                    <label for="editor">正文</label>
                    <div id="editor">${news.content}</div>
                </div>
                <div class="form-group col-xs-12 col-sm-3">
                    <select name="isPublic" class="form-control">
                        <option value="1">公开</option>
                        <option value="0">保存为草稿</option>
                    </select>
                </div>

                <div class="form-group col-xs-12">
                    <button type="submit" class="btn btn-success">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>

<%-- 富文本编辑器wangeditor --%>
<script src="//unpkg.com/wangeditor/release/wangEditor.min.js"></script>
<script type="text/javascript">

    function check_news() {
        $("#id_content").val(editor.txt.html())
        return true;
    }

    var E = window.wangEditor;
    var editor = new E('#editor');
    // 图片上传说明书 https://www.kancloud.cn/wangfupeng/wangeditor3/335782
    editor.customConfig.uploadImgServer = "${pageContext.request.contextPath}/file_wangUploadImage.action"
    editor.customConfig.uploadImgMaxSize=10*1024*1024;
    editor.customConfig.uploadImgMaxLength = 50;
    editor.customConfig.uploadFileName = 'upFile'  //上传文件名，与action中一致
    editor.customConfig.uploadImgHooks = {
        before: function (xhr, editor, files) {
            // 图片上传之前触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

            // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
            // return {
            //     prevent: true,
            //     msg: '放弃上传'
            // }
            console.log("准备上传")
        },
        success: function (xhr, editor, result) {
            console.log("上传成功"+result);
            // 图片上传并返回结果，图片插入成功之后触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        fail: function (xhr, editor, result) {
            alert("上传失败"+result);
            // 图片上传并返回结果，但图片插入错误时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        error: function (xhr, editor) {
            alert("上传出错"+result);
            // 图片上传出错时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
        timeout: function (xhr, editor) {
            alert("上传超时"+result);
            // 图片上传超时时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },

        // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
        // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
        customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            console.log("回传图片json数据:");
            console.log(result)
            result=$.parseJSON(result) //字符串转换为json
            insertImg("${pageContext.request.contextPath}/"+result.data);

            // result 必须是一个 JSON 格式字符串！！！否则报错
        }
    }
    editor.create();
</script>


</body>
</html>