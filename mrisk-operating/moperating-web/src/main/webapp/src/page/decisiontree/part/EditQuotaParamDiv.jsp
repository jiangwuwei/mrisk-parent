<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<form method="post" id="quotaParamForm">
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close" onclick="closeWin()" >关闭</div>
            <h2>指标参数保存</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr style="display: none">
                    <td colspan="2">
                        <input type="text" id="id" name="id" class="input" value="${param.quotaId}">
                    </td>
                </tr>
                <tr>
                    <td>中文名称</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" id="chineseName" name="chineseName" value="${para.chineseName}" class="input"></div>
                    </td>
                </tr>
                <tr>
                    <td>参数名</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text"  name="name" id="name" value="${para.name}" class="input"></div>
                    </td>
                </tr>
                <tr>
                    <td>参数类型</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <select name="dataType" class="input" style="width:200px;">
                                <option value="1" <c:if test="${para.dataType == 1}">selected</c:if>>数字类型</option>
                                <option value="2" <c:if test="${para.dataType == 2}">selected</c:if>>字符类型</option>
                                <option value="3" <c:if test="${para.dataType == 3}">selected</c:if>>boolean数字类型</option>
                            </select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>参数缺省值</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" name="defaultValue" value="${para.defaultValue}" class="input"></div>
                    </td>
                </tr>
                <tr>
                    <td>是否可为空</td>
                    <td>
                        <div class="input-text" style='width:300px;line-height:34px;'>
                            <input type="radio" name="mandatory" value="1" <c:if test="${para.mandatory == 1}">checked</c:if>>不为空
                            <input type="radio" name="mandatory" value="0" <c:if test="${para.mandatory == 0}">checked</c:if>> 可为空
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>
                        <div class="input-text" style='width:300px;height:70px;'>
                            <textarea name="description" class="input" cols="4">${para.description}</textarea>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="pop-btns">
                            <div  class="btn-blue left save_btn"  style="margin-right: 5%" onclick="saveQuotaParam()">保存</div>
                            <div  class="btn-white left close_btn" style="margin-left: 5%" onclick="closeWin()">关闭</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>