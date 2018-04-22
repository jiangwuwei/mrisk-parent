<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>指标元数据管理</title>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/src/css/common/jquery.page.css'/>">
    <script type="text/javascript" src="<c:url value='/src/js/lib/jquery-3.1.0.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript">
        $(function(){
            $("#newBuilt").on("click", function () {
                editQuotaTemplate(null);
            });
        });

        function  closeWin() {
            $("#wrapper").hide();
        }


        function  editQuotaTemplate(id) {
            $("#wrapper").load("<c:url value='/dtQuotaTemplateController/findQuotaTemplate.do'/>", {"id":id}, function () {
                $("#wrapper").show();
            })
        }


        function  delQuotaTemplate(id) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/dtQuotaTemplateController/delQuotaTemplate.do'/>",
                dataType:'json',
                data:{
                    "id":id
                },
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.resultCode ) {
                        showTempSuccessPop("删除成功");
                        $("#" + id).remove();
                    }else{
                        showTempErrorPop("Can not delete a referred parameter!");
                    }
                }
            })
        }

        function saveQuotaTemplate() {
            var jsonData = $("#quotaForm").serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/dtQuotaTemplateController/saveQuotaTemplate.do'/>",
                dataType:'json',
                data:jsonData,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.resultCode ) {
                        showTempSuccessPop("保存成功");
                        var div = $("#wrapper").hide();
                        window.location.reload();
                    }else{
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }


        function toggleSubTable( trObj) {
            jQuery(trObj).next().toggle();
        }

    </script>
</head>
<body>
    <div class="content">
        <div class="btn-add btn-add-long" id="newBuilt">新建指标</div>
        <div class="list mar20">
            <table cellpadding='0' cellspacing='0' class='table mar20'>
                <thead>
                    <tr>
                        <td>接入厂商</td>
                        <td>指标中文名</td>
                        <td>指标参数名</td>
                        <td>指标类型</td>
                        <td>指标来源</td>
                        <td>指标描述</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr id="${item.id}"  <c:if test="${item.sourceType == 2}">onclick="toggleSubTable(this)"</c:if>>
                        <td>${item.sourceName}</td>
                        <td>${item.chineseName}</td>
                        <td>${item.paramName}</td>
                        <td>
                            <c:if test="${item.dataType == 1}">数字类型</c:if>
                            <c:if test="${item.dataType == 2}">字符串</c:if>
                            <c:if test="${item.dataType == 3}">boolean数字类型</c:if>
                        </td>
                        <td>
                            <c:if test="${item.sourceType == 1}">上下文(内部)</c:if>
                            <c:if test="${item.sourceType == 2}">外部服务</c:if>
                        </td>
                        <td width="15%">${item.description}</td>
                        <td>
                            <c:if test="${item.sourceType == 1}">
                                <div class="operate">
                                    <i class="iconfont" title="编辑" onclick="editQuotaTemplate('${item.id}')"></i>
                                    <i class="iconfont" title="删除" onclick="delQuotaTemplate('${item.id}')"></i>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    <tr style="display:none">
                        <td colspan="7">
                            <c:if test="${item.sourceType == 2}">
                                <table style="width:95%;text-align:center;" border="1px">
                                <thead>
                                <tr>
                                    <td style="width:20%"> 参数中文名</td>
                                    <td style="width:20%">参数名</td>
                                    <td style="width:10%">参数类型</td>
                                    <td style="width:10%">是否必须</td>
                                    <td style="width:10%">缺省值</td>
                                    <td>参数描述</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="para" items="${item.paramList}" varStatus="paramStatus">
                                    <tr>
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
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="clear"></div>
        </div>
    </div>
    <div class="pop-wrapper" style="display:none;"  id="wrapper"></div>
</body>
</html>