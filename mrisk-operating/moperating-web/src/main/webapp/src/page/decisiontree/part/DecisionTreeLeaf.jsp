<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="dot-juece" style="display: ${currNode.nodeType eq 3 ? 'block;' :'none;'}" id="leafNodeId" >
    <table cellpadding='0' cellspacing='0' class='table-inside'>
        <tr>
            <td>处理结果</td>
            <td>
                <select name="decisionCode" class="input-select">
                    <option value="1" <c:if test="${currNode.decisionCode eq 1}">selected="selected"</c:if>>通过
                    </option>
                    <option value="2" <c:if test="${currNode.decisionCode eq 2}">selected="selected"</c:if>>人工审核
                    </option>
                    <option value="3" <c:if test="${currNode.decisionCode eq 3}">selected="selected"</c:if>>拒绝
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>指令</td>
            <td>
                <select name="actionCode" class="input-select">
                    <option value="" <c:if
                            test="${empty currNode.actionCode}">selected="selected"</c:if>>空</option>
                    <c:forEach items="${actionCodeList}" var="actionCode">
                        <option value="${actionCode.id}" <c:if
                                test="${currNode.actionCode eq actionCode.id}">selected="selected"</c:if>>${actionCode.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>原因</td>
            <td>
                <select name="reason" class="input-select">
                    <c:forEach items="${refuseCodeList}" var="refuseCode">
                        <option value="${refuseCode.id}" <c:if
                                test="${currNode.reason eq refuseCode.id}">selected="selected"</c:if>>${refuseCode.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td valign='top'>描述</td>
            <td>
                <textarea name="description" id="tdDescription" cols="50" rows="10" class='text-area'>${currNode.description}</textarea>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div class="pop-btns">
                    <a href="javascript:void(0)" class="btn-blue left " onclick="saveLeafNode(this)">保存</a>
                    <a href="javascript:void(0)" class="btn-white left " onclick="closeSilde()">返回</a>
                    <a href="javascript:void(0)" class="btn-blue left orange confirm_btn " onclick="delLeafNode()">删除</a>
                </div>
            </td>
        </tr>
    </table>
</div>