<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/24
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form method="post" id="formId">
<div class="pop-black tablewrapper_sub"></div>
<div class="pop" style='width:700px;' id="pop">
    <div class="pop-title">
        <div class="pop-close add" onclick="closePop()">关闭</div>
        <h2>${empty quota.name?"新增指标":"指标详情"}</h2>
    </div>
    <div class="pop-cnt" style="padding-top:8px">
        <table cellpadding='0' cellspacing='0' class='table-guize'>
            <tr>
                <td width='12%'>指标名称</td>
                <td width='38%'>
                    <div class="input-text" style='width:200px;'><input type="text" name="name" class="input" value="${quota.name}"></div>
                </td>
                <td width='12%'>指标类型</td>
                <td width='38%'>
                    <select class="input-select " name="quotaDataType" id="quotaDataType" style='width:200px;'>
                        <c:forEach items="${QuotaDataType}" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq quota.quotaDataType}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>选择指标源</td>
                <td>
                    <select class="input-select" style='width:200px;' name ="sourceType"id="sourceType">
                        <c:forEach items="${SourceType}" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq quota.sourceType}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>数据库源</td>
                <td>
                    <select class="input-select" style='width:200px;' name="accessSource">
                        <c:forEach items="${AccessSource}" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq quota.accessSource}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>指标描述</td>
                <td colspan='3'>
                    <div class="input-text" style='width:520px;'><input type="text" name="description" class="input" value="${quota.description}"></div>
                </td>
            </tr>
            <tr>
                <td>指标定义</td>
                <td colspan="3">
                    <ul class="ul-radio clearfix left radioQuotaTemplateId">
                        <li><input type="radio" name="quotaTemplateType" id="sqlo" class="radio" onclick="showRadio(1)" <c:if test="${empty quota.quotaTemplateId}">checked="checked"</c:if> value="1"/><label for="sqlo">人工编写</label></li>
                        <li><input type="radio" name="quotaTemplateType" id="templateo" class="radio" onclick="showRadio(2)" value="2" <c:if test="${not empty quota.quotaTemplateId}">checked="checked"</c:if>/><label for="templateo">使用模板</label></li>
                    </ul>
                    <ul class='ul-shijian clearfix'>
                        <li>
                            <select class="input-select quotaTemplateId" onclick="showQuotaTemplate()" id="quotaTemplateId" style='width:100px;'>
                                <c:forEach items="${templateList}" var="item">
                                    <option value="${item.id}" <c:if test="${item.id eq quota.quotaTemplateId}">selected="selected"</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr class="template" style="${not empty quota.quotaTemplateId ? '' : 'display: none'}">
                <td>时间片</td>
                <td colspan='3'>
                    <ul class='ul-tiaojian clearfix'>
                        <li>
                            <select name="timeShardType" class="input-select" style='width:60px;'>
                                <option value="1" <c:if test="${quota.quotaStatisticsTemplate.timeShardType eq 1}">selected="selected"</c:if>> 近</option>
                                <option value="2" <c:if test="${quota.quotaStatisticsTemplate.timeShardType eq 2}">selected="selected"</c:if>> 当</option>
                            </select>
                        </li>
                        <li>
                            <input type="text" class="input-select subinpt_w10p timeShardValue" name="timeShardValue" style="width: 60px;display: ${quotaStatisticsTemplate.timeShardType ne 2?'block':'none'};"
                                   value="${empty quota.quotaStatisticsTemplate.timeShardValue?'0':quota.quotaStatisticsTemplate.timeShardValue}">
                        </li>
                        <li>
                            <select class="input-select" name="timeShardUnit" style='width:60px;'>
                                <option value="month" <c:if test="${quota.quotaStatisticsTemplate.timeShardUnit eq 'month'}">selected="selected"</c:if>> 月 </option>
                                <option value="day" <c:if test="${quota.quotaStatisticsTemplate.timeShardUnit eq 'day'}">selected="selected"</c:if>> 日 </option>
                                <option value="hour" <c:if test="${quota.quotaStatisticsTemplate.timeShardUnit eq 'hour'}">selected="selected"</c:if>> 时 </option>
                                <option value="minute" <c:if test="${quota.quotaStatisticsTemplate.timeShardUnit eq 'minute'}">selected="selected"</c:if>> 分
                                </option>
                            </select>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr class="template" style="${not empty quota.quotaTemplateId ? '' : 'display: none'}">
                <td>主属性</td>
                <td>
                    <select class="input-select" name="primaryAttr" style='width:200px;'>
                        <c:forEach items="${sceneConfigList}" var="sceneConfig">
                            <option value="${sceneConfig.englishName}" <c:if test="${quota.quotaStatisticsTemplate.primaryAttr eq sceneConfig.englishName}">selected="selected"</c:if>>[系统] ${sceneConfig.chineseName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>从属性</td>
                <td>
                    <select class="input-select" name="secondAttr" style='width:200px;' onchange="Select.onchange(this,event, false)">
                        <option value="" <c:if test="${empty quota.quotaStatisticsTemplate.secondAttr}">selected="selected"</c:if>></option>
                        <c:forEach items="${sceneConfigList}" var="sceneConfig">
                            <option value="${sceneConfig.englishName}" <c:if test="${quota.quotaStatisticsTemplate.secondAttr eq sceneConfig.englishName}">selected="selected"</c:if>>[系统] ${sceneConfig.chineseName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr class="type computeType template" id="computeType" style="${not empty quota.quotaTemplateId ? '' : 'display: none'}">
                <td >计算类型</td>
                <td colspan='3'>
                    <select class="input-select" name="computeType" style='width:200px;'>
                        <option value="1" <c:if test="${quota.quotaStatisticsTemplate.computeType eq 1}">selected="selected"</c:if>> 计算出现次数</option>
                        <option value="2" <c:if test="${quota.quotaStatisticsTemplate.computeType eq 2}">selected="selected"</c:if>> 计算关联次数</option>
                        <option value="3" <c:if test="${quota.quotaStatisticsTemplate.computeType eq 3}">selected="selected"</c:if>> 计算去重个数</option>
                    </select>
                </td>
            </tr>
            <tr class="field computeField template" id="computeField" style="display: ${ quota.quotaTemplateId eq 2?'':'none'};">
                <td>计算字段</td>
                <td colspan='3'>
                    <select name="computeField"  class="input-select s_select_w30p_fl" style='width:200px;'>
                        <c:forEach items="${sceneMoneyAttrList}" var="moneyAttr">
                            <option value="${moneyAttr.value}" <c:if test="${quotaStatisticsTemplate.computeField eq moneyAttr.value}">selected="selected"</c:if>>
                                [系统] ${moneyAttr.key}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <input type="hidden" name="isAnd" value=""/>
            <input type="hidden" name="function" id="function" value="${quota.quotaStatisticsTemplate.function}"/>
            <input type="hidden" name="offset" id="offset" value="${quota.quotaStatisticsTemplate.offset}"/>
            <input type="hidden" name="limit" id="limit" value="${quota.quotaStatisticsTemplate.limit}"/>
            <input type="hidden" name="order" id="order" value="${quota.quotaStatisticsTemplate.order}"/>
            <input type="hidden" name="orderField" id="orderField" value="${quota.quotaStatisticsTemplate.orderField}"/>
            <input type="hidden" name="quotaTemplateId" value="${quota.quotaTemplateId}"/>
            <tr class="template" style="${not empty quota.quotaTemplateId ? '' : 'display: none'}">
                <td valign='top' style='line-height:34px;'>条件设置</td>
                <td colspan='3' >
                    <ul class="ul-radio clearfix left condition">
                        <%--<c:set var="quotaStatisticsTemplate" value="${quota.quotaStatisticsTemplate}"/>--%>
                        <li><input type="radio" name="condition" value="true" id="allCon" checked="checked" <c:if test="${quota.quotaStatisticsTemplate.isAnd}">checked="checked"</c:if>
                        /><label for="allCon">满足所有条件</label></li>
                        <li style="width:140px;"><input type="radio" name="condition" value="false" id="anyCon" <c:if test="${not quota.quotaStatisticsTemplate.isAnd}">checked="checked"</c:if>/><label for="anyCon">满足以下任意条件</label></li>
                    </ul>
                    <ul class='ul-btns left'>
                        <li><div  id ="addCondition" class='btn-blue add_condition' onclick="showAddCondition()"  quotaIndex="${formId}" condIndex="${fn:length(quota.quotaStatisticsTemplate.conditions)}">添加条件</div></li>
                        <li><div  class='btn-white check_quota_content' onclick="showQuotaContent()">查看规则</div></li>
                    </ul>
                    <div class="clear"></div>
                    <div id="for_condition">

                    </div>
                    <ul class='ul-tiaojian clearfix mar20 for_condition s_one' id="conditions" data-size="${quota.quotaStatisticsTemplate.conditions.size()}" style="margin-top: 10px;margin-left: 5px">
                        <c:forEach items="${quota.quotaStatisticsTemplate.conditions}" var="condition" varStatus="varStatus3">
                            <c:set var="condIndex" value="${varStatus3.index}"/>
                            <c:set var="attr" value="${condition.attr}"/>
                            <c:set var="oper" value="${condition.oper}"/>
                            <c:set var="value" value="${condition.value}"/>
                            <c:set var="isValueInput" value="${condition.isValueInput}"/>
                            <c:set var="margineleft" value="15%"/>
                            <%@include file="AddCondition.jsp" %>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
            <tr class="quotaContent" id="quotaContent" style="${empty quota.quotaTemplateId ? '' : 'display: none'}">
                <td valign='top'>指标内容</td>
                <td colspan='3'>
                    <textarea name="quotaContent" id="quotaContents"  cols="50" rows="3" class='text-area' style='width:530px'>${quota.quotaContent}</textarea>
                </td>
            </tr>
            <input type="hidden" name="sceneNo" value="${empty quota.sceneNo?sceneNo:quota.sceneNo}">
            <input type="hidden" name="status" value="${empty quota.status?'1':quota.status}">
            <input type="hidden" name="quotaNo" value="${quota.quotaNo}">
            <input type="hidden" name="id" value="${quota.id}">
            <tr>
                <td></td>
                <td valign='top'>
                    <div class="pop-btns">
                        <div  class="btn-blue left ${empty quota.name?'addClass':''}" onclick="save()" style="margin-right: 5%">保存</div>
                        <div class="btn-white left" onclick="closePop()" style="margin-left: 5%">返回</div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
    </form>
<%--
<div class="pop-black"></div>
<div class="pop" style='width:700px;'>
    <div class="pop-title">
        <div class="pop-close quota-detail" onclick="closePop()">关闭</div>
        <h2>指标详情</h2>
    </div>
    <div class="pop-cnt">
        <table cellpadding='0' cellspacing='0' class='table-guize'>
            <tr>
                <td width='12%'>指标名称</td>
                <td width='38%'>
                    <div class="input-text" style='width:200px;'><input type="text" class="input" id="quotaName" value="${quota.name}"></div>
                </td>
                <td width='12%'>指标类型</td>
                <td width='38%'>
                    <select class="input-select" style='width:200px;'>
                        <c:forEach items="${QuotaDataType}" var="item">
                            <option value="${item.id}"
                                    <c:if test="${item.id eq quotaDataType}">selected="selected"</c:if>>${item.name}</option>
                        </c:forEach>
                    </select>
                    &lt;%&ndash;<div class="input-text" style='width:200px;'><input type="text" class="input"></div>                             &ndash;%&gt;
                </td>
            </tr>
            <tr>
                <td>选择指标源</td>
                <td>
                    <select class="input-select" style='width:200px;'>
                        <option>登录场景</option>
                        <option>拒绝</option>
                    </select>
                </td>
                <td>数据库源</td>
                <td>
                    <select class="input-select" style='width:200px;'>
                        <option>登录场景</option>
                        <option>拒绝</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td valign='top'>指标描述</td>
                <td colspan='3'>
                    <textarea name=""  cols="50" rows="5" class='text-area' style='width:530px'></textarea>
                </td>
            </tr>
            <tr>
                <td>指标定义</td>
                <td colspan="3">
                    <ul class="ul-radio clearfix left">
                        &lt;%&ndash;<li><input type="radio"/><label for=" ">使用SQL</label></li>&ndash;%&gt;
                        &lt;%&ndash;<li><input type="radio"/><label for=" ">使用模板</label></li>&ndash;%&gt;
                    </ul>
                    <ul class='ul-shijian clearfix'>
                        <li>
                            <select class="input-select" style='width:100px;'>
                                <option>频度统计模板</option>
                                <option>--</option>
                            </select>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>时间片</td>
                <td colspan='3'>
                    <ul class='ul-tiaojian clearfix'>
                        <li>
                            <select class="input-select" style='width:60px;'>
                                <option>近</option>
                                <option>--</option>
                            </select>
                        </li>
                        <li>
                            <select class="input-select" style='width:60px;'>
                                <option>1</option>
                                <option>--</option>
                            </select>
                        </li>
                        <li>
                            <select class="input-select" style='width:60px;'>
                                <option>日</option>
                                <option>--</option>
                            </select>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td>主属性</td>
                <td>
                    <select class="input-select" style='width:200px;'>
                        <option>[系统]设备指纹</option>
                        <option>拒绝</option>
                    </select>
                </td>
                <td>从属性</td>
                <td>
                    <select class="input-select" style='width:200px;'>
                        <option>[系统]乐视账号</option>
                        <option>拒绝</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>计算类型</td>
                <td colspan='3'>
                    <select class="input-select" style='width:200px;'>
                        <option>计算关联次数</option>
                        <option>拒绝</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td valign='top' style='line-height:34px;'>条件设置</td>
                <td colspan='3'>
                    <ul class="ul-radio clearfix left">
                        &lt;%&ndash;<li><input type="radio"/><label for="">满足所有条件</label></li>&ndash;%&gt;
                        &lt;%&ndash;<li><input type="radio"/><label for="">满足以下任意条件</label></li>&ndash;%&gt;
                    </ul>
                    <ul class='ul-btns left'>
                        <li><a href="" class='btn-blue'>添加条件</a></li>
                        <li><a href="" class='btn-white'>查看规则</a></li>
                    </ul>
                    <div class="clear"></div>
                    <ul class='ul-tiaojian clearfix mar20'>
                        <li>
                            <select class="input-select" style='width:200px;'>
                                <option>[数字]1天内累计成功登陆的次数</option>
                                <option>--</option>
                            </select>
                        </li>
                        <li>
                            <select class="input-select" style='width:60px;'>
                                <option>与</option>
                                <option>--</option>
                            </select>
                        </li>
                        <li>
                            <select class="input-select" style='width:200px;'>
                                <option>[数字]1天内累计成功登陆的次数</option>
                                <option>--</option>
                            </select>
                        </li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="pop-btns">
                        <div  class="btn-blue left " style="margin-right: 5%">保存</div>
                        <div class="btn-white left" onclick="closePop()" style="margin-left: 5%">返回</div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
--%>
