<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="list mar20 tablebox">
    <div class="pop-cnt" style="padding: 0px 20px 20px 20px;">
        <ul class="ul-add-shuxing clearfix">
            <li class="ul-btns left" style="padding-right:10px"><span style="display:inline-block;width:200px"></span></li>
            <li class="ul-btns left" style="padding-right:10px">
                <div class="btn-add btn-add-long to_add_btn" style="margin-top:0px;" onclick="addRuleForScardResult(0,'','','',0)">添加结果配置</div></li>
            <li class="ul-btns left" style="padding-right:10px"><span style="display:inline-block;width:70px"></span></li>
            <li class="ul-btns left" style="padding-right:10px"><a href="javascript:void(0)" class="btn-white left" id="close_policy_btn1">返回</a></li>
        </ul>
        <ul class="list-celue-ul" style="display: block;" id="ruleRouteFrame"></ul>
    </div>
</div>
<script type="text/javascript">
    var decreseRuleValue = 0;
    function addRuleForScardResult(routeId, routeName, finalResult, routeExpression, typeFlag ){
        var incValue = 0;
        if ( typeFlag === 1 ){
            incValue = routeId;
        }else if ( typeFlag === 0 ){
            decreseRuleValue--;
            incValue = decreseRuleValue;
        }
        var htmlStr = '<li class="clearfix" id="scardRule' + incValue + '" style="line-height:40px; padding:0px 0px;border-bottom:0px">';
        htmlStr+='<p class="list-icons">';
        htmlStr+='<i class="iconfont" title="保存路径" onclick="saveScardRuleRoute(\'' + incValue + '\',' + typeFlag+')"></i>';
        htmlStr+='<i class="iconfont" title="删除指标" onclick="delScardRuleRoute(\'' + incValue + '\',' + typeFlag +')"></i>';
        htmlStr+='</p><h2>';
        htmlStr+='<div class="label_tab_3" style="width:90%">';
        htmlStr+='<form method="post" id="scardRuleForm' + incValue +'">';
        htmlStr+='<input type="hidden" name="id" value="' + incValue +'">';
        htmlStr+='<input type="hidden" name="scardId" value="${scard.id}">';
        htmlStr+='<table width="100%"><tr><td style="width:60px;text-align:right;padding-right:5px">路径名称:</td>';
        htmlStr+='<td style="width:120px;text-align:left"><input type="text" name="routeName" value="' + routeName + '"></td>';
        htmlStr+='<td style="width:80px;text-align:right;padding-right:5px">返回结果:</td>';
        htmlStr+='<td style="width:100px;text-align:left"><input type="text" name="finalResult" value="' + finalResult + '" style="width:50px;"></td>';
        htmlStr+='<td style="width:120px;text-align:right;padding-right:5px">评分结果分支路径:</td>';
        htmlStr+='<td style="text-align:left"><input type="text" name="routeExpression" value="'+ routeExpression + '" style="width:200px;"></td></tr>';
        htmlStr+='</table>';
        htmlStr+='</form></div></h2></li>';
        $(htmlStr).appendTo($('#ruleRouteFrame'));
    }

    <c:forEach var="route" items="${resultRuleList}" varStatus="status">
    addRuleForScardResult('${route.id}','${route.routeName}','${route.finalResult}','${route.routeExpression}',1);
    </c:forEach>

    /**
     * idValue  是后端的 id
     * @param routeId  前端的id
     * @param flag
     */
    function delScardRuleRoute(routeId, flag) {
        var idValue = $("#scardRuleForm" + routeId ).find("[name=id]").val();
        if ( idValue < 0  ){
            $("#scardRule" + routeId).remove();
        }else {
            if (confirm("您确认要删除该分支路径吗?")) {
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/scardRule/delScardRule.do'/>",
                    dataType: 'json',
                    data: {"ruleId": idValue },
                    error: function (request) {
                        showTempErrorPop("Connection error!");
                    },
                    success: function (data) {
                        if (data.res.code == 0) {
                            $("#scardRule" + routeId).remove();
                            showTempSuccessPop("删除路径操作成功");
                        } else {
                            showTempErrorPop("保存失败,可能原因是参数名称重复!");
                        }
                    }
                })
            }
        }

    }

    /**
     * 保存参数的路径
     * @param routeId
     * @param flag
     */
    function saveScardRuleRoute(routeId, flag) {
        if (confirm("您确认保存该分支路径吗?")) {
            var jsonData = $("#scardRuleForm" + routeId ).serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/scardRule/saveScardRule.do'/>",
                dataType: 'json',
                data: jsonData,
                error: function (request) {
                    showTempErrorPop("Connection error!");
                },
                success: function (data) {
                    if (data.res.code == 0) {
                        if( data.id > 0 ) {
                            $("#scardRuleForm" + routeId).find("[name=id]").val(data.id);
                        }
                        showTempSuccessPop("保存路径操作成功");
                    } else {
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }
    }

</script>