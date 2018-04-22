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
                editParamTemplate(null);
            });
        });

        function  closeWin() {
            $(".new-pop-wrapper").hide();
        }


        function  editParamTemplate(id) {
            $("#wrapper").load("<c:url value='/quotaMetaController/findParamTemplate.do'/>", {"id":id}, function () {
                $(".new-pop-wrapper").show();
            })
        }

        function  delParamTemplate(id) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/quotaMetaController/delParamTemplate.do'/>",
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
            var jsonData = $("#templateForm").serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/quotaMetaController/saveParamTemplate.do'/>",
                dataType:'json',
                data:jsonData,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.resultCode ) {
                        showTempSuccessPop("保存成功");
                        var div = $(".new-pop-wrapper").hide();
                    }else{
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }
    </script>
</head>
<body>
    <div class="content">
        <div class="btn-add btn-add-long" id="newBuilt">新建参数模板</div>
        <div class="list mar20">
            <table cellpadding='0' cellspacing='0' class='table mar20'>
                <thead>
                    <tr>
                        <td>参数中文名</td>
                        <td>参数名</td>
                        <td>参数类型</td>
                        <td>参数缺省值</td>
                        <td>是否必须</td>
                        <td>最后修改时间</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr id="${item.id}">
                        <td style="width:20%">${item.chineseName}</td>
                        <td>${item.name}</td>
                        <td style="width:10%">
                            <c:if test="${item.dataType == 1}">数字类型</c:if>
                            <c:if test="${item.dataType == 2}">字符串</c:if>
                            <c:if test="${item.dataType == 3}">boolean数字</c:if>
                        </td>
                        <td style="width:10%">
                            <c:if test="${ empty item.defaultValue}">空值</c:if>
                            <c:if test="${ not empty item.defaultValue}">${item.defaultValue}</c:if>
                        </td>
                        <td style="width:10%">
                            <c:if test="${item.mandatory == 0}">可为空</c:if>
                            <c:if test="${item.mandatory == 1}"><font color="red">不许空</font></c:if>
                        </td>
                        <td style="width:15%"> <fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm:ss" value="${item.modifiedDate}" /></td>
                        <td style="width:10%">
                            <div class="operate">
                                <i class="iconfont" title="编辑" onclick="editParamTemplate('${item.id}')"></i>
                                &nbsp;&nbsp;
                                <i class="iconfont" title="删除" onclick="delParamTemplate('${item.id}')"></i>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="clear"></div>
        </div>
    </div>

    <div class="pop-wrapper new-pop-wrapper" style="display:none;"  id="wrapper">

    </div>
</body>
</html>