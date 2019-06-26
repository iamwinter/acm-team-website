
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>新闻管理-<s:property value="#request.homeName"/></title>
</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="col-xs-12 col-sm-2">
            <%@include file="menu.jsp"%>
        </div>
        <div class="col-xs-12 col-sm-10">

            <div class="panel-body">
                <form action="${pageContext.request.contextPath}/news_admin_list" method="get">

                    <div class="form-group col-xs-9 col-sm-3">
                        <input type="text" name="key" class="form-control"
                               placeholder="新闻标题查找"
                               value="<s:property value="#request.key"/>">
                    </div>

                    <button type="submit" class="btn btn-success">搜索</button>
                </form>
            </div>

            <div class="text-center">
                <ul class="pagination">
                    <li><a href="${pageContext.request.contextPath}/news_admin_list?key=${key}&page=<s:property value="#request.page-(#request.page==1?0:1)"/>">&laquo;</a></li>
                    <s:iterator begin="1" end="#request.pageCount" status="sta">
                        <li class="<s:property value="#request.page==#sta.index+1?'active':''"/>">
                            <a href="${pageContext.request.contextPath}/news_admin_list?page=${sta.index+1}&key=${key}">
                                    ${sta.index+1}
                            </a>
                        </li>
                    </s:iterator>
                    <li><a href="${pageContext.request.contextPath}/news_admin_list?key=${key}&page=<s:property value="#request.page+(#request.page==#request.pageCount?0:1)"/>">&raquo;</a></li>
                </ul>
            </div>

            <div class="table-responsive">
                <table class="table" style="overflow: auto">
                    <caption>新闻列表</caption>
                    <thead>
                        <tr>
                            <th>标题</th>
                            <th>日期</th>
                            <th>公开</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <s:iterator value="dataList" var="news" status="sta">
                        <tr id="tr_${sta.index}">
                            <td><a href="${pageContext.request.contextPath}/news_show?id=<s:property value="#news.id"/>" target="_blank"><s:property value="#news.title"/></a> </td>
                            <td><s:property value="#news.created"/> </td>
                            <td>
                                <div class="checkbox checkbox-slider--b-flat">
                                    <label>
                                        <input type="checkbox" <s:property value="#news.isPublic==1?'checked':''"/>
                                               onchange="switch_public(<s:property value="#news.id"/>,this)">
                                        <span></span>
                                    </label>
                                </div>
                            </td>

                            <td class="text-nowrap">
                                <a href="${pageContext.request.contextPath}/news_editNews?id=<s:property value="#news.id"/>" target="_blank">编辑</a>
                                /
                                <a href="javascript:void(0)" onclick="delete_news(<s:property value="#news.id"/>,'tr_${sta.index}')">删除</a>
                            </td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>

            <div class="text-center">
                <ul class="pagination">
                    <li><a href="${pageContext.request.contextPath}/news_admin_list?key=${key}&page=<s:property value="#request.page-(#request.page==1?0:1)"/>">&laquo;</a></li>
                    <s:iterator begin="1" end="#request.pageCount" status="sta">
                        <li class="<s:property value="#request.page==#sta.index+1?'active':''"/>">
                            <a href="${pageContext.request.contextPath}/news_admin_list?page=${sta.index+1}&key=${key}">
                                    ${sta.index+1}
                            </a>
                        </li>
                    </s:iterator>
                    <li><a href="${pageContext.request.contextPath}/news_admin_list?key=${key}&page=<s:property value="#request.page+(#request.page==#request.pageCount?0:1)"/>">&raquo;</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>

<script type="text/javascript">

    function switch_public(news_id,that_input) {
        that_input.disabled=true;
        $.ajax({
            url:"${pageContext.request.contextPath}/news_change_public",
            type:"post",
            data:{'id':news_id},
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                if(result.res==true){
                    toastr.options.positionClass = 'toast-bottom-left';
                    toastr.success('修改成功')
                }else{
                    toastr.options.positionClass = 'toast-bottom-left';
                    toastr.error(result.msg)
                    that_input.checked = !that_input.checked;
                }
                that_input.disabled=false;
            },
            error:function (res) {
                alert("系统错误！修改失败")
            }
        })
    }

    function delete_news(news_id,tr_id) {
        if(confirm('确定删除该用户？')){
            $.ajax({
                url:"${pageContext.request.contextPath}/news_delete",
                type:"post",
                data:{'id':news_id},
                success:function (result) {
                    result=$.parseJSON(result) //字符串转换为json
                    if(result.res==true){
                        toastr.options.positionClass = 'toast-bottom-left';
                        toastr.success('删除成功！')
                        $('#'+tr_id).fadeOut();
                    }else{
                        toastr.options.positionClass = 'toast-bottom-left';
                        toastr.error(result.msg)
                    }
                },
                error:function (res) {
                    alert("系统错误！修改失败")
                }
            })
        }
    }
</script>

</body>
</html>
