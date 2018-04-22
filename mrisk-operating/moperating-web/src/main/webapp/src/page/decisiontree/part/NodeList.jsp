<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="part-title">
    <p class="part-btn" id="add-menu" style="background-color:#5c94ef;color:#fff" onclick="showMockRules('${param.policyId}')">验证决策树</p>
</div>
<table cellpadding='0' cellspacing='0' class='table mar20'>
    <thead>
    <tr>
        <td>节点类型</td>
        <td>节点编号</td>
        <td>指标中文名</td>
        <td>指标参数名</td>
        <td>节点路径</td>
        <td>路径表达式</td>
        <td>决策结果</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${list}" varStatus="status">
        <tr id="${item.id}">
            <td>
                <c:if test="${item.nodeType == 1}"><span style="color:red;">根节点</span></c:if>
                <c:if test="${item.nodeType == 2}">枝干节点</c:if>
                <c:if test="${item.nodeType == 3}"><span style="color:blue;">叶子节点</span></c:if>
            </td>
            <td>${item.nodeNo}</td>
            <td>${item.chineseName}</td>
            <td>${item.paramName}</td>
            <td>${item.routeName}</td>
            <td>${item.routeExpression}</td>
            <td>
                <c:if test="${item.decisionCode == 1}"><span style="color:red;">通过</span></c:if>
                <c:if test="${item.decisionCode == 2}">人工审核</c:if>
                <c:if test="${item.decisionCode == 3}"><span style="color:blue;">拒绝</span></c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>