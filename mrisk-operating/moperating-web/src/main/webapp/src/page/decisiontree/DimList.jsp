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

            $("#newBuilt").on("click", function () {
                $("#popWrapper").show();
            });

            $("#closeButton").on("click", function () {
                $("#popWrapper").hide();
            });

            $("#submitButton").on("click",function () {
                $("#saveForm").submit();
            })

        });


        function delDim(code,id){
            if (window.confirm("***不要轻易删除维表,**** 你确认删除吗？")){
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/dtDimController/delDim.do'/>",
                    dataType:'json',
                    async : false,
                    data:{
                        "code":code,
                        "id":id
                    },
                    error: function(request) {
                        showTempErrorPop("Connection error");
                    },
                    success: function(data) {
                        showTempSuccessPop("删除成功");
                        $("#"+code+id).remove();
                    }
                })
            }
        }
    </script>
</head>
<body>
    <div class="content">
        <div class="list mar20">
            <div class="btn-add" id="newBuilt">新建</div>
            <table cellpadding='0' cellspacing='0' class='table mar20'>
                <thead>
                    <tr>
                        <td>字典种类</td>
                        <td>字典ID</td>
                        <td>字典名称</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr id="${item.code}${item.id}">
                        <td>${item.code}</td>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>
                            <div class="operate">
                                <i class="iconfont" title="删除" onclick="delDim('${item.code}','${item.id}')"></i>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <div class="clear"></div>
        </div>
    </div>
    <!--弹框-->
    <div class="pop-wrapper"  id="popWrapper" style="display: none">
        <form method="post" id="saveForm" action="<c:url value='/dtDimController/saveDim.do'/>">
            <div class="pop-black"></div>
            <div class="pop">
                <div class="pop-title">
                    <div class="pop-close cancelButton" >关闭</div>
                    <h2>新建拒绝码</h2>
                </div>
                <div class="pop-cnt">
                    <table cellpadding='0' cellspacing='0' class='table-inside'>
                        <tr>
                            <td>字典种类</td>
                            <td>
                                <div class="input-text" style='width:300px;'>
                                    <input type="hidden" class="input" name="code" value="${param.code}">
                                    <input type="text" class="input" name="displayCode" value="${param.code}" disabled="disabled" style="background-color:#999900">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>字典编码</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input type="text" class="input" name="id"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>字典名称:</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input type="text" class="input" name="name"></div>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <div class="pop-btns">
                                    <div class="btn-blue left pop_submit_btn" style="margin-right: 20px" id="submitButton">保存</div>
                                    <div class="btn-white left close" style="margin-left: 20px" id="closeButton">返回</div>
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