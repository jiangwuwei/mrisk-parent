<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/27
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="s_one" style="width: 100%">
    <select name="conditions[${condIndex}].attr" class="s_select_w250_m40_fl cond_attr"  style='width:200px;margin-right: 7px; height: 34px; padding: 0 4px'>
        <!-- 场景属性 -->
        <c:forEach items="${sceneConfigList}" var="sceneConfig">
            <option value="${sceneConfig.englishName}"
                    <c:if test="${attr eq sceneConfig.englishName}">selected="selected"</c:if>>
                [系统]<span style="color:#0000FF;">[${sceneConfig.dbTypeName}]</span> ${sceneConfig.chineseName}</option>
        </c:forEach>
    </select>
    <select name="conditions[${condIndex}].oper" class="s_select_w100_fl" style='width:60px;margin-right: 7px;margin-left: 0px;height: 34px;  padding: 0 4px'>
        <option value=">" <c:if test="${oper eq '>'}">selected="selected"</c:if>> 大于</option>
        <option value=">=" <c:if test="${oper eq '>='}">selected="selected"</c:if>>大于等于</option>
        <option value="=" <c:if test="${oper eq '='}">selected="selected"</c:if>> 等于</option>
        <option value="!=" <c:if test="${oper eq '!='}">selected="selected"</c:if>> 不等于</option>
        <option value="equal" <c:if test="${oper eq 'equal'}">selected="selected"</c:if>> 等于(字符串)</option>
        <option value="<" <c:if test="${oper eq '<'}">selected="selected"</c:if>> 小于</option>
        <option value="<=" <c:if test="${oper eq '<='}">selected="selected"</c:if>> 小于等于</option>
        <option value="isEmpty" <c:if test="${oper eq 'isEmpty'}">selected="selected"</c:if>> 为空</option>
        <option value="contains" <c:if test="${oper eq 'contains'}">selected="selected"</c:if>> 包含</option>
        <option value="notContains" <c:if test="${oper eq 'notContains'}">selected="selected"</c:if>> 不包含</option>
    </select>

    <select name="conditions[${condIndex}].value" class="s_select_w250_fl" style='width:200px;margin: 0%; height: 34px; padding: 0 4px'
            onkeydown="Select.del(this,event,'isValueInput${ruleIndex}_${condIndex}')"
            onkeypress="Select.write(this,event,'isValueInput${ruleIndex}_${condIndex}')"
            onchange="Select.onchange(this,event,'isValueInput${ruleIndex}_${condIndex}')">
        <option value="${isValueInput? value:''}">${isValueInput? value:''}</option>
        <c:forEach items="${sceneConfigList}" var="sceneConfig">
            <option value="${sceneConfig.englishName}"
                    <c:if test="${(not isValueInput)  && (value eq sceneConfig.englishName)}">selected="selected"</c:if>>
                [系统] <span style="color:#0000FF;">[${sceneConfig.dbTypeName}]</span> ${sceneConfig.chineseName}</option>
        </c:forEach>
    </select>
    <input type="hidden" name="conditions[${condIndex}].isValueInput" id="isValueInput${ruleIndex}_${condIndex}"
           value="${isValueInput}">
    <label  title='删除' class='iconfont icon-delete del_rule_cond iconfont confirm_del' style='width:8%;    margin-left: 13px;;font-size: 22px' onclick='del_rule_cond(this)'>&#xe738;</label>
    <%--<label title='删除' class="iconfont icon-delete del_rule_cond" style="width:8%;"--%>
           <%--onclick="del_rule_cond(this)">&#xe738;</label>--%>
</div>
