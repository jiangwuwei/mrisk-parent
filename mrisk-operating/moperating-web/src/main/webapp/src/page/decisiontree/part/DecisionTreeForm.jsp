<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--表单提交内容--%>
<%--需要根据选中的指标属性来获取--%>
<input type="hidden" name="chineseName" value="${currNode.chineseName}" id="tdChineseName">
<input type="hidden" name="paramName" value="${currNode.paramName}" id="tdParamName">
<input type="hidden" name="quotaId" value="${currNode.quotaId}" id="tdQuotaId">
<input type="hidden" value="${currNode.id}" id="tdCurrNodeId">
<input type="hidden" name="branchParentId" value="${currNode.parentId}">
<%--提交form的时候根据根据页面上值填充policyId和sceneNo--%>
<input type="hidden" name="policyId" id="tdPolicyId">
<input type="hidden" name="sceneNo" id="tdSceneNo">
<%--提交form的时候根据要保存的node设定下面的属性,默认为当前节点的属性，以方便保存叶节点时少设置属性--%>
<input type="hidden" name="id" id="tdId" value="${currNode.id}">
<input type="hidden" name="parentId" id="tdParentId" value="${currNode.parentId}">
<input type="hidden" name="nodeNo" id="tdNodeNo" value="${currNode.nodeNo}">
<input type="hidden" name="routeName" id="tdRouteName">
<input type="hidden" name="score" id="tdScore">
<input type="hidden" name="nodeType" id="tdNodeType">
<%--更新类型，2：保存分叉节点， 3保存叶节点--%>
<input type="hidden" name="updateType" id="tdUpdateType">
<%--routeMode属性--%>
<input type="hidden" name="routeMode.nodeId" id="tdNodeId">
<%--防止提交决策节点数据时出错，给予默认值false--%>
<input type="hidden" name="routeMode.isJoin" id="tdIsJoin" value="false">
<%--route的其他属性都是属性节点的属性,由后台封装--%>
<input type="hidden" name="routeMode.routes[0].operation" id="tdRoutes0_Operation">
<input type="hidden" name="routeMode.routes[0].value" id="tdRoutes0_Value">
<input type="hidden" name="routeMode.routes[0].paramDataType" id="tdRoutes0_paramDataType">
<input type="hidden" name="routeMode.routes[1].operation" id="tdRoutes1_operation">
<input type="hidden" name="routeMode.routes[1].value" id="tdRoutes1_value">
<input type="hidden" name="routeMode.routes[1].paramDataType" id="tdRoutes1_paramDataType">
<%--routeMode属性 end--%>
<%--其他表单内容见决策节点部分--%>