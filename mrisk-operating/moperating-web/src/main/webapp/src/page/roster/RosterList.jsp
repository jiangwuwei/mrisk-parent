<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <title>名单库管理_名单库导入</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/src/css/common/jquery.page.css'/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/jquery.page.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".cancelButton").on("click", function () {
                $("#popWrapper").hide();
            });
            var uidReg = /^(\d{7,11})$/;
            var phoneReg = /^(1\d{10})$/;
            var idReg=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
            var bankNoReg = /^(\d{16}|\d{19})$/;
            $("#savaButton").on("click",function () {
                var  content = $("#content").val().replace(new RegExp(/(\r)/g),"").split("\n");
                var rosterType = $("#rosterType").val();
                for( var i = 0 ; i < content.length; i++){
                    var cn = $.trim(content[i]);
                    if ( rosterType == 1 ){
                        if( !uidReg.test(cn) ){
                            showTempErrorPop("[" + cn + "]不符合uid的格式!");
                            return;
                        }
                    }else  if ( rosterType == 2 ){
                        if( !phoneReg.test(cn) ){
                            showTempErrorPop("[" + cn + "]不符合手机号码的格式!");
                            return;
                        }
                    }else  if ( rosterType == 3 ){
                        if( !idReg.test(cn) ){
                            showTempErrorPop("[" + cn + "]不符合身份证号码的格式!");
                            return;
                        }
                    }else  if ( rosterType == 4 ){
                        if( !bankNoReg.test(cn) ){
                            showTempErrorPop("[" + cn + "]不符合银行卡的格式!");
                            return;
                        }
                    }
                }
                if (window.confirm("您确定执行导入操作吗?")){
                    var jsonData = $("#saveForm").serialize();
                    if ($("#rosterBusiType").val() == 200 && $("#rosterType").val() != 1) {
                        showTempErrorPop("信用用户只支持uid类型,不支持其他内容类型!");
                        return;
                    }
                    $.ajax({
                        type: "POST",
                        url: "<c:url value='/rosterController/rosterBatchSave.do'/>",
                        dataType: 'json',
                        data: jsonData,
                        error: function (request) {
                            showTempErrorPop("Connection error");
                        },
                        success: function (data) {
                            if ( data.resultCode ){
                                var failure ="";
                                var error = false;
                                $.each(data.errorList, function(index,value){
                                    failure +=  value+"\n";
                                    error = true;
                                });
                                if ( error ){
                                    failure = "\n导入失败记录如下:\n" + failure;
                                }
                                showTempSuccessPop("保存操作成功!" +　failure　);
                                window.location.reload();
                            }
                        }
                    });
                }
            });

            function selectRosterOrigin(){
                var rosterBusiType = $("#rosterBusiType").val();
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/rosterController/getRosterOrigin.do'/>",
                    dataType:'json',
                    data:{
                        "rosterBusiType":rosterBusiType
                    },
                    error: function(request) {
                        showTempErrorPop("Connection error");
                    },
                    success: function(data) {
                        $("#rosterOrigin").empty();
                        $.each(data,function (index,value) {
                            $("#rosterOrigin").append("<option value='"+value.id+"'>"+value.name+"("+value.id+")</option>");
//                        document.getElementById("rosterOrigin").add(new Option(name,id));
                        })
                    }
                });
            };
            $("#newBuilt").on("click", function () {
                $("#popWrapper").show();
            });
            $("#rosterBusiType").on("change", function(){
                selectRosterOrigin();
            });
            $("#search_btn").on("click",function () {
                $("#searchForm").submit();
            })
            selectRosterOrigin();
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#page").Page({
                totalPages: ${page.totalPage},//分页总数
                liNums: 9,//分页的数字按钮数(建议取奇数)
                currentPage:${page.currentPage},
                activeClass:'activP', //active 类样式定义
                limit: ${page.pageSize},
                totalCount: ${page.totalCount},
                callBack : function (page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });
        });

        function deleteRoster(rosterBusiType,id){
            if (window.confirm("确认删除？")){
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/rosterController/deleteRoster.do'/>",
                    dataType:'json',
                    data:{
                        "rosterBusiType":rosterBusiType,
                        "id":id
                    },
                    error: function(request) {
                        showTempErrorPop("Connection error");
                    },
                    success: function(data) {
                        showTempSuccessPop("删除成功");
                        $("#"+id).remove();
                    }
                })
            }
        }
    </script>
</head>
<body>
    <div class="content">
        <form method="post" id="searchForm" action="<c:url value='/rosterController/getContentList.do'/>">
        <div class="search clearfix">
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>名单库类型</label>
                    <select class="input-select" style='width:200px;' id="rosterBusiType" name="rosterBusiType">
                        <c:forEach items="${rosterBusiTypeList}" var="item">
                            <option value="${item.id}" <c:if test="${item.id eq rosterBusiType}">selected="selected"</c:if>>&nbsp;${item.name}(${item.id})</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>内容类型</label>
                    <select class="input-select"  name="rosterType">
                        <c:forEach items="${rosterTypeList}" var="item">
                            <option value="${item.id}" <c:if test="${item.id eq rosterType}">selected="selected"</c:if>>&nbsp;${item.name}</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>内容</label>
                    <div class="input-text"><input type="text" class="input" name="content" value="${param.content}"></div>
                </li>
            </ul>
            <div id = "search_btn" class="btn left">查询</div>
        </div>
            </form>
        <!--search end-->
        <div class="list mar20">
            <div class="btn-add" id="newBuilt">新建</div>
            <table cellpadding='0' cellspacing='0' class='table mar20'>
                <thead>
                    <tr>
                        <td>名单来源</td>
                        <td>内容类型</td>
                        <td>内容</td>
                        <td>操作人</td>
                        <td>入库时间</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr id="${item.id}">
                        <td>${item.rosterOriginName}</td>
                        <td>${item.rosterTypeName}</td>
                        <td>${item.content}</td>
                        <td>${item.creator}</td>
                        <td><fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm:ss" value="${item.createdDate}" /></td>
                        <td>
                            <div class="operate">
                                <i class="iconfont" title="删除" onclick="deleteRoster('${rosterBusiType}','${item.id}',this)"></i>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <c:if test="${not empty list}">
                <div id="page" style="float:right;"></div>
            </c:if>
            <div class="clear"></div>
        </div>
    </div>
    <!--弹框-->
    <div class="pop-wrapper" id="popWrapper" style="display: none">
        <form method="post" id="saveForm" action=''>
        <div class="pop-black"></div>
        <div class="pop">
            <div class="pop-title">
                <div class="pop-close cancelButton" >关闭</div>
                <h2>新建名单库</h2>
            </div>
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <td>名单库类型</td>
                        <td>
                            <select class="input-select" style='width:200px;' name="rosterBusiType">
                                <c:forEach items="${rosterBusiTypeList}" var="item">
                                <option value="${item.id}" <c:if test="${item.id eq id}">selected="selected"</c:if>>&nbsp;${item.name}(${item.id})</option>
                                </c:forEach>

                        </td>
                    </tr>
                    <tr>
                        <td>数据来源</td>
                        <td>
                            <select class="input-select" style='width:200px;' id="rosterOrigin" name="rosterOrigin">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>内容类型:</td>
                        <td>
                            <select class="input-select" style='width:200px;' id="rosterType" name="rosterType">
                                <c:forEach items="${rosterTypeList}" var="item">
                                    <option value="${item.id}" <c:if test="${item.id eq id}">selected="selected"</c:if>>&nbsp;${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>内容</td>
                        <td>
                            <textarea name="content" id="content" cols="50" rows="10" class='text-area'></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="pop-btns">
                                <div id="savaButton"  class="btn-blue left" style="margin-right: 5%">保存</div>
                                <div id="cancelButton" class="btn-white left cancelButton" style="margin-left: 5%">返回</div>
                            </div>
                        </td>
                    </tr>
                </table>

            </div>
        </div>
            </form>
    </div>

</body>
</html>