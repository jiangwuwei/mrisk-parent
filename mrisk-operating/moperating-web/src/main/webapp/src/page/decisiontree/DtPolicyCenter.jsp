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
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/util/popUtil.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/component/echarts2/echarts-all.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/model/decisiontree/decisiontreeChart.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/page/dtPolicyCenter.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript">

        $(function () {

            $("#close_policy_btn").on("click", function () {
                window.location = '<c:url value="/dtPolicies/list.do?sceneNo=all"/>';
            });

            $("#save_policy_btn").on("click", function () {
                var formId = '#' + $(this).closest("form").attr("id");
                if (!$(formId + " input[name='name']").val()) {
                    showTempErrorPop("策略名称不能为空！")
                    return;
                }
                submitAjaxForm2(formId, "<c:url value='/dtPolicy/insertOrUpdate.do'/>", function (data) {
                    window.location = "<c:url value='/dtPolicy/toEdit.do?sceneNo='/>" + data.sceneNo + '&policyId=' + data.policyId;
                });
            });

        })

        function  showMockRules2(id) {
            $("#mockRuleDiv").load("<c:url value='/dtQuotaTemplateController/findQuotaTemplate.do'/>", {"id":1}, function () {
                $("#mockRuleDiv").show();
            })
        }

        function showMockRules(policyId) {
            $("#mockRuleDiv").load("<c:url value='/dtPolicies/validadteTree.do'/>", {"policyId":policyId}, function () {
                $("#mockRuleDiv").show();
            })
        }

        function  closeWin() {
            $("#mockRuleDiv").hide();
        }

        var addRouteCondition = function(currNodeIndex){
            currNodeIndex = currNodeIndex + 1;
            var htmlStr = "<div id='forCondition'></div>";
            htmlStr+="<div class='item-shuxing mar20'";
            htmlStr+="  nodeIndex='"+currNodeIndex+"'>";
            htmlStr+="     <div class='item-title-shuxing clearfix'>";
            htmlStr+="         <p class='list-icons'>";
            htmlStr+="             <i class='iconfont' title='编辑' onclick='editRoute(this)'>&#xe6f5;</i>";
            htmlStr+="             <i class='iconfont' title='删除' onclick='delRoute(this)'>&#xe738;</i>";
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
            htmlStr+="                      id='score_"+currNodeIndex+"'>";
            htmlStr+="                    </div>";
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
            htmlStr+="                </select>";
            htmlStr+="                <div class='input-text' style='width:70px;'>";
            htmlStr+="                    <input type='text' class='input' id='value_1_"+currNodeIndex+"'>";
            htmlStr+="                </div>";
            htmlStr+="            </div>";
            htmlStr+="        </div>";
            htmlStr+="        <div class='pop-btns'>";
            htmlStr+="            <a href='javascript:void(0)' class='btn-blue left' onclick='saveBranchNode(this)'>保存</a>";
            htmlStr+="           <a href='javascript:void(0)' class='btn-white left' onclick='editRoute(this)'>取消</a>";
            htmlStr+="        </div>";
            htmlStr+="    </div>";
            htmlStr+="</div>";
            $("#for_condition")[0].outerHTML = htmlStr;
        }
    </script>
</head>
<body>
    <input type="hidden" id="updateStatusUrl" value="<c:url value='/rule/updateStatus.do'/>">
    <input type="hidden" id="policy_id" value="${policy.id}">
    <input type="hidden" id="basePath" value="${basePath}">
    <input type="hidden" id="sceneNo" value="${policy.sceneNo}">
    <div class="content">
        <ul class='celue-title clearfix'>
            <li class="${editTab eq 0 ?'selected':''}" onclick="changeTab(this,0)">基础信息</li>
            <li class="${editTab eq 1 ?'selected':''}" onclick="changeTab(this,1)">规则配置</li>
            <li class="${editTab eq 2 ?'selected':''}" onclick="changeTab(this,2)">节点信息</li>
        </ul>
        <div class="cnt-jichu" style='display:${editTab eq 0 ?'block':'none'};' id="editTab0">
            <%@include file="part/DtPolicyDetail.jsp"%>
        </div>
        <!--基础信息 end-->
        <div class="cnt-peizhi" style='background-color:white;display:${editTab eq 1 ?'block':'none'};' id="editTab1">

            <div class="box-shu" style="overflow: auto">
                <div class="box-shu" id="decisionTree" style="width:1400px; height:1000px" isLoadedTree="0">

                </div>
            </div>
        </div>
        <!--基础信息 end-->
        <div class="cnt-peizhi" style='display:${editTab eq 2 ?'block':'none'};' id="editTab2">
            <div class="box-shu" style="padding:0px 10px" id="nodeList" isLoadedTree="0">
            </div>
        </div>
    </div>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <c:set var="errorMsg" value="节点保存失败"></c:set>
    <%@include file="/src/page/common/OnErrorPop.jsp"%>
    <form id="nodeForm">
    <div class="slide" style='display:none;width:560px;' id="treeDetail">
    </div>
    </form>
    <div class="pop-wrapper" style="display:none;"  id="mockRuleDiv"></div>
</body>
</html>