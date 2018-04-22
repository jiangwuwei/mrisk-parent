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
            <div class="input-text" style='width:300px;'>
                <input type="text" name="sceneNo" class="input" value='${policy.sceneNo}' readonly style="background-color: lightgrey"></div>
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