
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>学习专区-<%=homeName%></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <form id="form_y_s" action="study_study" method="get">
            <input type="number" name="forYear" hidden>
            <input type="number" name="subjectId" hidden>
            <div>
                年份：
                <s:iterator value="#request.years" var="y" status="sta">
                    <s:if test="#sta.index>0">/</s:if>
                    <a href="javascript:change_year(${y})" class="${y==requestScope.forYear?'bg-success':''}">${y}</a>
                </s:iterator>
            </div>
            <div>
                科目：
                <s:iterator value="#request.subjects" var="subject" status="sta">
                    <s:if test="#sta.index>0">/</s:if>
                    <a href="javascript:change_subject(${subject.id})" class="${subject.id==requestScope.subjectId?'bg-success':''}">${subject.name}</a>
                </s:iterator>
            </div>
            <script type="text/javascript">
                function change_year(y) {
                    $("input[name='subjectId']").val(${subjectId})
                    $("input[name='forYear']").val(y)
                    $("#form_y_s").submit()
                }
                function change_subject(sub) {
                    $("input[name='forYear']").val(${forYear})
                    $("input[name='subjectId']").val(sub)
                    $("#form_y_s").submit()
                }
            </script>
        </form>


        <table class="table">
<%--            <caption>标题。。。。</caption>--%>
            <thead>
                <tr>
                    <th>文件夹</th>
                    <th>描述</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="#request.folders" var="folder">
                    <tr>
                        <td>
                            <a href="<%=rootPath%>/study_files?fatherId=<s:property value="#folder.id"/>">
                                <s:property value="#folder.title"/>
                            </a>
                        </td>
                        <td><s:property value="#folder.resume"/> </td>
                        <s:if test="#session.user.isSuper==1">
                            <td>
                                <a href="#">修改</a>
                                /
                                <a href="#">删除</a>
                            </td>
                        </s:if>
                    </tr>
                </s:iterator>
            </tbody>
        </table>

        <s:if test="#session.user.isSuper==1">
            <div class="panel panel-default">
                <div class="panel-heading">分享文件</div>
                <div class="panel-body">
                    <div class="alert alert-info">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>温馨提示：</strong>
                        <p>点击文件框后，您可以按住ctrl键选择多个文件进行上传!</p>
                        <p>文件上传总和不能超过2G!</p>
                    </div>

                    <form onsubmit="return studyUpload();" class="panel-body">

                        <div class="form-group col-xs-12">
                            <!-- 文件夹 -->
                            <div class="col-xs-12 col-sm-4">
                                <label for="id_folder">选择文件夹</label>
                                <select id="id_folder" class="form-control" required>
                                    <s:iterator value="#request.folders" var="folder">
                                        <option value="${folder.id}">${folder.title}</option>
                                    </s:iterator>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-xs-12">
                            <div class="col-xs-12 col-sm-4">
                                <label for="study_file">资料文件（可批量选择）</label>
                                <input id="study_file" type="file" class="form-control" multiple required>
                            </div>
                        </div>
                        <button class="btn btn-success">上传</button>
                    </form>

                    <div class="alert alert-warning">
                            <%--                <a href="#" class="close" data-dismiss="alert">&times;</a>--%>
                        <strong>警告！</strong>
                        <font>上传过程请不要关闭浏览器！也尽量不要关闭此页面</font>
                    </div>

                    <div id="id_show_progress" class="col-xs-9">
                        上传进度
                    </div>
                </div>

            </div>
        </s:if>

        <s:if test="#session.user.isSuper==1">
            <div class="panel panel-default">
            <div class="panel-heading">创建文件夹</div>
            <div class="panel-body">

                <form id="form_addFolder" onsubmit="return addFolder();" class="panel-body">
                    <div class="form-group col-xs-12">
                        <div class="col-xs-12 col-sm-4">
                            <!-- 年份 -->
                            <label for="id_year">备考年份</label>
                            <select name="forYear" id="id_year" class="form-control" required>
                                <script type="text/javascript">
                                    now_year = new Date().getUTCFullYear();
                                    for(var i=now_year+1;i>=now_year-5;i--)
                                        $("#id_year").append("<option value='"+i+"'>"+i+"</option>")
                                </script>
                            </select>
                        </div>
                    </div>

                    <div class="form-group col-xs-12">
                        <!-- 科目 -->
                        <div class="col-xs-12 col-sm-4">
                            <label for="id_subjectId">备考科目</label>
                            <select name="subjectId" id="id_subjectId" class="form-control" required>
                                <s:iterator value="#request.subjects" var="subject">
                                    <option value="${subject.id}">${subject.name}</option>
                                </s:iterator>
                            </select>
                        </div>
                    </div>

                    <div class="form-group col-xs-12">
                        <!-- 主题 -->
                        <div class="col-xs-12 col-sm-4">
                            <label>文件夹名称</label>
                            <input type="text" name="title" class="form-control" required>
                        </div>
                    </div>

                    <div class="form-group col-xs-12">
                        <!-- 简单描述 -->
                        <div class="col-xs-12 col-sm-4">
                            <label for="id_subjectId">文件夹简述</label>
                            <textarea name="resume" rows="4" class="form-control" placeholder="简单描述一下文件夹所存储的资料吧" required></textarea>
                        </div>
                    </div>
                    <button class="btn btn-success">确定创建文件夹</button>
                </form>
            </div>

        </div>
        </s:if>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
<script type="text/javascript">

    function studyUpload() {
        var formData = new FormData();
        files = $("#study_file")[0].files;
        for(var i=0;i<files.length;i++){
            formData.append('upFile', files[i]);  //添加图片信息的参数
        }
        showProgress();
        formData.append('fatherId',$("#id_folder").val())
        alert("文件已经开始上传，请耐心等待，不要关闭此窗口！上传完成后会提示您")
        $.ajax({
            url:"<%=rootPath%>/study_upload",
            type:"post",
            data:formData,
            dataType:"json",
            cache: false, //上传文件不需要缓存
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                console.log(result)
                if(result.res==true){
                    alert("上传完成!")
                }else{
                    alert(result.msg)
                }
            },
            error:function (res) {
                alert("上传失败！未知错误")
            }
        })
        return false;  //一定要return false，否则form会自己提交一次
    }

    function showProgress() {
        // 显示文件上传进度
        for(var i=0;i<$("#study_file")[0].files.length;i++){
            $("#id_show_progress").append("<div id=\"progress"+i+"\" class=\"progress\">\n" +
                "\t<div class=\"progress-bar\" style=\"width: 0%;\">\n" +
                "\t\t<span class=\"sr-only\">40% 完成</span>\n" +
                "\t</div>\n" +
                "</div>")
        }
    }

    function addFolder(){
        $.ajax({
            url:"<%=rootPath%>/study_addFolder",
            type:"post",
            data:$('#form_addFolder').serialize(),
            dataType:"json",
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                console.log(result)
                if(result.res==true){
                    window.location.reload()
                }else{
                    alert(result.msg)
                }
            },
            error:function (res) {
                alert("提交失败！未知错误")
            }
        })
        return false;  //一定要return false，否则form会自己提交一次
    }

</script>
</body>
</html>
