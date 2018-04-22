<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="pop-wrapper" style='display:none;' id="hitRuleDetailDiv">
    <div class="pop-black"></div>
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close" onclick="popClose()">关闭</div>
            <h2>命中规则详情</h2>
        </div>
        <div class="pop-cnt pop-guize">
            <ul class="guize-side" id="ruleNameId">
                <c:forEach items="${hitRules}" var="item" varStatus="status">
                    <li ruleIndex="${status.index}" id="ruleName${status.index}" onclick="clickPopHitRule(this)">${item.name}</li>
                </c:forEach>
            </ul>
            <c:forEach items="${hitRules}" var="item" varStatus="status">
                <div class="guize-cnt clearfix" id="ruleDetail${status.index}" style="display:none">
                    <dl>
                        <dt>得分</dt>
                        <dd>${item.score}</dd>
                    </dl>
                    <dl>
                        <dt>指标数</dt>
                        <dd>${empty item.hitQuotasCount ? 0 : item.hitQuotasCount }</dd>
                    </dl>
                    <dl>
                        <dt>操作指令</dt>
                        <dd>${empty item.actionCode ? '暂无' : item.actionCode }</dd>
                    </dl>
                    <c:forEach items="${item.hitQuotas}" var="quotaVo">
                        <dl style="width:100%">
                            <dt style="font-weight:bold;margin-bottom:5px;">【${quotaVo.key}】:</dt>
                            <dt>${quotaVo.value}</dt>
                        </dl>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>
    </div>
</div>