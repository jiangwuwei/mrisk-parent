<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<form method="post" id="selectParamForm">
    <div class="pop" style="width:700px;top:50px">
        <div class="pop-title">
            <div class="pop-close" onclick="closeWin()" >关闭</div>
            <h2>评分卡入参选择</h2>
            <input type="hidden" id="scardId"  name="scardId" class="input" value="${param.scardId}">
        </div>
        <c:forEach var="item" items="${scardParamsList}" varStatus="status">
            <h3 style="padding-left:10px;padding-top:10px;font-weight: bold">${item.typeName}</h3>
            <div class="pop-cnt"  style="padding:5px;">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <c:forEach items="${item.paramList}" var="subItem" varStatus="obj">
                            <td style="width:50px;padding:5px;">
                                <input type="checkbox" name="paramId" <c:if test="${subItem.checked == true}">checked disabled="true"</c:if> value="${subItem.id}" style="width:15px"/>
                            </td>
                            <td style="width:300px;padding:5px;">
                                <label>${subItem.chinese_name}(${subItem.param_name})</label>
                            </td>
                            <c:if test="${obj.count%2 == 0}"></tr><tr></c:if>
                        </c:forEach>
                </table>
            </div>
        </c:forEach>
        <div style="text-align: center">
            <div  class="btn-blue left save_btn"  style="margin-left: 25%" onclick="saveSelectParam()">保存</div>
            <div  class="btn-white left close_btn" style="margin-left: 5%" onclick="closeWin()">关闭</div>
        </div>
        <div class="pop-cnt">
            <div>&nbsp;</div>
        </div>
    </div>
</form>