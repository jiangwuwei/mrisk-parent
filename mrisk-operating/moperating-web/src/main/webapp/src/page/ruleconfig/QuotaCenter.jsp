<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>配置规则管理_风控指标管理</title>
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <style type="text/css">
        .input-select{margin: 0%}
    </style>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/app/page/quotaCenter.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        function delQuota() {
            var dataNo = $("#confirm_del").attr("data-quotaNo");
            delById(dataNo,'${basePath}/quota/delById.do');
            $("#confirm_del").removeAttr("data-quotaNo");
        }
        $(function () {
            $(".popClose").on("click", function () {
                $(this).closest(".pop-wrapper").hide();
            });
            $(".confirm_del").on("click",function (event) {
                $("#confirm_del").attr("data-quotaNo",$(this).attr("quotaId"));
                $("#confirm_del").show();
                return false;
            });
            $(".label_tab").on("click", function(){
                if ($(this).closest("h2").find('#label_tab').hasClass('up')) {
                    $(this).closest("h2").find('#label_tab').removeClass('up');
                    $(this).closest("h2").find('#label_tab').addClass('down');
                    $(this).closest("h2").find("#label_tab").html("&#xe69a");
                }else{
                    $(this).closest("h2").find('#label_tab').removeClass('down');
                    $(this).closest("h2").find('#label_tab').addClass('up');
                    $(this).closest("h2").find("#label_tab").html("&#xe612");

                }
                $(this).closest(".list-celue").find(".list-celue-ul").slideToggle();
            });

            $(".search_btn").on("click", function(){
                window.location = '${basePath}/quota/list.do?quotaName='+$("#quota-name").val();
            });
            $(".selScene").on("change", function(){
                window.location = '${basePath}/quota/list.do?sceneNo='+$("#selectSceneNo").val();
            });

            $(".quota-detail").on("click", function () {
                var ul = $(".detail");
                if (ul.css("display") === "none") {
                    ul.attr("display"," ");
                    ul.show();
                } else {
                    ul.attr("display","none");
                    ul.hide();

                }
            });
        })
        function showDetail(quotaId) {
            $(".detail").load($("#basePath").val()+'/quota/getQuota.do', {
                quotaId: quotaId,
                sceneNo:$("#data-sceneNo").attr("data-sceneNo"),
            }, function () {
                $(".detail").show();
            });
        }

        var closePop = function () {
            $(".detail").hide();
        }
        function showQuotaContent() {
            var formId = "formId";
            var formIdSelector = '#'+ formId;
            var isUseSql = $(".radioQuotaTemplateId").find("input[name='quotaTemplateType']:checked").val();
            var templateId = null;
            if(isUseSql == 1){
                if(!$("#quotaContents").val()){
                    alert("指标内容不能为空!");
                    return;
                }
            }else{
                templateId = $("#quotaTemplateId option:selected").val();
            }
            $(formIdSelector+" input[name='quotaTemplateId']").val(templateId);
            var isAnd = $(".condition").find("input[name='condition']:checked").val();
            $(formIdSelector+" input[name='isAnd']").val(isAnd);
            submitAjaxForm2(formIdSelector,'${basePath}/quota/checkQuotaContent.do', function (data) {
                $(formIdSelector+" textarea[name='quotaContent']").val(data.quotaContent);
            }, 1);
            $('#quotaContent').show();
        }
        function showRadio(num){
            if (num == 1) { //use sql
                $(".template").hide();
                $("#quotaContent").show();
                return;
            }
            //use template
            var templateId = $(".quotaTemplateId option:selected").val();
            if (templateId == 1) {
                $("#computeType").show();
                $("#computeField").hide();
            } else if (templateId == 2) {
                $("#computeType").hide();
                $("#computeField").show();
            } else if( templateId == 3){
                $("#computeType").hide();
                $("#computeField").hide();
            }
            $(".template").show();
            $("#quotaContent").hide();
        }
        function save() {
            var formId = "formId";
            var formIdSelector = '#'+ formId;
            var isUseSql = $(".radioQuotaTemplateId").find("input[name='quotaTemplateType']:checked").val();
            var templateId = null;
            if(isUseSql == 1){
                if(!$(formIdSelector+" textarea[name='quotaContent']").val()){
                    showTempErrorPop("指标内容不能为空!");
                    return;
                }
            }else{
                templateId = $("#quotaTemplateId option:selected").val();
            }
            $(formIdSelector+" input[name='quotaTemplateId']").val(templateId);
            var isAnd = $(".condition").find("input[name='condition']:checked").val();
            $(formIdSelector+" input[name='isAnd']").val(isAnd);

            if($(this).hasClass('addClass')){
                submitAjaxForm2(formIdSelector,'${basePath}/quota/insertOrUpdate.do', function(){
                    window.location.reload();
                });
            }else{
                submitAjaxForm2(formIdSelector,'${basePath}/quota/insertOrUpdate.do', function (data) {
                    $(formIdSelector+" textarea[name='quotaContent']").val(data.quotaContent);
                });
            }
        }
        function showAddCondition(){
            var condIndex = $("#addCondition").attr('condIndex');
            var size =$("#conditions").attr('data-size');
            var ruleIndex = $("#addCondition").attr('ruleIndex');
            var isvalueInputId = 'isValueInput' + ruleIndex + "_" + condIndex;
            $("#addCondition").attr('condIndex', parseInt(condIndex) + 1);
            var htmlStr = "<div id='for_condition'></div><div class='s_one' style='margin-top: 10px'>";
//                var htmlStr = " <ul class='s_one ul-tiaojian clearfix mar20 for_condition'>";
            htmlStr += "<select name='conditions[" + condIndex + "].attr' class='s_select_w250_m40_fl input-select' style='width:200px;margin-right: 10px;'>";
            <c:forEach items="${sceneConfigList}" var="sceneConfig">
            htmlStr += "   <option value='${sceneConfig.englishName}'>[系统][${sceneConfig.dbTypeName}]${sceneConfig.chineseName}</option>";
            </c:forEach>
            htmlStr += "</select>";
            htmlStr += "<select name='conditions[" + condIndex + "].oper' class='s_select_w100_fl input-select' style='width:60px;margin-right: 10px;margin-left: 0px' >";
            htmlStr += "	<option value='>' > 大于</option>";
            htmlStr += "	<option value='>=' > 大于等于</option>";
            htmlStr += "	<option value='=' > 等于</option>";
            htmlStr += "	<option value='equal' > 等于(字符串)</option>";
            htmlStr += "	<option value='<' > 小于</option>";
            htmlStr += "	<option value='<=' > 小于等于</option>";
            htmlStr += "	<option value='isEmpty' > 为空</option>";
            htmlStr += "	<option value='contains' > 包含</option>";
            htmlStr += "	<option value='notContains' > 不包含</option>";
            htmlStr += "</select>";
            htmlStr += "<select name='conditions[" + condIndex + "].value' class='s_select_w250_fl input-select' style='width:200px;margin: 0%' ";
            htmlStr += " onkeydown=\"Select.del(this,event,'" + isvalueInputId + "')\"";
            htmlStr += " onkeypress=\"Select.write(this,event,'" + isvalueInputId + "')\">";
            htmlStr += " onchange=\"Select.onchange(this,event,'" + isvalueInputId + "')\">";
            htmlStr += "	<option value=''></option>";
            <c:forEach items="${sceneConfigList}" var="sceneConfig">
            htmlStr += "   <option value='${sceneConfig.englishName}'>[系统]";
            htmlStr += "[${sceneConfig.dbTypeName}]${sceneConfig.chineseName}</option>";
            </c:forEach>
            htmlStr += "</select>";
            htmlStr += "<input type='hidden' name='conditions[" + condIndex + "].isValueInput'";
            htmlStr += " id='" + isvalueInputId + "' value='false'>";

            htmlStr += "<label  title='删除' class='iconfont icon-delete del_rule_cond iconfont confirm_del' style='width:8%;margin-left: 3%;font-size: 22px' onclick='del_rule_cond(this)'>&#xe738;</label>";
            htmlStr += "</div>";
//                htmlStr += "</ul>";
//                htmlStr += "<div class='clear'>";
            $('#for_condition')[0].outerHTML = htmlStr;
        }
        //根据模板调整可选项
        function  showQuotaTemplate() {
            var templateId = $("#quotaTemplateId option:selected").val();
            var self = $("#showQuotaTemplate");
            $.ajax({
                cache: true,
                type: "POST",
                url: $("#basePath").val()+"/quotaTemplate/getTemplateById.do",
                data: {'id': templateId},
                dataType: 'json',
                error: function (request) {
                    showTempErrorPop("Connection error");
                },
                success: function (data) {
                    var quotaContent = JSON.parse(data.quotaContent);
                    if ($(".radioQuotaTemplateId").find("input[name='quotaTemplateType']:checked").val()==2){
                        if (quotaContent.computeType.show) {
                            $("#computeType").show();
                        }else{
                            $("#computeType").hide();
                        }
                        if (quotaContent.computeField.show) {
                            $("#computeField").show();
                        }else{
                            $("#computeField").hide();
                        }
                    }
                    var templateFunction = (typeof quotaContent.function == 'undefined')?'':quotaContent.function;
                    var limit = (typeof quotaContent.limit == 'undefined')?'':quotaContent.limit;
                    var offset = (typeof quotaContent.offset == 'undefined')?'':quotaContent.offset;
                    var orderField = (typeof quotaContent.orderField == 'undefined')?'':quotaContent.orderField;
                    var order = (typeof quotaContent.order == 'undefined')?'':quotaContent.order;
                    $("#function").val(templateFunction);
                    $("#limit").val(limit);
                    $("offset").val(offset);
                    $("orderField").val(orderField);
                    $("order").val(order);
                    $("#quotaDataType option").each(function () {
                        if($(this).val() == data.quotaDataType){
                            $(this)[0].selected = true;
                        }
                    });
                    $("#sourceType option").each(function () {
                        if($(this).val() == data.sourceType){
                            $(this)[0].selected = true;
                        }
                    });
                    $("#accessSource option").each(function () {
                        if($(this).val() == data.accessSource){
                            $(this)[0].selected = true;
                        }
                    });

                }
            });
        };
    </script>
</head>
<body>
<input type="hidden" value="${basePath}" id="basePath">
    <div class="content"> 
        <div class="search clearfix">
            <ul class='search-ul left clearfix'>
                <li class="left">
                    <label>场景名称</label>
                    <select class="input-select selScene" id="selectSceneNo">
                        <c:forEach items="${allPolicies}" var="item">
                            <option value="${item.sceneNo}"
                                    <c:if test="${item.sceneNo eq sceneNo}">selected="selected"</c:if>>${item.name}(${item.sceneNo})</option>
                        </c:forEach>
                    </select>
                </li>
                <li class="left">
                    <label>指标名称</label>
                    <div class="input-text"><input type="text" class="input" value="${quotaName}" id="quota-name"></div>
                </li>
            </ul>
            <div class="btn left search_btn">查询</div>
        </div>
        <!--search end-->
        <!--新建场景按钮开始-->
        <div class="list mar20">
            <c:forEach var="pair" items="${list}" varStatus="varStatus1">
            <div class="list-celue">
                <div class="list-celue-title clearfix  bg-gray">
                    <p class='list-icons'>
                        <i class="iconfont add" title='添加' id="data-sceneNo" data-sceneNo="${pair.key.sceneNo}" onclick="showDetail(-1)">&#xe6df;</i>
                    </p>
                    <h2>
                        <i class="iconfont down label_tab" id="label_tab">&#xe69a;</i><span class="label_tab">${pair.key.name}</span>
                    </h2>
                </div>
                <ul class='list-celue-ul'>
                    <c:forEach var="quota" items="${pair.value}">
                        <c:set var="quotaTitle" value="指标详情"/>
                        <li class='clearfix quota-detail' data = "${quota.name}" onclick="showDetail(${quota.id})">
                        <p class='list-icons'>
                           <i class="iconfont confirm_del" quotaId="${quota.id}" title='删除'>&#xe738;</i>
                        </p>
                        <h2>
                            <span>${quota.name} (${quota.quotaNo})</span>
                        </h2>
                    </li>

                    </c:forEach>
                </ul>
            </div>
            </c:forEach>
        </div>
    </div>
    <!--content end-->
    <div class="pop-wrapper" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop">
            <div class="pop-title">
                <div class="pop-close" onclick="closePop()">关闭</div>
                <h2>新建场景</h2>
            </div>
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <td>策略集名称</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input type="text" class="input"></div> 
                        </td>
                    </tr>
                    <tr>
                        <td>场景标识</td>
                        <td>
                            <select class="input-select">
                                <option>登录场景</option>
                                <option>拒绝</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>场景描述</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input type="text" class="input"></div> 
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="pop-btns">
                                <a href="#" class="btn-blue left">保存</a>
                                <a href="#" class="btn-white left">返回</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <!--新建策略集end-->
    <div class="pop-wrapper" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop-tip">
            <i class="iconfont ok">&#xe72d;</i>
            <p>指标修改成功</p>
        </div>
    </div>
    <!--指标修改成功 end-->
    <div class="pop-wrapper" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop-tip">
            <i class="iconfont error">&#xe6f2;</i>
            <p>指标修改失败</p>
        </div>
    </div>
    <!--指标修改失败 end-->
    <div class="pop-wrapper" style='display:none;' id="confirm_del">
        <div class="pop-black"></div>
        <div class="pop-tips">
            <h2>您确定要执行该操作吗？</h2>
             <div class="pop-btns">
                <div  class="btn-blue left orange" onclick="delQuota()" style="margin-right: 10px">确定</div>
                <div  class="btn-white left popClose" style="margin-left: 10px">取消</div>
            </div>
        </div>
    </div>
    <!--确认取消弹框 end-->
    <div class="pop-wrapper detail" style='display:none'>

    </div>
    <!--指标详情end-->
   <%-- <div class="pop-wrapper add-quota tablewrapper_sub" style="display: none">
        <div class="pop-black"></div>
        <div class="pop" style='width:700px;'>
            <div class="pop-title">
                <div class="pop-close add">关闭</div>
                <h2>新增指标</h2>
            </div>
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-guize'>
                    <tr>
                        <td width='12%'>指标名称</td>
                        <td width='38%'>
                            <div class="input-text" style='width:200px;'><input type="text" class="input" value="新增指标"></div>
                        </td>
                        <td width='12%'>指标类型</td>
                        <td width='38%'>
                            <select class="input-select " name="quotaDataType" style='width:200px;'>
                                <c:forEach items="${QuotaDataType}" var="item">
                                    <option value="${item.id}"
                                            <c:if test="${item.id eq quotaDataType}">selected="selected"</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>选择指标源</td>
                        <td>
                            <select class="input-select" style='width:200px;'>
                                <c:forEach items="${SourceType}" var="item">
                                    <option value="${item.id}"
                                            <c:if test="${item.id eq sourceType}">selected="selected"</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>数据库源</td>
                        <td>
                            <select class="input-select" style='width:200px;'>
                                <c:forEach items="${AccessSource}" var="item">
                                    <option value="${item.id}"
                                            <c:if test="${item.id eq accessSource}">selected="selected"</c:if>>${item.name}</option>
                                </c:forEach>
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
                                <li><input type="radio" name="fix" id="sql" class="radio"  checked="checked" value="1"/><label for="sql">人工编写</label></li>
                                <li><input type="radio" name="fix" id="template" class="radio" value="2"/><label for="template">使用模板</label></li>
                            </ul>
                             <ul class='ul-shijian clearfix'>
                                 <li>
                                    <select class="input-select quotaTemplateId" style='width:100px;'>
                                        <c:set var="quotaTemplateId" value="${quota.quotaTemplateId}"/>
                                        <c:forEach items="${templateList}" var="item">
                                            <option value="${item.id}"
                                                    <c:if test="${item.id eq quotaTemplateId}">selected="selected"</c:if>>${item.name}</option>
                                        </c:forEach>
                                    </select> 
                                 </li>
                             </ul>
                        </td>
                    </tr>
                    <tr class="template" style="display: none">
                        <td>时间片</td>
                        <td colspan='3'>
                            <ul class='ul-tiaojian clearfix'>
                                <li>
                                    <select class="input-select" style='width:60px;'>
                                        <option value="1" <c:if test="${quotaStatisticsTemplate.timeShardType eq 1}">selected="selected"</c:if>> 近</option>
                                        <option value="2" <c:if test="${quotaStatisticsTemplate.timeShardType eq 2}">selected="selected"</c:if>> 当</option>
                                    </select>
                                </li>
                                <li>
                                    <input type="text" class="input-select subinpt_w10p timeShardValue" name="timeShardValue" style="width: 60px;display: ${quotaStatisticsTemplate.timeShardType ne 2?'block':'none'};"
                                           value="${empty quotaStatisticsTemplate.timeShardValue?'0':quotaStatisticsTemplate.timeShardValue}">
                                </li>
                                <li>
                                    <select class="input-select" style='width:60px;'>
                                        <option value="month" <c:if test="${quotaStatisticsTemplate.timeShardUnit eq 'month'}">selected="selected"</c:if>> 月
                                        </option>
                                        <option value="day" <c:if test="${quotaStatisticsTemplate.timeShardUnit eq 'day'}">selected="selected"</c:if>> 日
                                        </option>
                                        <option value="hour" <c:if test="${quotaStatisticsTemplate.timeShardUnit eq 'hour'}">selected="selected"</c:if>> 时
                                        </option>
                                        <option value="minute" <c:if test="${quotaStatisticsTemplate.timeShardUnit eq 'minute'}">selected="selected"</c:if>> 分
                                        </option>
                                    </select>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <tr class="template" style="display: none">
                        <td>主属性</td>
                        <td>
                            <select class="input-select" style='width:200px;'>
                                <c:forEach items="${sceneConfigList}" var="sceneConfig">
                                    <option value="${sceneConfig.englishName}" <c:if test="${quotaStatisticsTemplate.primaryAttr eq sceneConfig.englishName}">selected="selected"</c:if>>[系统] ${sceneConfig.chineseName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>从属性</td>
                        <td>
                            <select class="input-select" style='width:200px;'
                                onchange="Select.onchange(this,event, false)">
                                <option value="" <c:if test="${empty quotaStatisticsTemplate.secondAttr}">selected="selected"</c:if>></option>
                                <c:forEach items="${sceneConfigList}" var="sceneConfig">
                                    <option value="${sceneConfig.englishName}" <c:if test="${quotaStatisticsTemplate.secondAttr eq sceneConfig.englishName}">selected="selected"</c:if>>[系统] ${sceneConfig.chineseName}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="type computeType" style="display: none">
                    <td >计算类型</td>
                    <td colspan='3'>
                        <select class="input-select" style='width:200px;'>
                            <option value="1" <c:if test="${quotaStatisticsTemplate.computeType eq 1}">selected="selected"</c:if>> 计算出现次数</option>
                            <option value="2" <c:if test="${quotaStatisticsTemplate.computeType eq 2}">selected="selected"</c:if>> 计算关联次数</option>
                            <option value="3" <c:if test="${quotaStatisticsTemplate.computeType eq 3}">selected="selected"</c:if>> 计算去重个数</option>
                        </select>
                    </td>
                </tr>
                    <tr class="field computeField" style="display: none">
                        <td>计算字段</td>
                        <td colspan='3'>
                            <select name="computeField" class="input-select s_select_w30p_fl" style='width:200px;'>
                                <c:forEach items="${sceneMoneyAttrList}" var="moneyAttr">
                                    <option value="${moneyAttr.value}"
                                            <c:if test="${quotaStatisticsTemplate.computeField eq moneyAttr.value}">selected="selected"</c:if>>
                                        [系统] ${moneyAttr.key}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="template" style="display: none">
                        <td valign='top' style='line-height:34px;'>条件设置</td>
                        <td colspan='3' >
                            <ul class="ul-radio clearfix left">
                                <li><input type="radio" name="condition" id="all" checked="checked"/><label for="all">满足所有条件</label></li>
                                <li><input type="radio" name="condition" id="anyone"/><label for="anyone">满足以下任意条件</label></li>
                            </ul>
                            <ul class='ul-btns left'>
                                <li><div href="" class='btn-blue add_condition'>添加条件</div></li>
                                <li><div href="" class='btn-white'>查看规则</div></li>
                            </ul>
                            <div class="clear"></div>
                            <ul class='ul-tiaojian clearfix mar20 for_condition'>
                                &lt;%&ndash;<li>
                                    <select class="input-select" style='width:200px;'>
                                        <c:forEach items="${sceneConfigList}" var="sceneConfig">
                                            <option value='${sceneConfig.englishName}'>[系统][${sceneConfig.dbTypeName}]${sceneConfig.chineseName}</option>
                                        </c:forEach>
                                    </select>
                                </li>
                                <li>
                                    <select class="input-select" style='width:60px;'>
                                            <option value='>' > 大于</option>
                                    </select>
                                </li>
                                <li>
                                    <select class="input-select" style='width:200px;'>
                                        <c:forEach items="${sceneConfigList}" var="sceneConfig">
                                             <option value='${sceneConfig.englishName}'>[系统][${sceneConfig.dbTypeName}]${sceneConfig.chineseName}</option>
                                        </c:forEach>
                                    </select>
                                </li>&ndash;%&gt;
                            </ul>
                        </td>
                    </tr>
                    <tr class="quotaContent">
                        <td valign='top'>指标内容</td>
                        <td colspan='3'>
                            <textarea name=""  cols="50" rows="5" class='text-area' style='width:530px'></textarea>
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
    </div>--%>
    <!--新增指标end-->
</body>
</html>