<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="routeMode" value="${policyRouter.routeMode}"/>
<c:set var="routes" value="${routeMode.routes}"/>
<div class="item-shuxing mar20" id="router_${routerIndex.index}" routerIndex="${routerIndex.index}" routerId="${policyRouter.id}">
    <div class="item-title-shuxing clearfix">
        <p class='list-icons'>
            <i class="iconfont" title='编辑' onclick="editRouter(this)">&#xe6f5;</i>
            <i class="iconfont" title='删除' onclick="delRouter(this)">&#xe738;</i>
        </p>
        <ul>
            <li>条件名称</li>
            <li>
                <div class="input-text" style='width:200px;'>
                    <input type="text" class="input" value="${policyRouter.name}" id="routeName_${routerIndex.index}"></div>
            </li>
            <li>权重</li>
            <li>
                <div class="input-text" style='width:80px;'>
                    <input type="text" class="input" value="${policyRouter.weightValue}" id="weightValue_${routerIndex.index}"></div>
            </li>
            <li>策略</li>
            <li>
                <select class="input-select" style='width:300px;' id="policy_${routerIndex.index}">
                    <c:forEach items="${policyList}" var="policy">
                        <option value="${policy.id}" <c:if test="${policyRouter.policyId eq policy.id}">selected="selected"</c:if>>${policy.name}</option>
                    </c:forEach>
                </select>
            </li>
        </ul>
    </div>
    <div class="item-cnt-shuxing" style="display:none;" >
        <div class="item-sx">
            <div class="kong"></div>
            <select class="input-select sx-front" style='width:60px;' id="isJoin_${routerIndex.index}">
                <option value="false" <c:if test="${empty routeMode.isJoin || !routeMode.isJoin}">selected="selected"</c:if>>空</option>
                <option value="true" <c:if test="${routeMode.isJoin}">selected="selected"</c:if>>与</option>
            </select>
            <div class="sx-top">
                <select class="input-select" style='width:90px;' id="operation_0_${routerIndex.index}">
                    <option value=">" <c:if test="${routes[0].operation eq '>'}">selected="selected"</c:if>> ></option>
                    <option value=">=" <c:if test="${routes[0].operation eq '>='}">selected="selected"</c:if>> >=</option>
                    <option value="==" <c:if test="${routes[0].operation eq '=='}">selected="selected"</c:if>> ==</option>
                    <option value="<=" <c:if test="${routes[0].operation eq '<='}">selected="selected"</c:if>> <=</option>
                    <option value="<" <c:if test="${routes[0].operation eq '<'}">selected="selected"</c:if>> <</option>
                    <option value="contains" <c:if test="${routes[0].operation eq 'contains'}">selected="selected"</c:if>> 包含</option>
                    <option value="notContains" <c:if test="${routes[0].operation eq 'notContains'}">selected="selected"</c:if>> 不包含</option>
                    <option value="isEmpty" <c:if test="${routes[0].operation eq 'isEmpty'}">selected="selected"</c:if>> 为空</option>
                </select>
                <div class="input-text" style='width:70px;'>
                    <input type="text" class="input" value='${routes[0].value}' id="value_0_${routerIndex.index}">
                </div>
            </div>
            <div class="sx-bottom">
                <select class="input-select" style='width:90px;' id="operation_1_${routerIndex.index}">
                    <option value=">" <c:if test="${routes[1].operation eq '>'}">selected="selected"</c:if>> ></option>
                    <option value=">=" <c:if test="${routes[1].operation eq '>='}">selected="selected"</c:if>> >=</option>
                    <option value="==" <c:if test="${routes[1].operation eq '=='}">selected="selected"</c:if>> ==</option>
                    <option value="<=" <c:if test="${routes[1].operation eq '<='}">selected="selected"</c:if>> <=</option>
                    <option value="<" <c:if test="${routes[1].operation eq '<'}">selected="selected"</c:if>> <</option>
                    <option value="contains" <c:if test="${routes[1].operation eq 'contains'}">selected="selected"</c:if>> 包含</option>
                    <option value="notContains" <c:if test="${routes[1].operation eq 'notContains'}">selected="selected"</c:if>> 不包含</option>
                    <option value="isEmpty" <c:if test="${routes[1].operation eq 'isEmpty'}">selected="selected"</c:if>> 为空</option>
                </select>
                <div class="input-text" style='width:70px;'>
                    <input type="text" class="input" value='${routes[1].value}' id="value_1_${routerIndex.index}">
                </div>
            </div>
        </div>
        <div class="pop-btns">
            <a href="javascript:void(0)" class="btn-blue left" onclick="saveRouter(this)">保存</a>
            <a href="javascript:void(0)" class="btn-white left" onclick="editRouter(this)">取消</a>
        </div>
    </div>
</div>
<c:remove var="routeMode"/>
<c:remove var="routes"/>