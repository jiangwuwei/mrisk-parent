<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<form method="post" id="quotaTemplateForm">
    <div class="pop" style="width:650px;">
        <div class="pop-title">
            <div class="pop-close" onclick="closeWin()" >关闭</div>
            <h2>指标参数保存</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside' border="0px">
                <tr style="display:none">
                    <td colspan="5">
                        <input type="text" id="sceneNo" name="sceneNo" class="input" value="${sceneNo}">
                    </td>
                </tr>
                <c:forEach items="${list}" var="item" varStatus="status">
                    <tr>
                        <td style="width:3%;text-align:center">
                            <input type="checkbox" name="quotaTemplateId" value="${item.id}"/>
                        </td>
                        <td style="text-align:left">
                            ${item.chineseName}
                        </td>
                        <td style="text-align:left">
                            ${item.paramName}
                        </td>
                        <td style="text-align:left;width:15%">
                            <c:if test="${item.sourceType == 1}">上下文(内部)</c:if>
                            <c:if test="${item.sourceType == 2}">外部服务</c:if>
                        </td>
                        <td style="text-align:left;width:15%;">
                            <c:if test="${item.dataType == 1}">数字类型</c:if>
                            <c:if test="${item.dataType == 2}">字符串</c:if>
                            <c:if test="${item.dataType == 3}">boolean数字</c:if>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="5">
                        <div class="pop-btns" style="text-align: center">
                            <div  class="btn-blue left save_btn"  style="margin-right: 5%" onclick="saveQuotaForPolicies()">保存</div>
                            <div  class="btn-white left close_btn" style="margin-left: 5%" onclick="closeWin()">关闭</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>