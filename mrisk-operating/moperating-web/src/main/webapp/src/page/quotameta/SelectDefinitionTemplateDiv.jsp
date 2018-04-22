<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<form method="post" id="definitionForm">
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close" onclick="closeWin()" >关闭</div>
            <h2>指标参数保存</h2>
        </div>
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr style="display:none">
                    <td colspan="2">
                        <input type="text" id="id" name="id" class="input" value="${param.id}">
                    </td>
                </tr>
                <c:forEach items="${allList}" var="item" varStatus="status">
                    <td style="width:50%;text-align:left">
                        <input type="checkbox" name="templateId" value="${item.id}"/>${item.value}
                    </td>
                    <c:if test="${ ( status.count%2 == 0 ) && (!status.last) }">
                        </tr><tr>
                    </c:if>
                    <c:if test="${ status.last }">
                        </tr>
                    </c:if>
                </c:forEach>
                <tr>
                    <td colspan="2">
                        <div class="pop-btns" style="text-align: center">
                            <div  class="btn-blue left save_btn"  style="margin-right: 5%" onclick="saveDefinitionTemplate()">保存</div>
                            <div  class="btn-white left close_btn" style="margin-left: 5%" onclick="closeWin()">关闭</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>