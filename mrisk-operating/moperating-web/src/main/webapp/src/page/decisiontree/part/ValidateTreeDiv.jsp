<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="pop-black"></div>
<div class="pop" style="width:650px">
    <div class="pop-title">
        <div class="pop-close" onclick="closeWin()" >关闭</div>
        <h2>模拟生成的规则</h2>
    </div>
    <div class="pop-cnt" style="padding:5px">
        <c:if test="${passFlag == 1}">
            <table cellpadding='0' cellspacing='0' class='table-inside' >
                <thead>
                <tr>
                    <td style="text-align: center">规则名称</td>
                    <td style="text-align: center">规则内容</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${mockRules}" varStatus="status">
                    <tr>
                        <td style="text-align: center" >${item.name}</td>
                        <td style="text-align: center">${item.ruleContent}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${passFlag == 0}">
            <br/>
            ${errorMessage}
            <br/><br/>&nbsp;
        </c:if>
    </div>
</div>