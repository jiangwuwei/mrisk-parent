<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp" %>
    <%@include file="/src/page/common/TempSuccessPop.jsp" %>
    <title>配置规则管理_风控策略管理_添加策略路由</title>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/util/popUtil.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript">

        var addRouteCondition = function(currNodeIndex){
            currNodeIndex = currNodeIndex + 1;
            var htmlStr = "<div id='for_condition'></div>";
            htmlStr+="<div class='item-shuxing mar20'";
            htmlStr+="  routerIndex='"+currNodeIndex+"'>";
            htmlStr+="     <div class='item-title-shuxing clearfix'>";
            htmlStr+="         <p class='list-icons'>";
            htmlStr+="             <i class='iconfont' title='编辑' onclick='editRouter(this)'>&#xe6f5;</i>";
            htmlStr+="             <i class='iconfont' title='删除' onclick='delRouter(this)'>&#xe738;</i>";
            htmlStr+="         </p>";
            htmlStr+="         <ul>";
            htmlStr+="             <li>条件名称</li>";
            htmlStr+="             <li>";
            htmlStr+="                 <div class='input-text' style='width:200px;'>";
            htmlStr+="                     <input type='text' class='input' ";
            htmlStr+="              id ='routeName_"+currNodeIndex+"'></div>";
            htmlStr+="            </li>";
            htmlStr+="            <li>权重</li>";
            htmlStr+="            <li>";
            htmlStr+="                <div class='input-text' style='width:80px;'>";
            htmlStr+="                    <input type='text' class='input' ";
            htmlStr+="                      id='weightValue_"+currNodeIndex+"'>";
            htmlStr+="                    </div>";
            htmlStr+="            </li>";
            htmlStr+="            <li>策略</li>";
            htmlStr+="            <li>";
            htmlStr+="                  <select class='input-select' style='width:300px;' id='policy_"+currNodeIndex+"'>";
                                        <c:forEach items="${policyList}" var="policy">
            htmlStr+="<option value='${policy.id}'>${policy.name}</option>";
                                        </c:forEach>
            htmlStr+="                      </select>";
            htmlStr+="            </li>";
            htmlStr+="        </ul>";
            htmlStr+="    </div>";
            htmlStr+="    <div class='item-cnt-shuxing' style='display:none;' >";
            htmlStr+="        <div class='item-sx'>";
            htmlStr+="            <div class='kong'></div>";
            htmlStr+="            <select class='input-select sx-front' style='width:60px;' ";
            htmlStr+="                      id='isJoin_"+currNodeIndex+"'>";
            htmlStr+="                <option value='true'>与</option>";
            htmlStr+="                <option value='false' selected='selected'>空</option>";
            htmlStr+="            </select>";
            htmlStr+="            <div class='sx-top'>";
            htmlStr+="                <select class='input-select' style='width:90px;' ";
            htmlStr+="                                        id='operation_0_"+currNodeIndex+"'>";
            htmlStr+="                    <option value='>' > ></option>";
            htmlStr+="                    <option value='>=' > >=</option>";
            htmlStr+="                    <option value='==' > ==</option>";
            htmlStr+="                    <option value='<=' > <=</option>";
            htmlStr+="                    <option value='<' > <</option>";
            htmlStr+="                    <option value='contains' > 包含</option>";
            htmlStr+="                    <option value='notContains' > 不包含</option>";
            htmlStr+="                    <option value='isEmpty' > 为空</option>";
            htmlStr+="                </select>";
            htmlStr+="                <div class='input-text' style='width:70px;'>";
            htmlStr+="                    <input type='text' class='input' value='${routes[0].value}' ";
            htmlStr+="                     id='value_0_"+currNodeIndex+"'>";
            htmlStr+="                </div>";
            htmlStr+="            </div>";
            htmlStr+="            <div class='sx-bottom'>";
            htmlStr+="                <select class='input-select' style='width:90px;' ";
            htmlStr+="                id='operation_1_"+currNodeIndex+"'>";
            htmlStr+="                    <option value='>'> ></option>";
            htmlStr+="                    <option value='>=' > >=</option>";
            htmlStr+="                    <option value='==' > ==</option>";
            htmlStr+="                    <option value='<=' > <=</option>";
            htmlStr+="                    <option value='<' > <</option>";
            htmlStr+="                    <option value='contains' > 包含</option>";
            htmlStr+="                    <option value='notContains' > 不包含</option>";
            htmlStr+="                    <option value='isEmpty' > 为空</option>";
            htmlStr+="                </select>";
            htmlStr+="                <div class='input-text' style='width:70px;'>";
            htmlStr+="                    <input type='text' class='input' id='value_1_"+currNodeIndex+"'>";
            htmlStr+="                </div>";
            htmlStr+="            </div>";
            htmlStr+="        </div>";
            htmlStr+="        <div class='pop-btns'>";
            htmlStr+="            <a href='javascript:void(0)' class='btn-blue left' onclick='saveRouter(this)'>保存</a>";
            htmlStr+="           <a href='javascript:void(0)' class='btn-white left' onclick='editRouter(this)'>取消</a>";
            htmlStr+="        </div>";
            htmlStr+="    </div>";
            htmlStr+="</div>";
            $("#for_condition")[0].outerHTML = htmlStr;
        }
    </script>
</head>
<body>
<input type="hidden" id="updateStatusUrl" value="<c:url value='/rule/updateStatus.do'/>">
<input type="hidden" id="sceneNo" value="${sceneNo}">
<div class="content">
    <div class="dot-shuxing">
        <form id="routerForm">
            <%--表单提交内容--%>
            <input type="hidden" name="id" id="tdId">
            <input type="hidden" name="name" id="tdName">
            <input type="hidden" name="policyId" id="tdPolicyId">
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
        <ul class="ul-add-shuxing clearfix">
            <li>选择路由主属性</li>
            <li>
                <select id="sceneAttribute" class="input-select" style='width:200px;' <c:if test="${not empty selectedAttribute}">disabled</c:if>>
                    <c:forEach items="${sceneVoList}" var="sceneVo">
                        <option value="${sceneVo.englishName}" paramDataType="${sceneVo.dbType}"
                                <c:if test="${selectedAttribute eq sceneVo.englishName}">selected="selected"</c:if>>${sceneVo.chineseName}</option>
                    </c:forEach>
                </select>
            </li>
            <li class='ul-btns left'><a href="javascript:void(0)" class='btn-blue'
                                        onclick="addRouteCondition(${fn:length(currNode.routeMode.routes)})">添加条件</a></li>
            <li class='ul-btns left'><a href="<c:url value="/policies/list.do"/>" class="btn-white left" style="margin-left: 5px;" >返回</a></li>
        </ul>
        <div id="for_condition"> </div>
        <c:forEach items="${routerList}" var="policyRouter" varStatus="routerIndex">
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
                            <div class="input-text" style='width:160px;'>
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
        </c:forEach>
    </div>
</div>
</body>
</html>