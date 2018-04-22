<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <title>风控事件中心_事件查询中心</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/common/jquery.page.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/js/app/component/skin/WdatePicker.css"/>">
    <script type="text/javascript" src="<c:url value='/src/js/app/component/jquery.page.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/echarts2/echarts-all.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/model/riskevents/eventDecisionTreeChart.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/eventList.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/WdatePicker.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            $("#page").Page({
                totalPages: ${totalPages},//分页总数
                liNums: 9,//分页的数字按钮数(建议取奇数)
                currentPage:${eventInputModel.currentPage},
                activeClass:'activP', //active 类样式定义
                limit: ${eventInputModel.pageSize},
                totalCount: ${totalCount},
                callBack : function (page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });
            initCondBlock();

            $("#slideControler").on("click", function () {
                getChange(null,1);
                $("#slideInfoDetail").hide();
            })
        })

    </script>
</head>
<body>
    <input type="hidden" id="basePath" value="${basePath}">
    <div class="content">
        <form method="post" id="searchForm" action="<c:url value="/monitor/eventList.do"/>">
            <input type="hidden" value="1" id="currentPage" name="currentPage">
            <input type="hidden" value="${deepSearch}" id="deepSearch" name="deepSearch">
        <div class="search clearfix">
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>时间</label>
                    <div class="input-date" style="width:150px"><input type="text" class='input' onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="startRiskDate" value="${eventInputModel.startRiskDate}"></div>
                    <label>~</label>
                    <div class="input-date" style="width:150px"><input type="text" class='input' onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" name="endRiskDate" value="${eventInputModel.endRiskDate}"></div>
                </li>
                <li class="left">
                    <label>业务类型</label>
                    <select class="input-select" name="extendMap['riskBusiType']" style="width:100px">
                        <option value="" <c:if test="${ empty eventInputModel.extendMap['riskBusiType']}">selected="selected"</c:if>>全部</option>
                        <option value="1" <c:if test="${ eventInputModel.extendMap['riskBusiType'] eq 1}">selected="selected"</c:if>>反欺诈</option>
                        <option value="2" <c:if test="${ eventInputModel.extendMap['riskBusiType'] eq 2}">selected="selected"</c:if>>决策树</option>
                        <option value="3" <c:if test="${ eventInputModel.extendMap['riskBusiType'] eq 3}">selected="selected"</c:if>>评分卡</option>
                    </select>
                </li>
                <li class="left">
                    <label>风险结果</label>
                    <select class="input-select" name="decisionCode" style="width:100px">
                        <option value="" <c:if test="${empty eventInputModel.decisionCode}">selected="selected"</c:if>>全部</option>
                        <option value="1" <c:if test="${eventInputModel.decisionCode eq 1}">selected="selected"</c:if>>通过</option>
                        <option value="2" <c:if test="${eventInputModel.decisionCode eq 2}">selected="selected"</c:if>>人工审核</option>
                        <option value="3" <c:if test="${eventInputModel.decisionCode eq 3}">selected="selected"</c:if>>拒绝</option>
                    </select>
                </li>
                <li class="left">
                    <label>用户ID/手机号码/身份证号码</label>
                    <div class="input-text"><input type="text" class="input" name="uid" value="${eventInputModel.uid}"></div>
                </li>
            </ul>
            <ul class='search-ul left clearfix'  id="deepSearchId1">
                <li class="left">
                    <label>场景名称</label>
                    <select class="input-select" style='width:293px;' name="sceneNo">
                        <option value="" <c:if test="${empty eventInputModel.sceneNo}">selected="selected"</c:if>>全部</option>
                        <c:forEach items="${allScenes}" var="item">
                            <option value="${item.sceneNo}"
                                    <c:if test="${item.sceneNo eq eventInputModel.sceneNo}">selected="selected"</c:if>>${item.name}(${item.sceneNo})</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>业务状态</label>
                    <select class="input-select" name="extendMap['riskStatus']" style="width:100px">
                        <option value="" <c:if test="${eventInputModel.extendMap['riskStatus'] eq ''}">selected="selected"</c:if>>全部</option>
                        <option value="0" <c:if test="${eventInputModel.extendMap['riskStatus'] eq '0'}">selected="selected"</c:if>>初始状态</option>
                        <option value="1" <c:if test="${eventInputModel.extendMap['riskStatus'] eq '1'}">selected="selected"</c:if>>成功</option>
                        <option value="2" <c:if test="${eventInputModel.extendMap['riskStatus'] eq '2'}">selected="selected"</c:if>>失败</option>
                    </select>
                </li>
            </ul>
            <ul class='search-ul left clearfix'  id="deepSearchId2">
                <li class="left">
                    <label>规则编号</label>
                    <div class="input-text"><input type="text" class="input" name="ruleNo" value="${eventInputModel.ruleNo}"></div>
                </li>
                <li class="left">
                    <label>设备指纹</label>
                    <div class="input-text"><input type="text" class="input" name="extendMap['deviceFingerprint']" value="${eventInputModel.extendMap['deviceFingerprint']}"></div>
                </li>
                <li class="left">
                    <label>风控riskId</label>
                    <div class="input-text"><input type="text" class="input" style="width:180px" name="extendMap['riskId']" value="${eventInputModel.extendMap['riskId']}"></div>
                </li>
            </ul>
            <a href='javascript:void(0);' class="btn left" onclick="searchEvent()">查询</a>
            <a href='javascript:void(0);' class="deep-search" onclick="deepSearch()" id="deepSearchBtn">高级搜索</a>
        </div>
        </form>
        <!--search end-->
        <div class="list mar20">
            <table cellpadding='0' cellspacing='0' class='table'>
                <thead>
                    <tr>
                        <td></td>
                        <td>事件发生时间</td>
                        <td>场景名称</td>
                        <td>风险结果</td>
                        <td>命中规则数量</td>
                        <td>用户ID</td>
                        <td>设备指纹</td>
                        <td>业务状态</td>
                        <td>IP</td>
                        <td>地理位置</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${list}" varStatus="status">
                    <tr onclick="trOnclick('${item.uid}','${item.deviceFingerprint}','${item.riskId}','${item.riskDate}',0, '${item.riskBusiType}', this)">
                        <td><i class="<c:if test="${item.riskBusiType eq 1 }">dot-red</c:if><c:if test="${item.riskBusiType eq 2 }">dot-blue</c:if><c:if test="${item.riskBusiType eq 3 }">dot-black</c:if>"></i></td>
                        <td>${item.riskDate}</td>
                        <td>${item.scenesName}</td>
                        <td>${item.decisionCode eq 0? '<span style="color:#ff0000;">异常</span>': item.decisionCode eq 1?'通过':(item.decisionCode eq 2?'人工审核':'<span style="color:#ff0000;">拒绝</span>')}</td>
                        <td>${item.hitRuleCount}</td>
                        <td>${item.uid}</td>
                        <td>${empty item.deviceFingerprint?'<span style="color:#ff0000;">无</span>':'有'}</td>
                        <td>
                            <c:choose>
                                <c:when test="${item.riskStatus eq '1'}">
                                    <i class='green'>成功</i>
                                </c:when>
                                <c:when test="${item.riskStatus eq '2'}">
                                    <i class='red'>失败</i>
                                </c:when>
                                <c:otherwise>
                                    <i class='blue' riskStatus="${item.riskStatus}">初始状态</i>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${item.sourceIp}</td>
                        <td>${item.geoAddr}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${not empty list}">
                <div id="page" style="float:right;"></div>
                <div class="clear"></div>
            </c:if>
        </div>
        <div class="slide" id="slideInfoDetail" style="display:none">
            <div class="slide-close" href="javascript:void(0);" onclick="resetValue(event)" id="slideControler"></div>
            <div class="slide-cnt">
                <ul class="slide-title clearfix">
                    <li id="localtable" onclick="getChange(this,1)">事件详情</li>
                    <li id="Saastable" onclick="getChange(this,2)">关联分析</li>
                </ul>
                <div class="slide-item1 clearfix mar20"  style='display:none;' id="decisionTreeEventDiv" ></div>
                <!--决策树事件详情 end-->
                <div class="slide-item1 mar20" id="antiFraudEventDiv" style='display:none;'></div>
                <!--反欺诈事件详情 end-->
                <div class="slide-item2 mar20" id="slideInfoContentSaas" style="display:none"></div>
                <!--关联分析 end-->
                <%--<%@include file="part/HitRuleDetailDiv.jsp" %>--%>
                <!--命中规则详情弹框end-->
            </div>
        </div>
    </div>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
</body>
</html>