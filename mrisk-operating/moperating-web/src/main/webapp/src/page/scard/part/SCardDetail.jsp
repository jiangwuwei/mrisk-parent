<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form method="post" id="policyForm">
<table cellpadding='0' cellspacing='0' class='table-celue'>
    <tr>
        <td>评分卡模型名称</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" name="name" class="input" value="${scard.name}"></div>
        </td>
    </tr>
    <tr>
        <td>场景标识</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" name="sceneNo" class="input" value='${scard.sceneNo}' readonly></div>
        </td>
    </tr>
    <tr style="display:<c:if test="${not empty scard.id }">none</c:if>">
        <td>是否带权重</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="checkbox" name="weightFlag" value="1" <c:if test="${(scard.weightFlag == 1)}">checked</c:if>>&nbsp;&nbsp;指标是否携带权重</div>
        </td>
    </tr>
    <tr style="display:<c:if test="${ empty scard.id }">none</c:if>">
        <td>是否带权重</td>
        <td>
            指标<span style="color:red">【<c:if test="${(scard.weightFlag == 1)}">携带</c:if><c:if test="${(scard.weightFlag == 0)}">不携带</c:if>】</span>权重
        </td>
    </tr>
    <tr>
        <td>执行优先级</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" name="weightValue" class="input" value="${scard.weightValue}"></div>
        </td>
    </tr>
    <tr>
        <td>评分卡模型描述</td>
        <td>
            <textarea cols="50" rows="10" class='text-area' name="description">${scard.description}</textarea>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <div class="pop-btns">
                <input name="scardNo" type="hidden" value="${scard.scardNo}">
                <input name="percentageFlag" type="hidden" value="${scard.percentageFlag}">
                <input name="id" type="hidden" value="${scard.id}">
                <input name="status" type="hidden" value="${scard.status}">
                <a href="#" class="btn-blue left" id="save_policy_btn">保存</a>
                <a href="#" class="btn-white left" id="close_policy_btn">返回</a>
            </div>
        </td>
    </tr>
</table>
</form>