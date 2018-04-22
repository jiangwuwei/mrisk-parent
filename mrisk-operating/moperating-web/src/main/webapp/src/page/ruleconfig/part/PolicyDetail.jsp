<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form method="post" id="policyForm">
<table cellpadding='0' cellspacing='0' class='table-celue'>
    <tr>
        <td>策略集名称</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" name="name" class="input" value="${policy.name}"></div>
        </td>
    </tr>
    <tr>
        <td>场景标识</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" name="sceneNo" class="input" value='${policy.sceneNo}' readonly></div>
        </td>
    </tr>
    <tr>
        <td>策略模式</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" class="input" value='${policy.chPatternName}'  readonly></div>
            <input name="policyPattern" type="hidden" value="${policy.policyPattern}"  >
        </td>
    </tr>
    <tr>
        <td>风险阈值</td>
        <td>
            <input type="hidden" class="range-slider" value="${policy.weightGradePair.key},${empty policy.weightGradePair.value ? '100' : policy.weightGradePair.value}" id="minMax"/>
            <input name="min" type="hidden" value="${policy.weightGradePair.key}"  id="min">
            <input name="max" type="hidden" value="${policy.weightGradePair.value}"  id="max">
        </td>
    </tr>
    <tr>
        <td>执行优先级</td>
        <td>
            <div class="input-text" style='width:300px;'><input type="text" name="weightValue" class="input" value="${policy.weightValue}"></div>
        </td>
    </tr>
    <tr>
        <td>策略描述</td>
        <td>
            <textarea cols="50" rows="10" class='text-area' name="description">${policy.description}</textarea>
        </td>
    </tr>
    <tr>
        <td></td>
        <td>
            <div class="pop-btns">
                <input name="policyNo" type="hidden" value="${policy.policyNo}">
                <input name="id" type="hidden" value="${policy.id}">
                <input name="status" type="hidden" value="${policy.status}">
                <a href="#" class="btn-blue left" id="save_policy_btn">保存</a>
                <a href="#" class="btn-white left" id="close_policy_btn">返回</a>
            </div>
        </td>
    </tr>
</table>
</form>