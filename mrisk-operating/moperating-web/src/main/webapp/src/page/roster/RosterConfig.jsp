<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>名单库管理_名单库场景配置</title>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            $("#saveButton").on("click", function () {
                if (window.confirm("您确定执行保存操作吗?")) {
                    var jsonData = $("#searchForm").serialize();
                    $.ajax({
                        type: "POST",
                        url: "<c:url value='/rosterController/rosterRestrictionSave.do'/>",
                        dataType: 'json',
                        data: jsonData,
                        error: function (request) {
                            showTempErrorPop("Connection error");
                        },
                        success: function (data) {
                            if (data.resultCode) {
                                showTempSuccessPop("保存操作成功!");
                            }
                        }
                    });
                }
            });

            $("#rosterBusiType").on("change", function () {
                selectScene();
            });
            selectScene();
        });

        function selectScene() {
            var rosterBusiType = $("#rosterBusiType").val();
            $.ajax({
                type: "POST",
                url: "<c:url value='/rosterController/getSceneNo.do'/>",
                dataType: 'json',
                data: {
                    rosterBusiType: rosterBusiType
                },
                error: function (request) {
                    showTempErrorPop("Connection error");
                },
                success: function (data) {
                    $(":checkbox[name='scenes']").each(function (n, value) {
                        var sceneJquery = $(this);
                        var currentValue = sceneJquery.val();
                        sceneJquery.prop("checked",false);
                        $.each(data, function (index, v) {
                            var sel = v.scene_no;
                            if (sel === currentValue) {
                                sceneJquery.prop("checked", true);
//                                sceneJquery.attr("checked", "checked");
                                return;
                            }
                        });
                    })
                }
            });
        }
    </script>
</head>
<body>
<div class="content">
    <form method="post" id="searchForm" action=''>
    <div class="search clearfix">
        <ul class='search-ul left clearfix'>
            <li class="left">
                <label>名单库类型</label>
                <select class="input-select" style='width:200px;' id="rosterBusiType" name="rosterBusiType">
                    <c:forEach items="${rosterTypeList}" var="item">
                        <option value="${item.id}" <c:if test="${item.id eq id}">selected="selected"</c:if>>
                            &nbsp;${item.name}(${item.id})
                        </option>
                    </c:forEach>
                </select>
            </li>
        </ul>
    </div>
    <!--search end-->
    <div class="list-box mar20">
        <ul class="list-checkbox clearfix">
            <c:forEach items="${sceneList}" var="item" varStatus="status">
                <li>
                    <div class="input-checkbox">
                        <input type="checkbox" name="scenes" value="${item.scene_no}"/>
                        <label>${item.name}(${item.scene_no})</label>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <div id="saveButton" class="btn-blue mar-auto">保存</div>
    </div>
        </form>
</div>
</body>
</html>