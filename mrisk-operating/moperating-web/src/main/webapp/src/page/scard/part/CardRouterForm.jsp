<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="routerForm">
<%--表单提交内容--%>
<input type="hidden" name="id" id="tdId">
<input type="hidden" name="name" id="tdName">
<input type="hidden" name="scardId" id="tdScardId">
<input type="hidden" name="sceneNo" id="tdSceneNo">
<input type="hidden" name="description" id="tdDescription">
<input type="hidden" name="weightValue" id="tdWeightValue">
<%--防止提交决策节点数据时出错，给予默认值false--%>
<input type="hidden" name="routeMode.isJoin" id="tdIsJoin" value="false">
<%--route的其他属性都是属性节点的属性,由后台封装--%>
<input type="hidden" name="routeMode.routes[0].operation" id="tdRoutes0_Operation">
<input type="hidden" name="routeMode.routes[0].value" id="tdRoutes0_Value">
<input type="hidden" name="routeMode.routes[0].paramDataType" id="tdRoutes0_paramDataType">
<input type="hidden" name="routeMode.routes[0].paramName" id="tdRoutes0_paramName">
<input type="hidden" name="routeMode.routes[1].operation" id="tdRoutes1_operation">
<input type="hidden" name="routeMode.routes[1].value" id="tdRoutes1_value">
<input type="hidden" name="routeMode.routes[1].paramDataType" id="tdRoutes1_paramDataType">
<input type="hidden" name="routeMode.routes[1].paramName" id="tdRoutes1_paramName">
<%--routeMode属性 end--%>
<%--其他表单内容见决策节点部分--%>
</form>