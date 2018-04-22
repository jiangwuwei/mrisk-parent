<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<input name="ruleNo" type="hidden" value="${rule.ruleNo}">
<input name="status" type="hidden" value="${rule.status}">
<input name="decisionCode" type="hidden" value="${rule.decisionCode}">
<input name="sceneNo" type="hidden" value="${rule.sceneNo}">
<input name="policyId" type="hidden" value="${rule.policyId}">
<input name="id" type="hidden" value="${rule.id}">
<tr>
    <td width='20%'>规则名称</td>
    <td width='80%'>
        <div class="input-text" style='width:300px;'>
            <input name="name" value="${rule.name}" type="text" class="input" id="newRuleName"></div>
            <input type="hidden" value="${rule.name}" id="oldRuleName">
    </td>
</tr>
<tr>
    <td>规则权重（0~1000）</td>
    <td>
        <div class="input-text" style='width:300px;'>
            <input type="text" class="input" name="score" value="${rule.score}"></div>
    </td>
</tr>
<tr>
    <td>执行顺序</td>
    <td>
        <div class="input-text" style='width:300px;'><input type="text" class="input" name="weightValue" value="${rule.weightValue}"></div>
    </td>
</tr>
<tr>
    <td>操作指令</td>
    <td>
        <select class="input-select" style='width:300px;' name="actionCode">
            <option value="" <c:if test="${empty rule.actionCode}">selected="selected"</c:if>>无</option>
            <c:forEach items="${DV}" var="acItem">
                <option value="${acItem.id}" <c:if test="${acItem.id eq rule.actionCode}">selected="selected"</c:if>>${acItem.name}</option>
            </c:forEach>
        </select>
    </td>
</tr>
<tr>
    <td>描述</td>
    <td>
        <div class="input-text" style='width:300px;'><input type="text" class="input" name="description" value="${rule.description}"></div>
    </td>
</tr>