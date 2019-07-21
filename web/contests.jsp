
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>竞赛汇总-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">

        <div class="panel-body">
            <a href="${pageContext.request.contextPath}/contest_home"
                class="<s:property value="res==true?'bg-success':''"/> ">等你来战</a>
            /
            <a href="${pageContext.request.contextPath}/contest_home?ending=true"
               class="<s:property value="res==false?'bg-success':''"/>">温故知新</a>
        </div>

        <s:if test="(#session.user.power>>3&1)==1">
            <a href="#" onclick="add_contest()" class="btn btn-primary"
               data-toggle="modal" data-target="#modalAddFolder">添加竞赛</a>
        </s:if>

        <s:if test="#request.running.size>0&&res==true">
            <div class="table-responsive">
                <table class="table">
                    <caption style="color: red">正在进行...</caption>
                    <thead>
                    <tr>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>竞赛信息</th>
                        <th>地址</th>
                    </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="#request.running" var="con">
                        <tr>
                            <td><s:property value="@Tools.DateTool@dateToStr(#con.startTime)"/> </td>
                            <td><s:property value="@Tools.DateTool@dateToStr(#con.endTime)"/> </td>
                            <td><s:property value="#con.title"/> </td>
                            <td>
                                <s:if test="#con.url!=null">
                                    <a href="<s:property value="#con.url"/>" target="_blank">进入</a>
                                </s:if>
                            </td>
                            <s:if test="(#session.user.power>>3&1)==1">
                                <td>
                                    <a href="#" onclick="update_contest(
                                        <s:property value="#con.id"/>,
                                            '<s:property value="@Tools.DateTool@dateToStr(#con.startTime,'yyyy-MM-dd_HH:mm')"/>',
                                            '<s:property value="@Tools.DateTool@dateToStr(#con.endTime,'yyyy-MM-dd_HH:mm')"/>',
                                            '<s:property value="#con.title"/>',
                                            '<s:property value="#con.url"/>'
                                            )" data-toggle="modal" data-target="#modalAddFolder">修改</a>
                                    /
                                    <a href="#">删除</a>
                                </td>
                            </s:if>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
        </s:if>

        <div class="table-responsive">
            <table class="table">
                <caption><s:property value="msg"/> </caption>
                <thead>
                    <tr>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>竞赛信息</th>
                        <th>地址</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="dataList" var="con">
                        <tr>
                            <td><s:property value="@Tools.DateTool@dateToStr(#con.startTime)"/> </td>
                            <td><s:property value="@Tools.DateTool@dateToStr(#con.endTime)"/> </td>
                            <td><s:property value="#con.title"/> </td>
                            <td class="text-nowrap">
                                <s:if test="#con.url!=null">
                                    <a href="<s:property value="#con.url"/>" target="_blank">进入</a>
                                </s:if>
                            </td>
                            <s:if test="(#session.user.power>>3&1)==1">
                                <td class="text-nowrap">
                                    <a href="#" onclick="update_contest(
                                        <s:property value="#con.id"/>,
                                        '<s:property value="@Tools.DateTool@dateToStr(#con.startTime,'yyyy-MM-dd_HH:mm')"/>',
                                        '<s:property value="@Tools.DateTool@dateToStr(#con.endTime,'yyyy-MM-dd_HH:mm')"/>',
                                        '<s:property value="#con.title"/>',
                                        '<s:property value="#con.url"/>'
                                            )" data-toggle="modal" data-target="#modalAddFolder">修改</a>
                                    /
                                    <a href="#">删除</a>
                                </td>
                            </s:if>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
        </div>

        <s:if test="res==false">
            <div class="text-center">
                <ul class="pagination">
                    <li><a href="${pageContext.request.contextPath}/contest_home?ending=true&page=<s:property value="#request.page-(#request.page==1?0:1)"/>">&laquo;</a></li>
                    <s:iterator begin="1" end="#request.pageCount" status="sta">
                        <li class="<s:property value="#request.page==#sta.index+1?'active':''"/>">
                            <a href="${pageContext.request.contextPath}/contest_home?page=${sta.index+1}&ending=true">
                                    ${sta.index+1}
                            </a>
                        </li>
                    </s:iterator>
                    <li><a href="${pageContext.request.contextPath}/contest_home?ending=true&page=<s:property value="#request.page+(#request.page==#request.pageCount?0:1)"/>">&raquo;</a></li>
                </ul>
            </div>
        </s:if>

        <s:if test="(#session.user.power>>3&1)==1">
            <div class="modal fade" id="modalAddFolder" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form id="form_folder" class="modal-content" action="${pageContext.request.contextPath}/contest_update" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">修改竞赛</h4>
                        </div>
                        <div class="modal-body" style="overflow: auto">

                            <input type="number" name="id" hidden> <!-- 文件夹主键id -->
                            <div class="form-group">
                                <label>开始时间</label>
                                <input type="datetime-local" name="startTime" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>结束时间</label>
                                <input type="datetime-local" name="endTime" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>标题</label>
                                <input type="text" name="title" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>网址</label>
                                <input type="url" name="url" class="form-control">
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确认</button>
                        </div>
                    </form><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>
        </s:if>

    </div>
</div>
<%@include file="/template/footer.jsp"%>
<script type="text/javascript">
    function update_contest(id,startTime,endTime,title,url) {
        $("#myModalLabel").html("更新竞赛");
        startTime = startTime.replace("_","T")
        endTime = endTime.replace('_','T')
        $("input[name='id']").val(id)
        $("input[name='startTime']").val(startTime)
        $("input[name='endTime']").val(endTime)
        $("input[name='title']").val(title)
        $("input[name='url']").val(url)
    }
    function add_contest() {
        $("#myModalLabel").html("添加竞赛");
        $("input[name='id']").val('')
        $("input[name='startTime']").val('')
        $("input[name='endTime']").val('')
        $("input[name='title']").val('')
        $("input[name='url']").val('')
    }
</script>
</body>
</html>
