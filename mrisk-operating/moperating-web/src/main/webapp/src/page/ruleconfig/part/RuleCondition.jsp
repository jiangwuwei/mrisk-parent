<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!--
condIndex
attr
oper
value
isValueInput
-->
<ul class='ul-tiaojian clearfix mar20'>
    <li>
        <select class="input-select" style='width:200px;' name="conditions[${condIndex}].attr" onchange="change_cond_ext(this,'_${condIndex}')">
            <c:forEach items="${quotaList}" var="quota">
                <option value="${quota.quotaNo}" attrType="${quota.quotaDataType}"
                        <c:if test="${attr eq quota.quotaNo}">selected="selected"</c:if>>
                    <span style="color:#0000FF;">[${quota.quotaTypeName}]</span>${quota.name}</option>
            </c:forEach>
            <!-- 扩展属性 -->
            <c:forEach items="${extendAttributeList}" var="extendAttribute">
                <option value="${extendAttribute.paramName}" attrType=""
                        <c:if test="${attr eq extendAttribute.paramName}">selected="selected"</c:if>>
                    [扩展]<span style="color:#0000FF;">[${extendAttribute.dataTypeName}]</span> ${extendAttribute.chineseName}</option>
            </c:forEach>
            <!-- 场景属性 -->
            <c:forEach items="${sceneConfigList}" var="sceneConfig">
                <option value="${sceneConfig.englishName}" attrType=""
                        <c:if test="${attr eq sceneConfig.englishName}">selected="selected"</c:if>>
                    [系统]<span style="color:#0000FF;">[${sceneConfig.dbTypeName}]</span> ${sceneConfig.chineseName}</option>
            </c:forEach>
        </select>
    </li>
    <li>
        <select class="input-select" style='width:60px;'  name="conditions[${condIndex}].oper">
            <option value=">" <c:if test="${oper eq '>'}">selected="selected"</c:if>> 大于</option>
            <option value=">=" <c:if test="${oper eq '>='}">selected="selected"</c:if>>大于等于</option>
            <option value="==" <c:if test="${oper eq '=='}">selected="selected"</c:if>> 等于</option>
            <option value="!=" <c:if test="${oper eq '!='}">selected="selected"</c:if>> 不等于</option>
            <option value="equal" <c:if test="${oper eq 'equal'}">selected="selected"</c:if>> 等于(字符串)</option>
            <option value="<" <c:if test="${oper eq '<'}">selected="selected"</c:if>> 小于</option>
            <option value="<=" <c:if test="${oper eq '<='}">selected="selected"</c:if>> 小于等于</option>
            <option value="isEmpty" <c:if test="${oper eq 'isEmpty'}">selected="selected"</c:if>> 为空</option>
            <option value="contains" <c:if test="${oper eq 'contains'}">selected="selected"</c:if>> 包含</option>
            <option value="notContains" <c:if test="${oper eq 'notContains'}">selected="selected"</c:if>> 不包含</option>
        </select>
    </li>
    <li>
        <select class="input-select" style='width:200px;' name="conditions[${condIndex}].value"
                onkeydown="Select.del(this,event,'isValueInput_${condIndex}')"
                onkeypress="Select.write(this,event,'isValueInput_${condIndex}')"
                onchange="Select.onchange(this,event,'isValueInput_${condIndex}')">
            <option value="${isValueInput? value:''}">${isValueInput? value:''}</option>
            <c:forEach items="${sceneConfigList}" var="sceneConfig">
                <option value="${sceneConfig.englishName}"
                        <c:if test="${(not isValueInput)  && (value eq sceneConfig.englishName)}">selected="selected"</c:if>>
                    [系统][${sceneConfig.dbTypeName}]${sceneConfig.chineseName}</option>
            </c:forEach>
        </select>
        <input type="hidden" name="conditions[${condIndex}].isValueInput" id="isValueInput_${condIndex}"
               value="${isValueInput}">
        <input type="hidden" name="conditions[${condIndex}].attrType" id="attrType_${condIndex}"
               value="${attrType}">

        <select name="conditions[${condIndex}].extent" class="input-select cond_extent"
                style="display:${not empty extent ?'block':'none'};width:130px;">
            <option value="" <c:if test="${empty extent}">selected="selected"</c:if>>不包含当前金额</option>
            <!-- 场景金额属性 -->
            <c:forEach items="${sceneMoneyAttrList}" var="sceneMoneyAttr">
                <option value="${sceneMoneyAttr.value}"
                        <c:if test="${extent eq sceneMoneyAttr.value}">selected="selected"</c:if>>
                    [系统] ${sceneMoneyAttr.key}</option>
            </c:forEach>
        </select>
        <i class="iconfont" title='删除' onclick="del_rule_cond(this)">&#xe738;</i>
    </li>
</ul>

<c:remove var="condIndex"></c:remove>
<c:remove var="attr"></c:remove>
<c:remove var="oper"></c:remove>
<c:remove var="value"></c:remove>
<c:remove var="isValueInput"></c:remove>
<c:remove var="extent"></c:remove>