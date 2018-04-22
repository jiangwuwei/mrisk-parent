<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <title>配置规则管理_风控策略管理_添加策略</title>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/jrange/jquery.range.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value='/src/js/app/page/scardCenter.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/util/popUtil.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/component/jrange/jquery.range-min.js'/>"></script>
    <script type="text/javascript">

        function addRuleCondition(thisObject){
            var condIndex = $(thisObject).attr('condIndex');
            var isvalueInputId = 'isValueInput_' + condIndex;
            var attrType = 'attrType' + "_" + condIndex;
            $(thisObject).attr('condIndex', parseInt(condIndex) + 1);
            var htmlStr = "<div id='for_condition'></div><ul class='ul-tiaojian clearfix mar20'><li>";
            htmlStr += "<select name='conditions[" + condIndex + "].attr' class='input-select' style='width:200px;'" ;
            htmlStr += "onchange='change_cond_ext(this,\""+"_"+condIndex+"\")'>";
            <c:forEach items="${quotaList}" var="item">
            htmlStr += "	<option value='${item.quotaNo}' attrType='${item.quotaDataType}'><span style='color:#0000FF;'>[${item.quotaTypeName}]</span> ${item.name}</option>";
            </c:forEach>
            <c:forEach items="${extendAttributeList}" var="extendAttribute">
            htmlStr += "   <option value='${extendAttribute.paramName}' attrType=''>[扩展][${extendAttribute.dataTypeName}]${extendAttribute.chineseName}</option>";
            </c:forEach>
            <c:forEach items="${sceneConfigList}" var="sceneConfig">
            htmlStr += "   <option value='${sceneConfig.englishName}' attrType=''>[系统][${sceneConfig.dbTypeName}]${sceneConfig.chineseName}</option>";
            </c:forEach>
            htmlStr += "</select>";
            htmlStr += "<select name='conditions[" + condIndex + "].oper' class='input-select' style='width:60px;margin-left:5px;margin-right:5px;'>";
            htmlStr += "	<option value='>' > 大于</option>";
            htmlStr += "	<option value='>=' > 大于等于</option>";
            htmlStr += "	<option value='==' > 等于</option>";
            htmlStr += "	<option value='!=' > 不等于</option>";
            htmlStr += "	<option value='equal' > 等于(字符串)</option>";
            htmlStr += "	<option value='<' > 小于</option>";
            htmlStr += "	<option value='<=' > 小于等于</option>";
            htmlStr += "	<option value='isEmpty' > 为空</option>";
            htmlStr += "	<option value='contains' > 包含</option>";
            htmlStr += "	<option value='notContains' > 不包含</option>";
            htmlStr += "</select>";
            htmlStr += "<select name='conditions[" + condIndex + "].value' class='input-select' style='width:200px;'";
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
            htmlStr += "<input type='hidden' name='conditions[" + condIndex + "].attrType'";
            htmlStr += " id='" + attrType + "' value=${empty quotaList?'':quotaList[0].quotaDataType}>";
            htmlStr += "<select name='conditions[" + condIndex + "].extent' class='input-select cond_extent' style='display:none;width:130px;margin-left:5px;'>";
            htmlStr += "    <option value='' <c:if test='${empty extent}'>selected='selected'</c:if>>无</option>";
            htmlStr += "    <c:forEach items='${sceneMoneyAttrList}' var='sceneMoneyAttr'>";
            htmlStr += "    <option value='${sceneMoneyAttr.value}'  <c:if test='${extent eq sceneMoneyAttr.value}'>selected='selected'</c:if>>";
            htmlStr += "[系统] ${sceneMoneyAttr.key}</option>";
            htmlStr += "</c:forEach>";
            htmlStr += "</select>";
            htmlStr += " <i class='iconfont' title='删除' onclick='del_rule_cond(this)'>&#xe738;</i>";
            htmlStr += "</div>";
            htmlStr += "<div class='clear'>";
            $(thisObject).closest('td').find('#for_condition')[0].outerHTML = htmlStr;
        }

        $(function () {

            $('.range-slider').jRange({
                from: 0,
                to: 1000,
                step: 1,
                scale: [0,200,400,600,800,1000],
                format: '%s',
                width: 300,
                showLabels: true,
                isRange : true
            });

            $("#search_btn").on("click", function () {
                window.location = '<c:url value="/scard/toEdit.do?sceneNo=${scard.sceneNo}&scardId="/>' + $("#policy_id").val() + '&ruleName=' + $("#ruleName").val() + '&editTab=1';
            });

            $("#close_policy_btn").on("click", function () {
                window.location = '<c:url value="/scardPolicies/list.do?sceneNo=all"/>';
            });

            $("#close_policy_btn1").on("click", function () {
                window.location = '<c:url value="/scardPolicies/list.do?sceneNo=all"/>';
            });

            $("#save_policy_btn").on("click", function () {
                var formId = '#' + $(this).closest("form").attr("id");
                if (!$(formId + " input[name='name']").val()) {
                    showTempErrorPop("评分卡模型名称不能为空!");
                    return;
                }
                submitAjaxForm2(formId, "<c:url value='/scard/insertOrUpdate.do'/>", function (data) {
                    window.location = "<c:url value='/scard/toEdit.do?sceneNo='/>" + data.sceneNo + '&scardId=' + data.policyId;
                });
            });

            var confirm_update_status = new Confirm({
                'wrapper': $("#confirm_update_status"),
                'trigger': {'parent': $(".list-celue"), 'selector': '.to_update_status'},
                confirmHandel: function ($theBtn) {
                    var toStatus = $theBtn.attr('toStatus');
                    var options = {
                        id: $theBtn.closest("p").attr("ruleId"),
                        updateStatusUrl: $("#updateStatusUrl").val(),
                        toStatus: toStatus
                    }
                    updateStatus(options, function (data) {
                        if(data.res.code < 0){
                            $("#onErrorPop").show();
                        }else{
                            $theBtn.siblings("span").removeClass("selected");
                            $theBtn.addClass("selected");
//                            $("#onSuccessPop").show();
                        }
                    });
                }
            });
            var confirm_del_rule = new Confirm({
                'wrapper': $("#confirm_del_rule"),
                'trigger': {'parent': $(".list-celue"), 'selector': '#delRule'},
                confirmHandel: function ($theBtn) {
                    var toStatus = $theBtn.attr('toStatus');
                    var options = {
                        id: $theBtn.closest("p").attr("ruleId"),
                        updateStatusUrl: $("#updateStatusUrl").val(),
                        toStatus: toStatus
                    }
                    updateStatus(options, function (data) {
                        if(data.res.code < 0){
                            $("#onErrorPop").show();
                        }else{
                            $("#tempSuccessMsg").text("删除成功！");
                            $("#tempSuccessPop").show();
                            $theBtn.closest(".list-celue").remove();
                        }
                    });
                }
            });
            confirm_update_status.init();
            confirm_del_rule.init();

        })
    </script>
</head>
<body>
    <input type="hidden" id="updateStatusUrl" value="<c:url value='/rule/updateStatus.do'/>">
    <input type="hidden" id="policy_id" value="${scard.id}">
    <input type="hidden" id="basePath" value="${basePath}">
    <input type="hidden" id="sceneNo" value="${scard.sceneNo}">
    <div class="content">
        <ul class='celue-title clearfix'>
            <li class="${editTab eq 0 ?'selected':''}" onclick="changeTab(this,0)">基础信息</li>
            <li class="${editTab eq 1 ?'selected':''}" onclick="changeTab(this,1)">规则配置</li>
            <li class="${editTab eq 2 ?'selected':''}" onclick="changeTab(this,2)">评分结果配置</li>
        </ul>
        <div class="cnt-jichu" style='display:${editTab eq 0 ?'block':'none'};' id="editTab0">
            <%@include file="part/SCardDetail.jsp"%>
        </div>
        <!--基础信息 end-->
        <div class="cnt-peizhi" style='display:${editTab eq 1 ?'block':'none'};' id="editTab1">
            <%@include file="part/SCardParamDetail.jsp"%>
        </div>
        <div class="cnt-jichu" style='display:${editTab eq 1 ?'block':'none'};' id="editTab2">
            <%@include file="part/SCardResultRuleDetail.jsp"%>
        </div>
    </div>
    <c:set var="successMsg" value="规则修改成功"></c:set>
    <%@include file="/src/page/common/OnSuccessPop.jsp"%>
    <c:set var="errorMsg" value="规则修改失败"></c:set>
    <%@include file="/src/page/common/OnErrorPop.jsp"%>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <%@include file="/src/page/common/ConfirmPop.jsp"%>
    <form method="post" id="ruleForm">
    <div class="pop-wrapper" style="display:none;" id="ruleDetail">

    </div></form>

    <div class="pop-wrapper" style='display:none;' id="confirm_del_rule">
        <div class="pop-black"></div>
        <div class="pop-tips">
            <h2>您确定要执行该操作吗？</h2>
            <div class="pop-btns">
                <a href="#" class="btn-blue left orange confirm_btn">确定</a>
                <a href="#" class="btn-white left cancel_btn">取消</a>
            </div>
        </div>
    </div>
</body>
</html>