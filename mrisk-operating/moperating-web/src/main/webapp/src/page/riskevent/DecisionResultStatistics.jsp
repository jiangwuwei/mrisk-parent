<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <title>风控事件中心_决策结果统计</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value='/src/js/app/component/echarts3/echarts.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/WdatePicker.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/model/riskevents/eventStatistic.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/model/riskevents/riskTrendPie.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/model/riskevents/riskTrend.js'/>"></script>
    <script>
        $(function () {
            $("#search_btn").on('click', function () {
                var queryDate = $("#queryDate").val();
                var scene = $("#sceneNo option:selected").val();
                var platform = $("#platform option:selected").val();
                getEventData({
                    queryDate: queryDate,
                    scene: scene,
                    platform: platform
                });
            });
            getEventData({
                queryDate: null,
                scene: null,
                platform: null
            });
            getSceneConfig();
            getNow();
            getEventToday();
        })

    </script>
</head>
<body>
    <input type="hidden" id="contextPath" value="<%= request.getContextPath() %>" >
    <div class="content">
        <ul class='colors clearfix'>
            <li>
                <h3>今日拒绝事件</h3>
                <p><span id="count3">0</span><i id="ratio3">0.0%</i></p>
            </li>
            <li>
                <h3>今日人工审核事件</h3>
                <p><span id="count2">0</span><i id="ratio2">0.0%</i></p>
            </li>
            <li>
                <h3>今日通过事件</h3>
                <p><span id="count1">0</span><i id="ratio1">0.0%</i></p>
            </li>
            <li>
                <h3>今日事件总量</h3>
                <p><span id="count4">0</span></p>
            </li>
        </ul>
        <form method="post" id="searchForm" action='<c:url value="/riskFraud/eventQuantity.do"/>'>
        <div class="search clearfix mar20">
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>时间</label>
                    <div class="input-date"><input type="text" class='input' onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="queryDate" id="queryDate" value=""></div>
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
            <a href='#' class="btn left" id="search_btn">查询</a>
        </div>
        </form>
        <!--search end-->
        <div class="list mar20">
            <div class="echart-shebei left" id="eventStatistic">
                <!--这是echart占位-->
            </div>
            <div class="list-shebei left">
                <h2>设备获取率(获取失败数/总数)</h2>
                <table cellpadding='0' cellspacing='0'>
                    <tr>
                        <td width='120px'>
                            <div class="circle" style="border:0px;border-radius: 0px;" id="canvasScoreOne"></div>
                        </td>
                        <td>
                            <dl class='list-shebei-dl'>
                                <dt>拒绝</dt>
                                <dd id="7dayRefuseTotalCount">0</dd>
                            </dl>
                        </td>
                    </tr>
                    <tr>
                        <td width='120px'>
                            <div class="circle" style="border:0px;border-radius: 0px;" id="canvasScoreTwo"></div>
                        </td>
                        <td>
                            <dl class='list-shebei-dl'>
                                <dt>人工审核事件</dt>
                                <dd id="7dayVerifyTotalCount">0</dd>
                            </dl>
                        </td>
                    </tr>
                    <tr>
                        <td width='120px'>
                            <div class="circle" style="border:0px;border-radius: 0px;" id="canvasScoreThree"></div>
                        </td>
                        <td>
                            <dl class='list-shebei-dl'>
                                <dt>通过</dt>
                                <dd id="7dayPassTotalCount">0</dd>
                            </dl>
                        </td>
                    </tr>
                    <tr>
                        <td width='120px'>
                            <div class="circle" style="border:0px;border-radius: 0px;" id="canvasScoreFour"></div>
                        </td>
                        <td>
                            <dl class='list-shebei-dl'>
                                <dt>总计</dt>
                                <dd id="7dayTotalCount">0</dd>
                            </dl>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</body>
</html>