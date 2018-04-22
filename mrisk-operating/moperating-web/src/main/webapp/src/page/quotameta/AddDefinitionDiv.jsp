<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<form method="post" id="definitionForm">
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close" onclick="closeWin()" >关闭</div>
            <h2>指标保存</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr style="display: none">
                    <td colspan="2">
                        <input type="text" id="id" name="id" class="input" value="${definition.id}">
                    </td>
                </tr>
                <tr>
                    <td>接入厂商</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <select name="sourceId" class="input" style="width:200px;">
                                <c:forEach var="item" items="${sourceList}">
                                    <option value="${item.id}" <c:if test="${definition.sourceId == item.id}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>中文名称</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <input type="text" id="chineseName" name="chineseName" value="${definition.chineseName}" class="input">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>参数名</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <input type="text"  name="paramName" id="paramName" value="${definition.paramName}" class="input">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>参数类型</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <select name="dataType" class="input" style="width:200px;">
                                <option value="1" <c:if test="${definition.dataType == 1}">selected</c:if>>单值类型</option>
                                <option value="2" <c:if test="${definition.dataType == 2}">selected</c:if>>多值类型</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>
                        <div class="input-text" style='width:300px;height:70px;'>
                            <textarea name="description" class="input" cols="4">${definition.description}</textarea>
                        </div>
                    </td>
                </tr>
                <tr style="display:<c:if test="${ empty definition.id}">none</c:if><c:if test="${ not empty definition.id}"></c:if>">
                    <td>请求参数</td>
                    <td>
                        <div class="input-text" style='width:400px;height:120px;'>
                            <textarea name="requestParams" class="input" cols="6" readonly style="background-color:#cccccc">${definition.requestParams}</textarea>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="pop-btns">
                            <div  class="btn-blue left save_btn"  style="margin-right: 5%" onclick="saveParamTemplate()">保存</div>
                            <div  class="btn-white left close_btn" style="margin-left: 5%" onclick="closeWin()">关闭</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>