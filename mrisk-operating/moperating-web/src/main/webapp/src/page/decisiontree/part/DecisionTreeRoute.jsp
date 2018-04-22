<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="routeMode" value="${dbNode.routeMode}"/>
<c:set var="routes" value="${routeMode.routes}"/>
<div class="item-shuxing mar20" id="dbNode_${nodeIndex.index}" branchNodeId="${dbNode.id}" nodeType="${dbNode.nodeType}"
     nodeNo="${dbNode.nodeNo}" parentId="${dbNode.parentId}" nodeIndex="${nodeIndex.index}">
    <div class="item-title-shuxing clearfix">
        <p class='list-icons'>
            <i class="iconfont" title='编辑' onclick="editRoute(this)">&#xe6f5;</i>
            <i class="iconfont" title='删除' onclick="delRoute(this)">&#xe738;</i>
        </p>
        <ul>
            <li>条件名称</li>
            <li>
                <div class="input-text" style='width:160px;'>
                    <input type="text" class="input" value="${dbNode.routeName}" id="routeName_${nodeIndex.index}"></div>
            </li>
            <li>权重</li>
            <li>
                <div class="input-text" style='width:80px;'>
                    <input type="text" class="input" value="${dbNode.score}" id="score_${nodeIndex.index}"></div>
            </li>
        </ul>
    </div>
    <div class="item-cnt-shuxing" style="display:none;" >
        <div class="item-sx">
            <div class="kong"></div>
            <select class="input-select sx-front" style='width:60px;' id="isJoin_${nodeIndex.index}">
                <option value="false" <c:if test="${empty routeMode.isJoin || !routeMode.isJoin}">selected="selected"</c:if>>空</option>
                <option value="true" <c:if test="${routeMode.isJoin}">selected="selected"</c:if>>与</option>
            </select>
            <div class="sx-top">
                <select class="input-select" style='width:90px;' id="operation_0_${nodeIndex.index}">
                    <option value=">" <c:if test="${routes[0].operation eq '>'}">selected="selected"</c:if>> ></option>
                    <option value=">=" <c:if test="${routes[0].operation eq '>='}">selected="selected"</c:if>> >=</option>
                    <option value="==" <c:if test="${routes[0].operation eq '=='}">selected="selected"</c:if>> ==</option>
                    <option value="<=" <c:if test="${routes[0].operation eq '<='}">selected="selected"</c:if>> <=</option>
                    <option value="<" <c:if test="${routes[0].operation eq '<'}">selected="selected"</c:if>> <</option>
                </select>
                <div class="input-text" style='width:70px;'>
                    <input type="text" class="input" value='${routes[0].value}' id="value_0_${nodeIndex.index}">
                </div>
            </div>
            <div class="sx-bottom">
                <select class="input-select" style='width:90px;' id="operation_1_${nodeIndex.index}">
                    <option value=">" <c:if test="${routes[1].operation eq '>'}">selected="selected"</c:if>> ></option>
                    <option value=">=" <c:if test="${routes[1].operation eq '>='}">selected="selected"</c:if>> >=</option>
                    <option value="==" <c:if test="${routes[1].operation eq '=='}">selected="selected"</c:if>> ==</option>
                    <option value="<=" <c:if test="${routes[1].operation eq '<='}">selected="selected"</c:if>> <=</option>
                    <option value="<" <c:if test="${routes[1].operation eq '<'}">selected="selected"</c:if>> <</option>
                </select>
                <div class="input-text" style='width:70px;'>
                    <input type="text" class="input" value='${routes[1].value}' id="value_1_${nodeIndex.index}">
                </div>
            </div>
        </div>
        <div class="pop-btns">
            <a href="javascript:void(0)" class="btn-blue left" onclick="saveBranchNode(this)">保存</a>
            <a href="javascript:void(0)" class="btn-white left" onclick="editRoute(this)">取消</a>
        </div>
    </div>
</div>
<c:remove var="routeMode"/>
<c:remove var="routes"/>