<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="pop-black "></div>
<div class="pop" style='width:900px;top:50px;'>
    <div class="pop-title">
        <div class="pop-close" onclick="closePop()">关闭</div>
        <h2>${empty rule.id? '新建':'编辑'}规则(配置)</h2>
    </div>
    <div class="pop-cnt">
        <table cellpadding='0' cellspacing='0' class='table-guize'>
            <%@include file="CommonRuleInfo.jsp"%>
            <input name="ruleMode" type="hidden" value="0">
            <tr>
                <td>事件发生时间</td>
                <td>
                    <ul class="ul-radio clearfix left">
                        <c:set var="ruleCondition" value="${rule.ruleCondition}"/>
                        <c:set var="isAnyTime">
                            <c:if test="${empty rule.id}">true</c:if>
                            <c:if test="${not empty rule.id}">${ruleCondition.isAnyTime}</c:if>
                        </c:set>
                        <li><input type="radio" name="isAnyTime" value="true" <c:if test="${isAnyTime}">checked="checked"</c:if>"/>
                            <label >任意时间</label></li>
                        <li><input type="radio" name="isAnyTime" value="false" <c:if test="${not isAnyTime}">checked="checked"</c:if>"/>
                            <label >只在该时间段内</label></li>
                    </ul>
                    <ul class='ul-shijian clearfix'>
                        <li>
                            <div class="input-text left" style='width:50px;'><input type="text" class="input" name="minTime" value="${ruleCondition.minTime}"></div>
                            <p>时</p>
                        </li>
                        <li>
                            <select class="input-select" style='width:60px;'>
                                <option value="&&" <c:if test="${ruleCondition.timeOper eq '&&'}">selected="selected"</c:if>> 与 </option>
                                <option value="||" <c:if test="${ruleCondition.timeOper eq '||'}">selected="selected"</c:if>> 或 </option>
                            </select>
                        </li>
                        <li>
                            <div class="input-text left" style='width:50px;'><input type="text" class="input" name="maxTime" value="${ruleCondition.maxTime}"></div>
                            <p>时</p>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td valign='top' style='line-height:34px;'>条件设置</td>
                <td>
                    <ul class="ul-radio clearfix left">
                        <li><input type="radio" name="isAnd" value="true" <c:if test="${ruleCondition.isAnd || empty rule.id}">checked="checked"</c:if>/><label >满足所有条件</label></li>
                        <li style="width:140px;"><input type="radio" name="isAnd" value="false" <c:if test="${not ruleCondition.isAnd && not empty rule.id}">checked="checked"</c:if>/><label >满足以下任意条件</label></li>
                    </ul>
                    <ul class='ul-btns left'>
                        <li><a href="javascript:void(0);" class='btn-blue' condIndex="${fn:length(ruleCondition.conditions)}" onclick="addRuleCondition(this)">添加条件</a></li>
                        <li><a href="javascript:void(0);" class='btn-white' onclick="checkRuleContent()">查看规则</a></li>
                    </ul>
                    <div class="clear"></div>
                    <div id="for_condition">
                    </div>
                    <c:forEach items="${ruleCondition.conditions}" var="condition" varStatus="varStatus2">
                        <c:set var="condIndex" value="${varStatus2.index}" />
                        <c:set var="attr" value="${condition.attr}" />
                        <c:set var="oper" value="${condition.oper}" />
                        <c:set var="value" value="${condition.value}" />
                        <c:set var="attrType" value="${condition.attrType}" />
                        <c:set var="isValueInput" value="${condition.isValueInput}" />
                        <c:set var="extent" value="${condition.extent}" />
                        <%@include file="RuleCondition.jsp" %>
                    </c:forEach>
                </td>
            </tr>
            <tr style="display:none;" id="finalRuleContentTr">
                <td valign='top'>规则内容</td>
                <td>
                    <textarea cols="50" rows="4" class='text-area' id="finalRuleContent"></textarea>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="pop-btns">
                        <a href="#" class="btn-blue left saveRulePop" onclick="saveRule()">保存</a>
                        <a href="#" class="btn-white left closeRulePop" onclick="closeRulePop()">返回</a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>