<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>决策树策略管理</title>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value='/src/js/lib/jquery-3.1.0.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/page/scenesPolicies.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            $(".label_tab").on("click", function(){
                if ($(this).find('.iconfont').hasClass('up')) {
                    $(this).find('.iconfont').removeClass('up');
                    $(this).find('.iconfont').addClass('down');
                    $(this).find(".iconfont").html("&#xe612");
                }else{
                    $(this).find('.iconfont').removeClass('down');
                    $(this).find('.iconfont').addClass('up');
                    $(this).find(".iconfont").html("&#xe69a");
                }
//                $(this).find(".iconfont").html("&#xe612");
                $(this).closest(".list-celue").find(".list-celue-ul").slideToggle();
            });

            $(".label_tab_2").on("click", function(){
                var liTable = $(this).parent().parent().next();
                if ( liTable ){
                    liTable.slideToggle();
                }
            });
        });

        function  closeWin() {
            $("#wrapper").hide();
        }

        function addQuotaForScene(sceneNo) {
            $("#wrapper").load("<c:url value='/dtQuotaController/selectQuota.do'/>", {"sceneNo":sceneNo}, function () {
                $("#wrapper").show();
            })
        }

        function editQuota(quotaId) {
            $("#wrapper").load("<c:url value='/dtQuotaController/findQuota.do'/>", {"quotaId":quotaId}, function () {
                $("#wrapper").show();
            })
        }


        function saveQuotaForPolicies () {
            if ( confirm("您确认保存吗?")) {
                var jsonData = $("#quotaTemplateForm").serialize();
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/dtQuotaController/saveQuotaForPolicies.do'/>",
                    dataType: 'json',
                    data: jsonData,
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.resultCode) {
                            showTempSuccessPop("保存成功");
                            var div = $("#wrapper").hide();
                            window.location.reload();
                        } else {
                            showTempErrorPop("保存失败,可能原因是参数名称重复!");
                        }
                    }
                })
            }
        }

        function saveQuota () {
            if ( confirm("您确认保存吗?")) {
                var jsonData = $("#quotaForm").serialize();
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/dtQuotaController/saveQuota.do'/>",
                    dataType: 'json',
                    data: jsonData,
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.resultCode) {
                            showTempSuccessPop("保存成功");
                            var div = $("#wrapper").hide();
                            window.location.reload();
                        } else {
                            showTempErrorPop("保存失败,可能原因是参数名称重复!");
                        }
                    }
                })
            }
        }

        function saveQuotaParam () {
            if ( confirm("您确认保存吗?")) {
                var jsonData = $("#quotaParamForm").serialize();
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/dtQuotaController/updateQuotaParam.do'/>",
                    dataType: 'json',
                    data: jsonData,
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.resultCode) {
                            showTempSuccessPop("保存成功");
                            var div = $("#wrapper").hide();
                            window.location.reload();
                        } else {
                            showTempErrorPop("保存失败,可能原因是参数名称重复!");
                        }
                    }
                })
            }
        }



        function delQuota (id) {
            if ( confirm("您确定要从场景中删除该指标吗")) {
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/dtQuotaController/delQuota.do'/>",
                    dataType: 'json',
                    data: {
                        "id": id
                    },
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.resultCode) {
                            showTempSuccessPop("删除成功");
                            $("#quota_" + id).remove();
                            $("#quota_detail_" + id).remove();
                        } else {
                            showTempErrorPop("Can not delete a referred parameter!");
                        }
                    }
                })
            }
        }

        function editQuotaParam(quotaId, paramName) {
            $("#wrapper").load("<c:url value='/dtQuotaController/findQuotaParam.do'/>", {"quotaId":quotaId,"paramName":paramName}, function () {
                $("#wrapper").show();
            })
        }
    </script>
</head>
<body>
<div class="list mar20 tablebox">
    <c:forEach var="item" items="${list}" varStatus="status">
        <div class="list-celue">
        <div class="list-celue-title clearfix">
            <p class="list-icons">
                <i class="iconfont" title="添加" onclick="addQuotaForScene('${item.sceneNo}')"></i>
            </p>
            <h2><div class="label_tab" style="width: 70%">
                <i class="iconfont up"></i><span>${item.name}(${item.sceneNo})</span>
            </div>
            </h2>
        </div>
        <ul class="list-celue-ul" style="display: block;" id="sceneNo_${item.sceneNo}">
            <c:forEach var="quotas" items="${item.quotasList}" varStatus="qstatus">
                <li class="clearfix" id="quota_${quotas.id}">
                    <p class="list-icons">
                        <i class="iconfont" title='编辑指标' onclick="editQuota('${quotas.id}')">&#xe6f5;</i>
                        <i class="iconfont" title='删除指标' onclick="delQuota('${quotas.id}')">&#xe738;</i>
                    </p>

                    <h2>
                        <div class="label_tab_2" style="width: 70%">
                        <i class="dot"></i><span>${quotas.chineseName}<span style="color:blue">
                            【${quotas.quotaNo} - ${quotas.paramName} -
                            <c:if test="${quotas.sourceType == 2}">外部服务</c:if><c:if test="${quotas.sourceType == 1}">上下文(内部)</c:if> -
                            <c:if test="${quotas.dataType == 1}">数字类型</c:if><c:if test="${quotas.dataType == 2}">字符串</c:if><c:if test="${quotas.dataType == 3}">boolean数字</c:if>】</span></span>
                        </div>
                    </h2>
                </li>
                <li class="clearfix" style="display:none" id="quota_detail_${quotas.id}">
                    <c:if test="${quotas.sourceType == 2}">
                        <table style="width:95%;text-align:center;" border="1px">
                            <thead>
                                <tr>
                                    <td>参数中文名</td>
                                    <td>参数名</td>
                                    <td>参数类型</td>
                                    <td>是否必须</td>
                                    <td>缺省值</td>
                                    <td>参数描述</td>
                                    <td>操作</td>
                                </tr>
                            </thead>
                            <c:forEach var="para" items="${quotas.paramsList}" varStatus="pstatus">
                                <tbody>
                                    <tr id="para_${para.name}">
                                        <td>${para.chineseName}</td>
                                        <td>${para.name}</td>
                                        <td>
                                            <c:if test="${para.dataType == 1}">数字类型</c:if>
                                            <c:if test="${para.dataType == 2}">字符串</c:if>
                                            <c:if test="${para.dataType == 3}">boolean数字</c:if>
                                        </td>
                                        <td>
                                            <c:if test="${para.mandatory == 0}">可为空</c:if>
                                            <c:if test="${para.mandatory == 1}"><span style="color:red;">不许空</span></c:if>
                                        </td>
                                        <td>
                                            <c:if test="${ empty para.defaultValue}">空值</c:if>
                                            <c:if test="${ not empty para.defaultValue}">${para.defaultValue}</c:if>
                                        </td>
                                        <td>${para.description}</td>
                                        <td>
                                            <div class="operate">
                                                <i class="iconfont" title="编辑" onclick="editQuotaParam('${quotas.id}','${para.name}')"></i>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
    </c:forEach>
</div>
<div class="pop-wrapper" style="display:none;"  id="wrapper"></div>
</body>
</html>