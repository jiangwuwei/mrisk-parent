<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <title>风控事件中心_设备指纹获取率</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/js/app/component/skin/WdatePicker.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/WdatePicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/echarts3/echarts.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/romateUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/model/riskevents/deviceFingerPrint.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/page/deviceFingerPrintRate.js"/>"></script>
</head>

<body>
<div class="content">
    <input type="hidden" value="${basePath}" id="basePath">
    <div class="search clearfix">
        <form method="post" id="searchForm" action='<c:url value="/riskFraud/deviceQuantity.do"/>'>
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>时间</label>
                    <div class="input-date">
                        <input type="text" class='input' name="queryDate" id="queryDate"
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value=""></div>
                </li>
                <li class="left">
                    <label>平台</label>
                    <select class="input-select" name="platform" id="platform" style="width:120px;">
                        <option value="">全部</option>
                        <option value="WEB">WEB</option>
                        <option value="IOS">IOS</option>
                        <option value="ANDROID">ANDROID</option>
                    </select>
                </li>
                <li class="left">
                    <label>场景名称</label>
                    <select class="input-select" id="sceneNo" name="scene">

                    </select>
                </li>
            </ul>
        </form>
        <a href='javascript:void(0);' class="btn left" id="search_btn">查询</a>
    </div>

    <!--search end-->
    <div class="list mar20">
        <div class="echart-shebei left" id="deviceFingerPrint">

        </div>
        <div class="list-shebei left">
            <h2>设备获取率(获取失败数/总数)</h2>
            <table cellpadding='0' cellspacing='0' id="tableList">

            </table>
        </div>
    </div>
    </div>

</body>
</html>