<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<div class="slide" style='width:560px;'>--%>
<div class="slide-close" onclick="closeSilde()"></div>
<div class="slide-cnt">
    <h2>基础信息配置</h2>
    <ul class="ul-radio clearfix mar20">
        <!--叶子节点不能修改为属性节点-->
        <li><input type="radio" name="nodeTypeRadio" value="2" onclick="changeNode(2)"
                   <c:if test="${currNode.nodeType ne 3}">checked="checked"</c:if>
                   <c:if test="${not empty currNode.id && currNode.nodeType eq 3}">disabled</c:if>/><label>添加属性节点</label></li>
        <!--已有children的节点不能修改为叶子节点-->
        <li><input type="radio" name="nodeTypeRadio" value="3" onclick="changeNode(3)"
                   <c:if test="${currNode.nodeType eq 3}">checked="checked"</c:if>
                   <c:if test="${not empty currNode.id && not empty list}">disabled</c:if>/><label>添加决策节点</label></li>
    </ul>
    <div class="dot-shuxing" id="branchNodeId" style="display: ${currNode.nodeType ne 3 ? 'block;' :'none;'}">
        <%@include file="DecisionTreeForm.jsp"%>
        <ul class="ul-add-shuxing clearfix">
            <li>属性名称</li>
            <li>
                <select id="quotaId" class="input-select" style='width:200px;' >
                    <c:forEach items="${quotaList}" var="quota">
                        <option value="${quota.id}" chineseName="${quota.chineseName}" paramDataType="${quota.dataType}"
                                paramName="${quota.paramName}"
                                <c:if test="${not empty currNode.id && not empty list}">disabled</c:if>
                                <c:if test="${currNode.quotaId eq quota.id}">selected="selected"</c:if>>${quota.chineseName}</option>
                    </c:forEach>
                </select>
            </li>
            <li class='ul-btns left'><a href="javascript:void(0)" class='btn-blue'
                                        onclick="addRouteCondition(${fn:length(currNode.routeMode.routes)})">添加条件</a></li>
        </ul>
        <div id="for_condition">
        </div>
        <c:forEach items="${list}" var="dbNode" varStatus="nodeIndex">
            <%@include file="DecisionTreeRoute.jsp" %>
        </c:forEach>

    </div>
    <!--dot-shuxing属性节点 end-->
    <%@include file="DecisionTreeLeaf.jsp"%>
</div>
<%--</div>--%>