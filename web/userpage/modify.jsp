
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>修改个人信息-<%=homeName%></title>
</head>
<body>

<%@include file="/template/header.jsp"%>
<div class="main" style="padding: 0 5%">
    <div class="bigContainer">
        <div class="alert alert-success">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <s:if test="#request.aimUser.id==#session.user.id">
                <strong>欢迎您，${sessionScope.user.username}！您可以修改您的个人信息</strong>
            </s:if>
            <s:else>
                <strong>欢迎管理员${sessionScope.user.username}！您将要修改用户${aimUser.username}的个人信息</strong>
            </s:else>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">修改密码</div>
        <form id="form_pwd" onsubmit="return check_pwd();" method="post" class="panel-body">
            <input type="number" name="id" value="${aimUser.id}" hidden>

            <div class="form-group col-xs-12">
                <div class="col-xs-3 col-sm-2"><label>新密码</label></div>
                <div class="col-xs-9 col-sm-5">
                    <input name="password" type="password" class="form-control" required
                           minlength="4" maxlength="16" placeholder="输入新密码">
                </div>
            </div>

            <div class="form-group col-xs-12">
                <div class="col-xs-3 col-sm-2"><label>确认新密码</label></div>
                <div class="col-xs-9 col-sm-5">
                    <input name="password2" type="password" class="form-control" required
                           minlength="4" maxlength="16" placeholder="输入新密码">
                </div>
            </div>

            <div class="form-group col-xs-12">
                <div class="col-xs-3 col-sm-2"><label>旧密码</label></div>
                <div class="col-xs-9 col-sm-5">
                    <input name="old_password" type="password" class="form-control" required
                           minlength="4" maxlength="16" placeholder="输入旧密码">
                </div>
            </div>

            <div id="password_alert" class="form-group col-xs-12 alert alert-warning" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>警告！</strong>
                <font></font>
            </div>
            <div id="password_alert_s" class="form-group col-xs-12 alert alert-success" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>保存成功！</strong>
                <font></font>
            </div>

            <div class="form-group col-xs-12">
                <button type="submit" class="btn btn-success">保存</button>
            </div>
        </form>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">基本信息</div>
        <form id="form_info" onsubmit="return check_info();" class="panel-body" method="post">
<%--            id必须--%>
            <input type="number" name="id" value="${aimUser.id}" hidden>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>邮箱</label></div>
                <div class="col-xs-9">
                    <input name="email" type="email" class="form-control"
                           placeholder="您可以输入新的邮箱" value="${aimUser.email}">
                </div>
            </div>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>姓名</label></div>
                <div class="col-xs-9">
                    <input name="nickName" type="text" class="form-control"
                           placeholder="您可以输入您的中文姓名" value="${aimUser.nickName}">
                </div>
            </div>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>身份</label></div>
                <div class="col-xs-9">
                    <select id="id_tag" name="tag" class="form-control">
                        <option value="0"><s:property value="@dao.UserDao@tagStr[0]"/></option>
                        <option value="1"><s:property value="@dao.UserDao@tagStr[1]"/></option>
                        <option value="2"><s:property value="@dao.UserDao@tagStr[2]"/></option>
                        <option value="3"><s:property value="@dao.UserDao@tagStr[3]"/></option>
                        <script>
                            var i=${aimUser.tag}    //选择默认身份
                            document.getElementById('id_tag')[i].selected=true;
                        </script>
                    </select>
                </div>
            </div>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>入学年份</label></div>
                <div class="col-xs-9">
                    <input name="grade" type="number" class="form-control"
                           placeholder="请填写四位数入学年份，如:2019"
                           value="${aimUser.grade}">
                </div>
            </div>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>专业</label></div>
                <div class="col-xs-9">
                    <input name="major" type="text" class="form-control"
                           placeholder="请填写专业名称，如:软件工程"
                           value="${aimUser.major}">
                </div>
            </div>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>现今/工作</label></div>
                <div class="col-xs-9">
                    <input name="work" type="text" class="form-control"
                           placeholder="请填写您目前的学校/工作"
                           value="${aimUser.work}">
                </div>
            </div>

            <div class="form-group col-xs-12 col-sm-6">
                <div class="col-xs-3"><label>个人博客/主页</label></div>
                <div class="col-xs-9">
                    <input name="blogUrl" type="url" class="form-control"
                           placeholder="您可以填写您的博客/网页地址，请以http(s)开头"
                           value="${aimUser.blogUrl}">
                </div>
            </div>

            <div class="form-group col-xs-12">
                <label for="editor">个人简介</label>
                <textarea id="id_resume" name="resume" class="form-control hidden">${aimUser.resume}</textarea>
                <div id="editor">${aimUser.resume}</div>
            </div>

            <div id="info_alert" class="form-group col-xs-12 alert alert-warning" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>警告！</strong>
                <font></font>
            </div>
            <div id="info_alert_s" class="form-group col-xs-12 alert alert-success" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>保存成功！</strong>
                <font></font>
            </div>

            <div class="form-group col-xs-12">
                <button type="submit" class="btn btn-success">保存</button>
            </div>
        </form>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">头像</div>
        <form id="form_photo" onsubmit="return check_photo();" class="panel-body" method="post">
            <input type="number" name="id" value="${aimUser.id}" hidden>

            <div class="form-group col-xs-12">
                <div class="col-xs-6">
                    <label for="preview">照片预览</label><br>
                    <s:if test="#request.photo_path!=null">
                        <img id="preview" src="<%=rootPath%>/<s:property value="#request.photo_path"/>" style="width: 260px">
                    </s:if>
                    <s:else>
                        <img id="preview" src="<%=rootPath%>/template/images/smallPic/defaultphoto.jpg" style="width: 260px">
                    </s:else>
                </div>
            </div>

            <div class="form-group col-xs-12">
                <div class="col-xs-6">
                    <input name="photo" type="file" accept="image/*" class="form-control" onchange="show_photo(this)">
                </div>
            </div>

            <div id="photo_alert" class="form-group col-xs-12 alert alert-warning" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>警告！</strong>
                <font></font>
            </div>
            <div id="photo_alert_s" class="form-group col-xs-12 alert alert-success" style="display: none;">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <strong>保存成功！</strong>
                <font></font>
            </div>

            <div class="form-group col-xs-12">
                <button class="btn btn-success">保存</button>
            </div>
        </form>

    </div>


</div>
<%@include file="/template/footer.jsp"%>
<%-- 富文本编辑器wangeditor --%>
<script src="//unpkg.com/wangeditor/release/wangEditor.min.js"></script>
<script type="text/javascript">
    var E = window.wangEditor;
    var editor = new E('#editor');
    // 图片上传说明书 https://www.kancloud.cn/wangfupeng/wangeditor3/335782
    editor.customConfig.uploadImgServer = "<%=rootPath%>/file_wangUploadImage.action"
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
            insertImg("<%=rootPath%>/"+result.data);

            // result 必须是一个 JSON 格式字符串！！！否则报错
        }
    }
    editor.create();
    //把数据读出来放到输入框
    //editor.txt.html("");
</script>

<script type="text/javascript">

    function check_pwd() {
        $("#password_alert").hide()
        $("#password_alert_s").hide()
        var p1=$("input[name='password']").val()
        var p2=$("input[name='password2']").val()
        var pold=$("input[name='old_password']").val()
        if(p1!=p2){
            $("#password_alert font").text("两次输入的新密码不一致！"+new Date())
            $("#password_alert").show()
        }else{
            $.ajax({
                url:"<%=rootPath%>/user_modify_pwd",
                type:"post",
                data:$("#form_pwd").serialize(),
                success:function (result) {
                    result=$.parseJSON(result) //字符串转换为json
                    if(result.res=="true"){
                        $("#password_alert_s font").text(new Date())
                        $("#password_alert_s").show()
                    }else{
                        $("#password_alert font").text("您输入的旧密码不正确！ "+new Date())
                        $("#password_alert").show()
                    }
                }
            })
        }
        return false;  //一定要return false，否则form会自己提交一次
    }

    function check_info() {
        $("#info_alert").hide()
        $("#info_alert_s").hide()
        $("#id_resume").val(editor.txt.html())
        $.ajax({
            url:"<%=rootPath%>/user_modify_info",
            type:"post",
            data:$("#form_info").serialize(),
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                if(result.res=="true"){
                    $("#info_alert_s font").text(new Date())
                    $("#info_alert_s").show()
                }else{
                    $("#info_alert font").text(result.msg+new Date())
                    $("#info_alert").show()
                }
            },
            error:function (res) {
                alert("系统错误！保存失败")
            }
        })
        return false;  //一定要return false，否则form会自己提交一次
    }

    function show_photo(fileDom) {
        $("#photo_alert_s").hide()
        //判断是否支持FileReader
        if(window.FileReader) {
            var reader = new FileReader();
        } else {
            alert("您的设备不支持图片预览功能，如需该功能请更换或升级您的设备！");
        }
        //获取文件
        var file = fileDom.files[0];
        var imageType = /^image\//;
        //是否是图片
        if(!imageType.test(file.type)) {
            alert("请选择图片！");
            return;
        }
        //读取完成
        reader.onload = function(e) {
            //获取图片dom
            var img = document.getElementById("preview");
            //图片路径设置为读取的图片
            img.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }

    function check_photo() {
        var formData = new FormData();
        formData.append('upFile', $("input[name='photo']")[0].files[0]);  //添加图片信息的参数
        formData.append('id', ${aimUser.id});  //添加图片信息的参数
        console.log(formData.get("upFile"))
        $.ajax({
            url:"<%=rootPath%>/user_modify_photo",
            type:"post",
            data:formData,
            dataType:"json",
            cache: false, //上传文件不需要缓存
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                console.log(result)
                if(result.res=="true"){
                    $("#photo_alert_s font").text(new Date())
                    $("#photo_alert_s").show()
                }else{
                    alert(result.msg)
                }
            },
            error:function (res) {
                alert("保存失败！请保证图片大小不超过8MB")
            }
        })
        return false;  //一定要return false，否则form会自己提交一次
    }

</script>
</body>
</html>
