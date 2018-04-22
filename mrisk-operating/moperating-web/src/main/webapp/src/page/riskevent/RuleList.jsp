<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <title>风控事件中心_命中规则详情</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/common/jquery.page.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/js/app/component/skin/WdatePicker.css"/>">
   <script type="text/javascript" src="<c:url value="/src/js/app/component/WdatePicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/jquery.page.js"/>"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#search_btn").on("click",function(){
                $("#searchForm").submit();
            });
            $("#sceneNo").on("onchange",function(){
                $("#searchForm").submit();
            })
            $("#page").Page({
                totalPages:"${page.totalPage}",//分页总数
                liNums: 9,//分页的数字按钮数(建议取奇数)
                currentPage:"${page.currentPage}",
                activeClass:'activP', //active 类样式定义
                limit: "${page.pageSize}",
                totalCount: "${page.totalCount}",
                callBack : function (page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });
        })

    </script>
</head>
<body>
    <div class="content"> 
        <div class="search clearfix">
            < class='search-ul left clearfix'>
            <form method="post" id="searchForm" action='${basePath}/monitor/ruleList.do'>
                <ul>
                <li class="left">
                    <label>时间</label>
                    <div class="input-date"><input type="text" class='input' onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchDate" value="${searchDate}"></div>
                </li>

                <li class="left">

                    <label>场景名称</label>
                    <select class="input-select" name="sceneNo" id="sceneNo" style="width:200px;">
                        <c:forEach items="${allScenes}" var="item">
                            <option value="${item.sceneNo}" <c:if test="${item.sceneNo eq sceneNo}">selected="selected"</c:if>>${item.name}(${item.sceneNo})</option>
                        </c:forEach>
                    </select>

                </li>

            </ul>
                </form>
            <div id="search_btn" class="btn left">查询</div>
        </div>
        <!--search end-->
        <div class="list mar20">
            <table cellpadding='0' cellspacing='0' class='table'>
                <thead>
                    <tr>
                        <td>规则名称</td>
                        <td>规则编号</td>
                        <td>场景名称</td>
                        <td>事件数</td>
                        <td>命中事件数</td>
                        <td>命中比例</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status" >
                    <tr>
                        <td>${item.ruleName}</td>
                        <td>${item.ruleNo}</td>
                        <td>${item.sceneName}</td>
                        <td>${item.totalEvents}</td>
                        <td>${item.hitCount}</td>
                        <td>${item.hitRatio}</td>
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
</body>
</html>