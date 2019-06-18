
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>学习科目-管理员-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-xs-12 col-sm-2">
            <%@include file="menu.jsp"%>
        </div>
        <div class="col-xs-12 col-sm-10">

            <div class="table-responsive">
                <table class="table text-nowrap" style="overflow: auto">
                    <thead>
                    <tr>
                        <th>顺序</th>
                        <th>科目名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="dataList" var="subject" status="sta">
                        <tr>
                            <td><s:property value="#sta.index+1"/> </td>
                            <td><s:property value="#subject.name"/> </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin_move_subject?studySubject.id=<s:property value="#subject.id"/>&dir=up">上移</a>
                                /
                                <a href="${pageContext.request.contextPath}/admin_move_subject?studySubject.id=<s:property value="#subject.id"/>&dir=down">下移</a>
                                /
                                    <%--   触发模态框--%>
                                <a href="#" onclick="update_sub(
                                    <s:property value="#subject.id"/>,
                                        '<s:property value="#subject.name"/>',
                                    <s:property value="#subject.priority"/>
                                        )"
                                   data-toggle="modal" data-target="#modal_update_sub">修改</a>
                                /
                                <a href="${pageContext.request.contextPath}/admin_delete_subject?studySubject.id=<s:property value="#subject.id"/>">删除</a>
                            </td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>

            <form action="${pageContext.request.contextPath}/admin_add_subject" method="post" class="panel-body">
                <div class="form-group col-xs-12 col-sm-4">
                    <label for="study_sub">添加科目</label>
                    <input id="study_sub" name="studySubject.name" placeholder="输入科目名称" class="form-control" required>
                </div>
                <div class="form-group col-xs-12">
                    <button type="submit" class="btn btn-success">确认添加</button>
                </div>
            </form>

<%--            模态框 修改科目--%>
            <div class="modal fade" id="modal_update_sub" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form action="${pageContext.request.contextPath}/admin_update_subject"
                          id="form_addFolder" class="modal-content" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">修改科目名称</h4>
                        </div>
                        <div class="modal-body" style="overflow: auto">
                            <input name="studySubject.id" hidden>
                            <input name="studySubject.priority" hidden>

                            <div class="form-group col-xs-12">
                                <label>新科目名称</label>
                                <input type="text" name="studySubject.name" class="form-control" autocomplete="off" required>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确认修改</button>
                        </div>
                    </form><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>


        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>
<script type="text/javascript">
    function update_sub(id,name,priority) {
        $("input[name='studySubject.id']").val(id)
        $("input[name='studySubject.name']").val(name)
        $("input[name='studySubject.priority']").val(priority)
    }
</script>
</body>
</html>