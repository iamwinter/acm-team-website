<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/template/headTag.jsp"%>
    <title>考研资料-</title>
<%--    视频播放器的css和js--%>
    <link rel="stylesheet" type="text/css" href="<%=rootPath%>/template/video/css/index.css">

</head>
<body>
<%@include file="/template/header.jsp"%>
<div class="main bkcolorhalf">
    <div class="bigContainer">
        <div class="form-group">
            <a href="javascript:history.back()">返回上一级</a>
        </div>

        <div class="text-center">
            <div id="psVideo" class="psVideo">
                <video src="https://blz-videos.nosdn.127.net/1/HearthStone/f6cd63b590d416821d3e27e0.mp4"
                       class="my-psVideo"
                       id="my-video" poster="<%=rootPath%>/template/video/source/1.jpg"
                       preload="metadata">
                </video>
                <div class="psVideo-loading">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <div id="id_play" class="psVideo-play-one"></div>
                <div class="psVideo-shade psVideo-shade-on">
                    <div class="psVideo-play-footer">
                        <div class="psVideo-progress">
                            <span class="psVideo-timeBar"></span>
                        </div>
                        <div class="psVideo-play-btn psVideo-stop"></div>
                        <div class="psVideo-time">
                            <span id="currentTime">00:00</span>
                            <span>/</span>
                            <span id="duration">00:00</span>
                        </div>
                        <div class="psVideo-dan">
                            <input type="text" class="psVideo-dan-input" placeholder="点击输入弹幕,回车键发送"/>
                            <span class="psVideo-dan-btn">发送</span>
                        </div>
                        <div class="psVideo-right-btn">
                            <div class="psVideo-btn all"></div>
                            <div class="psVideo-btn loop" id="loop"></div>
                            <div class="psVideo-btn set">
                                <div class="set-list">
                                    <div class="play-speed">播放速度</div>
                                    <div class="play-speed-list">
                                        <span id="speed05Btn">X0.5</span>
                                        <span class="moren" id="speed1Btn">X1</span>
                                        <span id="speed2Btn">X2</span>
                                    </div>
                                </div>
                                <div class="konge1"></div>
                            </div>
                            <div class="psVideo-btn huazhi">自动</div>
                            <div class="psVideo-btn sound" id="soundBtn">
                                <div class="sound-list">
                                    <div class="sound-number">90</div>
                                    <div class="volume">
                                        <span class="volumeBar"></span>
                                    </div>
                                </div>
                                <div class="konge"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="player-list">
                    <div class="player-list-bk">
                        <div class="player-list-head">播放列表</div>
                        <div class="player-list-cotent">
                            <div class="player-list-box">
                                <div class="player-list-video video-now">炉石传说</div>
                                <div class="player-list-video">突然之间</div>
                                <div class="player-list-video">进阶</div>
                                <div class="player-list-video">渐渐</div>
                                <div class="player-list-video">年少有为</div>
                                <div class="player-list-video">爱久见人心</div>
                                <div class="player-list-video">盗将行</div>
                                <div class="player-list-video">Lemon</div>
                                <div class="player-list-video">我的梦</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script type="text/javascript" src="<%=rootPath%>/template/video/javascript/index.js"></script>
        </div>

        <hr>
        <button class="btn btn-primary">下载所有文件</button>

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
                            <s:if test="#file.path.contains('mp4')">
                                <a onclick="playVideo('<%=rootPath%>/${file.path}')" style="cursor: pointer">播放</a>
                                /
                            </s:if>
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

        <s:if test="#session.user.isSuper==1">
            <div class="panel panel-default">
                <div class="panel-heading">上传文件</div>
                <div class="panel-body">
                    <div class="alert alert-info">
                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                        <strong>温馨提示：</strong>
                        <p>1.点击文件框后，您可以按住ctrl键选择多个文件进行上传!</p>
                        <p>2.断点续传，如果意外关闭页面，可重新选择文件，系统将继续上次上传位置</p>
                    </div>

                    <form onsubmit="return studyUpload();" class="panel-body">
                        <div class="form-group col-xs-12 col-sm-4 ">
                                <label for="study_file">资料文件（按住ctrl多选）</label>
                                <input id="study_file" type="file" class="form-control" onchange="fileSelected()" multiple required>
                        </div>
                        <div class="form-group col-xs-12">
                            <button type="submit" class="btn btn-success">上传</button>
                        </div>
                    </form>

                    <button disabled id="btn_show_progress" class="btn btn-primary" data-toggle="modal" data-target="#myModal">查看文件上传进度</button>
                    <!-- 模态框（Modal） -->
                    <div class="modal fade" id="myModal" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title" id="myModalLabel">文件上传进度</h4>
                                </div>
                                <div id="tags_progress" class="modal-body">
                                    <div class="alert alert-danger">
                                        <a href="#" class="close" data-dismiss="alert">&times;</a>
                                        <strong>请勿关闭当前网页！</strong>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div><!-- /.modal-content -->
                        </div><!-- /.modal -->
                    </div>

                </div> <!-- end of panel-body -->

            </div>
        </s:if>

    </div>
</div>
<%@include file="/template/footer.jsp"%>

<script type="text/javascript">
    function playVideo(path) {
        $("#my-video").attr("src",path)
        $("#psVideo").fadeIn(1000)
        $('body,html').animate({scrollTop:0},1000);
        $("#id_play").click();
    }

    //监听选择文件信息，显示文件名、上传进度等
    function fileSelected() {
        //HTML5文件API操作
        var files = $("#study_file")[0].files;
        if(files.length==0)$("#btn_show_progress").attr('disabled',true)
        else $("#btn_show_progress").removeAttr('disabled')
        $("#tags_progress").html('')    //先清空
        for(var i=0;i<files.length;i++){
            var file=files[i];  //第i个文件
            var fileSize = 0;
            if (file.size > 1024 * 1024)
                fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
            else
                fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
            var tag_progress=
                "<p>"+file.name+" ["+fileSize+"]"+"</p>\n" +
                "<div class='progress'>\n" +
                "    <div id='tag_progress"+i+"' class='progress-bar' style='background-color: #2aabd2;color: black'>\n" +
                "        0\n" +
                "    </div>\n" +
                "</div>\n";
            $("#tags_progress").append(tag_progress)
        }
    }

    // form表单提交事件
    function studyUpload() {
        $("#btn_show_progress").click(); //打开模态框 显示进度信息
        var files = $("#study_file")[0].files;  //获取所有文件
        for(var i=0;i<files.length;i++){
            prepareUpload(files[i],i)   //遍历文件进行上传
        }
        return false;
    }

    // 上传一个文件的准备工作,实际上是查询已上传大小，同时创建一个临时文件保存已上传大小
    // uploadFileWithCut函数递归完成上传，每次一片 1MB,过程中查询临时文件
    function prepareUpload(file,fileIndex) {
        var testxhr = new XMLHttpRequest(); //使用这个玩意上传
        var formData = new FormData();
        formData.append('fatherId',${fatherId})
        formData.append('fileName',file.name);
        formData.append('fileSize',file.size);

        testxhr.onload = function(evt){
            var start = parseInt(JSON.parse(evt.target.responseText).uploadSize);
            if(start==-1)
                alert("该文件已存在或重名，请重新上传！\n"+file.name)
            else
                uploadFileWithCut(file,start,fileIndex);  //切割文件并上传
        };
        testxhr.open("POST", "<%=rootPath%>/file_getUploadedSize");
        testxhr.send(formData);
    }


    //递归上传一个文件，每层切割一小块上传
    function uploadFileWithCut(file,start,fileIndex) {
        var xhr = new XMLHttpRequest();
        var fd = new FormData();
        //关联表单数据,可以是自定义参数
        fd.append('fatherId',${fatherId});    //父文件夹id
        fd.append("fileName", file.name);
        fd.append("fileSize", file.size);
        var end = start + 1024*1024;//文件分片上传，每片1M
        var endFlag = false;
        if(end>=file.size){
            end = file.size;
            endFlag = true;
        }
        fd.append("upFile", file.slice(start,end));//上传一片文件
        fd.append("uploadSize", end);   //本次上传之后，已上传大小

        //监听事件
        // xhr.addEventListener("load", function (ev) { alert("上传完成！") }, false);
        //xhr.addEventListener("error", function (ev) { alert("上传失败:\n"+file.name) }, false);
        //xhr.addEventListener("abort", function (ev) { alert("您取消上传文件:\n"+file.name) }, false);
        xhr.upload.addEventListener("progress", function(evt){
            //刷新一次进度
            if (evt.lengthComputable) {
                //var percentComplete = Math.round(evt.loaded * 100 / evt.total);
                var percent = Math.round((start+evt.loaded) * 100 / file.size).toString()+'%';
                $("#tag_progress"+fileIndex).css('width',percent);
                $("#tag_progress"+fileIndex).html(percent);
            } else {
                $("#tag_progress"+fileIndex).html('上传失败');
            }
        }, false);
        xhr.onload = function(evt){
            if(!endFlag){
                uploadFileWithCut(file,end,fileIndex);   //前一片传完后，递归传下一片
            }
        };
        //发送文件和表单自定义参数
        xhr.open("POST", "<%=rootPath%>/file_doUpload");
        xhr.send(fd);
    }

</script>
</body>
</html>
