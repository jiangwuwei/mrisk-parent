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
                editDefinition(null);
            });
        });

        function  closeWin() {
            $(".new-pop-wrapper").hide();
        }


        function  editDefinition(id) {
            $("#wrapper").load("<c:url value='/quotaDefinitionController/findQuotaDefinition.do'/>", {"id":id}, function () {
                $(".new-pop-wrapper").show();
            })
        }

        function  editParamInstance(id) {
            $("#wrapper").load("<c:url value='/quotaDefinitionController/findParamInstance.do'/>", {"id":id}, function () {
                $(".new-pop-wrapper").show();
            })
        }

        function  delDefinition(id) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/quotaDefinitionController/delQuotaDefinition.do'/>",
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

        function saveParamTemplate() {
            var jsonData = $("#definitionForm").serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/quotaDefinitionController/saveQuotaDefinition.do'/>",
                dataType:'json',
                data:jsonData,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.resultCode ) {
                        showTempSuccessPop("保存成功");
                        var div = $(".new-pop-wrapper").hide();
                        window.location.reload();
                    }else{
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }

        function saveDefinitionTemplate() {
            var jsonData = $("#definitionForm").serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/quotaDefinitionController/saveDefinitionTemplate.do'/>",
                dataType:'json',
                data:jsonData,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.resultCode ) {
                        showTempSuccessPop("保存成功");
                        var div = $(".new-pop-wrapper").hide();
                        window.location.reload();
                    }else{
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }

        function saveParamInstance() {
            var jsonData = $("#instanceForm").serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/quotaDefinitionController/saveParamInstance.do'/>",
                dataType:'json',
                data:jsonData,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.resultCode ) {
                        showTempSuccessPop("保存成功");
                        var div = $(".new-pop-wrapper").hide();
                        window.location.reload();
                    }else{
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }


        function selectDefinition(id){
            $("#wrapper").load("<c:url value='/quotaDefinitionController/selectDefinitionTemplate.do'/>", {"id":id}, function () {
                $(".new-pop-wrapper").show();
            })
        }

        function toggleSubTable( trObj) {
            jQuery(trObj).next().toggle();
        }

        function  delParamInstance(id) {
            if ( confirm("***请您再三确认是否需要删除该属性?***") ) {
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/quotaDefinitionController/delParamInstance.do'/>",
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
                            $("#instance" + id).remove();
                        } else {
                            showTempErrorPop("Can not delete a referred parameter!");
                        }
                    }
                })
            }
        }

    </script>
</head>
<body>
    <div class="content">
        <div class="btn-add btn-add-long" id="newBuilt">新建接入指标</div>
        <div class="list mar20">
            <table cellpadding='0' cellspacing='0' class='table mar20'>
                <thead>
                    <tr>
                        <td>接入厂商</td>
                        <td>指标中文名</td>
                        <td>指标参数标识</td>
                        <td>指标类型</td>
                        <td>指标描述</td>
                        <td>同步状态</td>
                        <td>最后修改时间</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr id="${item.id}" onclick="toggleSubTable(this)">
                        <td width="10%">${item.sourceName}</td>
                        <td width="15%">${item.chineseName}</td>
                        <td>${item.paramName}</td>
                        <td>
                            <c:if test="${item.dataType == 1}">单值类型</c:if>
                            <c:if test="${item.dataType == 2}">多值类型</c:if>
                        </td>
                        <td width="15%">${item.description}</td>
                        <td width="10%">
                            <c:if test="${item.sycStatus == 0}"><span style="color:red">未同步</span></c:if>
                            <c:if test="${item.sycStatus == 1}">已同步</c:if>
                        </td>
                        <td><fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm:ss" value="${item.modifiedDate}" /></td>
                        <td width="10%">
                            <c:if test="${item.sycStatus == 0}">
                                <div class="operate">
                                    <i class="iconfont" title="添加" onclick="selectDefinition('${item.id}')"></i>
                                    <i class="iconfont" title="编辑" onclick="editDefinition('${item.id}')"></i>
                                    <i class="iconfont" title="删除" onclick="delDefinition('${item.id}')"></i>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    <tr style="display:none">
                        <td colspan="8">
                            <table style="width:98%;text-align:center" border="1px">
                                <thead>
                                <tr>
                                    <td style="width:15%"> 参数中文名</td>
                                    <td style="width:20%">参数名</td>
                                    <td style="width:10%">参数类型</td>
                                    <td style="width:10%">是否必须</td>
                                    <td style="width:10%">缺省值</td>
                                    <td>参数描述</td>
                                    <td style="width:10%">操作</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="para" items="${item.paramsList}" varStatus="paramStatus">
                                    <tr id="instance${para.id}">
                                        <td>${para.chineseName}</td>
                                        <td>${para.name}</td>
                                        <td>
                                            <c:if test="${para.dataType == 1}">数字类型</c:if>
                                            <c:if test="${para.dataType == 2}">字符串</c:if>
                                            <c:if test="${para.dataType == 3}">boolean数字类型</c:if>
                                        </td>
                                        <td>
                                            <c:if test="${para.mandatory == 0}">可为空</c:if>
                                            <c:if test="${para.mandatory == 1}"><font color="red">不许空</font></c:if>
                                        </td>
                                        <td>
                                            <c:if test="${ empty para.defaultValue}">空值</c:if>
                                            <c:if test="${ not empty para.defaultValue}">${para.defaultValue}</c:if>
                                        </td>
                                        <td>${para.description}</td>
                                        <td>
                                            <c:if test="${item.sycStatus == 0}">
                                                <div class="operate">
                                                    <i class="iconfont" title="编辑"  onclick="editParamInstance('${para.id}')"></i>
                                                    &nbsp;&nbsp;
                                                    <i class="iconfont" title="删除" onclick="delParamInstance('${para.id}')"></i>
                                                </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="clear"></div>
        </div>
    </div>
    <div class="pop-wrapper new-pop-wrapper" style="display:none;"  id="wrapper"></div>
</body>
</html>