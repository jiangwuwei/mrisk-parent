<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <div class="box">
        <dl class='item2-top' style="padding:5px">
            <dt><div class="circle">${scardAttrVoMap['scardScore'].value}</div></dt>
            <dd>
                <ul>
                    <li>评分卡分数：<span style="font-weight: bold;color:blue">${scardAttrVoMap['scardScore'].value}</span></li>
                    <li>命中评分卡策略：${detailAttVoMap['scard'].name}</li>
                    <c:if test="${ not empty scardAttrVoMap['scardRuleFinal']}"><li>评分卡规则结果：${scardAttrVoMap['scardRuleFinal'].value}</li></c:if>
                    <c:if test="${ not empty detailAttVoMap['scardRouter']}"><li>命中路由：${detailAttVoMap['scardRouter'].name}</li></c:if>
                </ul>
            </dd>
        </dl>
        <div class="item2-bot">
            <h2>评分卡路径</h2>
            <ul class='clearfix'>
                    <li>
                        <table cellpadding='0' cellspacing='0' class='table'>
                            <thead><td>参数中文名称</td><td>传入参数名称</td><td>参数缺省分数</td><td>路径配置分数</td><td>路径最终得分</td><td>路径的表达式</td></thead>
                            <c:forEach items="${detailAttVoMap['scardParamRoutes']}" var="item" varStatus="status">
                            <tr>
                                <td>${item.chineseName}</td><td>${item.paramName}</td><td>${item.defaultScore}</td><td>${item.scoreCardParamRoute.routeScore}</td><td>${item.finalScore}</td><td>${item.scoreCardParamRoute.routeExpression}</td>
                            </tr>
                            </c:forEach>
                        </table>
                    </li>
            </ul>
        </div>
    </div>
    <div class="box mar20">
        <h2>变量属性</h2>
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
        <h2>事件属性</h2>
        <table cellpadding='0' cellspacing='0'>
            <tr>
                <td style="padding:5px;">事件事件：${eventAttVoMap['riskDate']}</td>
                <td style="padding:5px;">事件ID：${eventAttVoMap['riskId']}</td>
            </tr>
            <tr>
                <td style="padding:5px;">事件场景号：${eventAttVoMap['scene']}</td>
                <td style="padding:5px;">事件请求的IP：${eventAttVoMap['sourceIp']}</td>
            </tr>
            <tr>
                <td style="padding:5px;">挑战随机数(0-100)：${eventAttVoMap['extendChallengeNumber']}</td>
                <td style="padding:5px;">&nbsp;</td>
            </tr>
        </table>
    </div>