<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!--<div class="slide-item1 clearfix mar20" style='display:none;' id="decisionTreeEventDiv" >-->
<div class="box item1-left" style="overflow-x: auto;overflow-y:hidden;padding:0px">
    <!--一个树-->
    <div class="box item1-left" style="height:1000px;width:1200px;" id="decisionTree">
        <!--一个树-->
    </div>
</div>

<input type="hidden" id="dt_policy_id" value="${dt_policy_id}">
<input type="hidden" id="dt_hitNodeId_str" value="${hitNodeIdStr}">
<input type="hidden" id="dt_risk_busi_type" value="${riskBusiType}">
<div class="item1-right" style="width:255px">
    <div class="box item1-r-ttop" style="width:255px">
        <h2>决策树属性</h2>
        <table cellpadding='0' cellspacing='0'>
            <tbody>
            <c:forEach items="${executeRouteList}" var="executeRoute" varStatus="varStatus">
                <tr>
                    <td width="50%" style="padding:5px;">${empty executeRoute.chineseName ? executeRoute.routeName : executeRoute.chineseName}：</td>
                    <td>${executeRoute.actualValue}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="box item1-r-top mar20" style="width:255px">
        <h2>事件属性</h2>
        <table cellpadding='0' cellspacing='0'>
            <tbody>
            <c:forEach items="${sceneVoList}" var="sceneVo" varStatus="varStatus">
                <tr>
                    <td width='40%' style="padding:5px;">${sceneVo.chName}：${sceneVo.value}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="box item1-r-bot mar20" style="width:255px">
        <h2>环境设备信息</h2>
        <table cellpadding='0' cellspacing='0'>
            <tbody>
            <tr>
                <td width='70px' style="padding:5px;">来源ip：${sourceIp}</td>
            </tr>
            <tr>
                <td style="padding:5px;">用户ID：${uid}</td>
            </tr>
            <tr>
                <td style="padding:5px;">平台：${platform}</td>
            </tr>
            <tr>
                <td valign='top' style="padding:5px;">token：${token}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<!--</div>-->