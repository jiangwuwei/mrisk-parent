<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--
<div class="slide-item1 mar20" style='display:none;' id="antiFraudEventDiv">
-->
    <div class="box">
        <dl class='item2-top'>
            <dt><div class="circle">${empty finalValue?'0':finalValue}</div></dt>
            <dd>
                <ul>
                    <li>得分：${empty finalValue?'0':finalValue}</li>
                    <li>命中规则数：${hitCount}</li>
                    <li>决策结果：${decisionCode eq 0? '<span style="color:#ff0000;">异常</span>': decisionCode eq 1?'通过':(decisionCode eq 2?'人工审核':'<span style="color:#ff0000;">拒绝</span>')}</li>
                </ul>
            </dd>
        </dl>
        <div class="item2-bot">
            <h2>命中规则</h2>
            <ul class='clearfix'>
                <c:forEach items="${hitRules}" var="item" varStatus="status">
                    <li><a href="#" ruleIndex="${status.index}" onclick="popHitRule(this)">${item.name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="box mar20">
        <h2>事件属性</h2>
        <table cellpadding='0' cellspacing='0'>
            <c:forEach items="${sceneVoList}" var="sceneVo" varStatus="varStatus">
                    <c:if test="${(varStatus.index+1)%2==1}">
                        <tr>
                        <td width='60%' style="padding:5px;">${sceneVo.chName}：${sceneVo.value}</td>
                    </c:if>
                    <c:if test="${(varStatus.index+1)%2==0}">
                        <td width='40%' style="padding:5px;">${sceneVo.chName}：${sceneVo.value}</td>
                        </tr>
                    </c:if>
            </c:forEach>
        </table>
    </div>
    <div class="box mar20">
        <h2>环境设备信息</h2>
        <table cellpadding='0' cellspacing='0'>
            <tr>
                <td style="padding:5px;">来源ip：${sourceIp}</td>
                <td style="padding:5px;">用户ID：${uid}</td>
            </tr>
            <tr>
                <td colspan='2' style="padding:5px;">平台：${platform}</td>
            </tr>
            <tr>
                <td colspan='2' style="padding:5px;">token:${token}</td>
            </tr>
        </table>
    </div>
<!--</div>-->


<%@include file="HitRuleDetailDiv.jsp" %>