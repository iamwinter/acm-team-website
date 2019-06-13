
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>学习专区-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <form id="form_y_s" action="study_folders" method="get">
            <input type="number" name="forYear" value="${forYear}" hidden>
            <input type="number" name="subjectId" value="${subjectId}" hidden>
            <div>
                年份：
                <s:iterator value="#request.years" var="y" status="sta">
                    <s:if test="#sta.index>0">/</s:if>
                    <a href="javascript:change_year(${y})" class="${y==forYear?'bg-success':''}">${y}</a>
                </s:iterator>
            </div>
            <div>
                科目：
                <s:iterator value="#request.subjects" var="subject" status="sta">
                    <s:if test="#sta.index>0">/</s:if>
                    <a href="javascript:change_subject(${subject.id})" class="${subject.id==subjectId?'bg-success':''}">${subject.name}</a>
                </s:iterator>
            </div>
            <script type="text/javascript">
                function change_year(y) {
                    $("input[name='forYear']").val(y)
                    $("#form_y_s").submit()
                }
                function change_subject(sub) {
                    $("input[name='subjectId']").val(sub)
                    $("#form_y_s").submit()
                }
            </script>
        </form>

        <!-- 模态框（Modal）添加文件夹 -->
        <s:if test="#session.user.isSuper==1">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin_subject" target="_blank">添加科目</a>
            <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#modalAddFolder">新建文件夹</a>
            <div class="modal fade" id="modalAddFolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form id="form_addFolder" onsubmit="return addFolder();" class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">文件夹的添加/修改/移动</h4>
                        </div>
                        <div class="modal-body" style="overflow: auto">

                            <div class="form-group col-xs-12">
                                <!-- 年份 -->
                                <label for="id_year">备考年份</label>
                                <select name="forYear" id="id_year" class="form-control" required>
                                    <script type="text/javascript">
                                        now_year = new Date().getUTCFullYear();
                                        for(var i=now_year+1;i>=now_year-5;i--)
                                            $("#id_year").append("<option value='"+i+"' "+(i==${forYear}?'selected':'')+">"+i+"</option>")
                                    </script>
                                </select>
                            </div>

                            <div class="form-group col-xs-12">
                                <!-- 科目 -->
                                <label for="id_subjectId">所属科目</label>
                                <select name="subjectId" id="id_subjectId" class="form-control" required>
                                    <s:iterator value="#request.subjects" var="subject">
                                        <option value="${subject.id}" ${subjectId==subject.id?'selected':''}>${subject.name}</option>
                                    </s:iterator>
                                </select>
                            </div>

                            <div class="form-group col-xs-12">
                                <!-- 主题 -->
                                <label>文件夹名称</label>
                                <input type="text" name="title" class="form-control" autocomplete="off" required>
                            </div>

                            <div class="form-group col-xs-12">
                                <!-- 简单描述 -->
                                <label for="id_subjectId">文件夹简述</label>
                                <textarea name="resume" rows="4" class="form-control" placeholder="简单描述一下文件夹所存储的资料吧"></textarea>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确认添加</button>
                        </div>
                    </form><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>
        </s:if>

        <table class="table">
            <thead>
                <tr>
                    <th>文件夹</th>
                    <th>描述</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="dataList" var="folder">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/study_files?id=<s:property value="#folder.id"/>">
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

    </div>
</div>
<%@include file="/template/footer.jsp"%>
<script type="text/javascript">

    function addFolder(){
        $.ajax({
            url:"${pageContext.request.contextPath}/study_addFolder",
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
