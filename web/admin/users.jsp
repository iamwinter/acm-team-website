
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>用户列表-管理员-<s:property value="#request.homeName"/></title>
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
                <form action="${pageContext.request.contextPath}/user_admin_users" method="get">

                    <div class="form-group col-xs-9 col-sm-4">
                        <input type="text" name="key" class="form-control"
                               placeholder="用户名、姓名、邮箱、年级查找"
                               value="<s:property value="#request.key"/>">
                    </div>

                    <button type="submit" class="btn btn-success">搜索</button>
                </form>
            </div>

            <div class="table-responsive">
                <table class="table text-nowrap" style="overflow: auto">
                    <caption>用户列表</caption>
                    <thead>
                        <tr>
                            <th>用户名</th>
                            <th>姓名</th>
                            <th>年级</th>
                            <th>身份</th>
                            <th>管理员</th>
                            <th>查看学习资料</th>
                            <th>锁定个人资料</th>
                            <th>管理学习资料</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="dataList" var="user" status="sta">
                            <tr id="tr_${sta.index}">
                                <td><a href="${pageContext.request.contextPath}/user_user?id=<s:property value="#user.id"/>" target="_blank"><s:property value="#user.username"/></a> </td>
                                <td><s:property value="#user.nickName"/> </td>
                                <td><s:property value="#user.grade"/> </td>
                                <td>
                                    <select id="id_tag_${sta.index}" name="tag" class="form-control"
                                            onchange="change_tag(<s:property value="#user.id"/>,this)">
                                        <option value="0"><s:property value="@dao.UserDao@tagStr[0]"/></option>
                                        <option value="1"><s:property value="@dao.UserDao@tagStr[1]"/></option>
                                        <option value="2"><s:property value="@dao.UserDao@tagStr[2]"/></option>
                                        <option value="3"><s:property value="@dao.UserDao@tagStr[3]"/></option>
                                        <script>
                                            var i=<s:property value="#user.tag"/>    //选择默认身份
                                            document.getElementById('id_tag_${sta.index}')[i].selected=true;
                                        </script>
                                    </select>
                                </td>
                                <td>
                                    <div class="checkbox checkbox-slider--b-flat">
                                        <label>
                                            <input type="checkbox" <s:property value="(#user.power&1)==1?'checked':''"/>
                                                onchange="switch_power(<s:property value="#user.id"/>,0,this)">
                                            <span></span>
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="checkbox checkbox-slider--b-flat">
                                        <label>
                                            <input type="checkbox" <s:property value="(#user.power>>1&1)==1?'checked':''"/>
                                                   onchange="switch_power(<s:property value="#user.id"/>,1,this)">
                                            <span></span>
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="checkbox checkbox-slider--b-flat">
                                        <label>
                                            <input type="checkbox" <s:property value="(#user.power>>2&1)==1?'checked':''"/>
                                                   onchange="switch_power(<s:property value="#user.id"/>,2,this)">
                                            <span></span>
                                        </label>
                                    </div>
                                </td>
                                <td>
                                    <div class="checkbox checkbox-slider--b-flat">
                                        <label>
                                            <input type="checkbox" <s:property value="(#user.power>>3&1)==1?'checked':''"/>
                                                   onchange="switch_power(<s:property value="#user.id"/>,3,this)">
                                            <span></span>
                                        </label>
                                    </div>
                                </td>

                                <td>
<%--                                    模态框--%>
                                    <a href="#" onclick="set_reset(<s:property value="#user.id"/>,<s:property value="#user.username"/>)"
                                       data-toggle="modal" data-target="#modalReset">重命名</a>
                                    /
                                    <a href="${pageContext.request.contextPath}/user_modify?id=<s:property value="#user.id"/>" target="_blank">修改</a>
                                    /
                                    <a href="javascript:void(0)" onclick="delete_user(<s:property value="#user.id"/>,'tr_${sta.index}')">删除</a>
                                </td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </div>

<%--            模态框--%>
            <div class="modal fade" id="modalReset" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form id="form_reset" class="modal-content" onsubmit="return user_reset()">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">重置用户名</h4>
                        </div>
                        <div class="modal-body" style="overflow: auto">

                            <input type="number" name="id" hidden>
                            <div class="form-group">
                                <label>新用户名</label>
                                <input type="text" name="username" class="form-control"
                                       autofocus autocomplete="off" required minlength="4">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-primary">确认</button>
                        </div>
                    </form><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>


        </div>
    </div>
</div>
<%@include file="/template/footer.jsp"%>


<script type="text/javascript">

    function change_tag(user_id,that_input) {
        $.ajax({
            url:"${pageContext.request.contextPath}/user_change_tag",
            type:"post",
            data:{'id':user_id,'tag':that_input.value},
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                if(result.res==true){
                    toastr.options.positionClass = 'toast-bottom-left';
                    toastr.success('用户身份修改成功！')
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

    function switch_power(user_id,x,that_input) {
        that_input.disabled=true;
        $.ajax({
            url:"${pageContext.request.contextPath}/user_switch_power",
            type:"post",
            data:{'id':user_id,'x':x},
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

    function delete_user(user_id,tr_id) {
        if(confirm('确定删除该用户？')){
            $.ajax({
                url:"${pageContext.request.contextPath}/user_delete_user",
                type:"post",
                data:{'id':user_id},
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


    function set_reset(id,username) {
        $("input[name='id']").val(id);
        $("input[name='username']").val(username);
    }

    function user_reset() {
        $.ajax({
            url:"${pageContext.request.contextPath}/user_user_reset",
            type:"post",
            data:$("#form_reset").serialize(),
            success:function (result) {
                result=$.parseJSON(result) //字符串转换为json
                if(result.res==true){
                    alert('操作成功')
                    location.reload();
                }else{
                    alert(result.msg)
                }
            },
            error:function (res) {
                alert("系统错误！修改失败")
            }
        })
        return false;
    }

</script>
</body>
</html>