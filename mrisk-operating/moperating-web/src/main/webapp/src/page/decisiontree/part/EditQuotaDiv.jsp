<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<form method="post" id="quotaForm">
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close" onclick="closeWin()" >关闭</div>
            <h2>指标保存</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr style="display: none">
                    <td colspan="2">
                        <input type="text" id="id" name="id" class="input" value="${quota.id}">
                    </td>
                </tr>
                <tr>
                    <td>指标参数名</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" disabled id="paramName" name="paramName" value="${quota.paramName}" class="input"></div>
                    </td>
                </tr>
                <tr>
                    <td>中文名称</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" id="chineseName" name="chineseName" value="${quota.chineseName}" class="input"></div>
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td>
                        <div class="input-text" style='width:300px;height:70px;'>
                            <textarea name="description" class="input" cols="4">${quota.description}</textarea>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="pop-btns">
                            <div  class="btn-blue left save_btn"  style="margin-right: 5%" onclick="saveQuota()">保存</div>
                            <div  class="btn-white left close_btn" style="margin-left: 5%" onclick="closeWin()">关闭</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>