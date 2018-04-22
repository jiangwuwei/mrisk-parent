<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pop-black"></div>
<div class="pop" style="width:800px;">
    <div class="pop-title" style="">
        <div class="pop-close" onclick="closeWin()" >关闭</div>
        <h2>变量<span style="color:blue;">【${scardParam.chineseName}】</span>参数配置</h2>
    </div>

    <div class="pop-cnt" style="padding: 0px 20px 20px 20px;">
        <form method="post" id="paramForm">
            <ul class="ul-add-shuxing clearfix">
                <li style="padding-right:10px;display:<c:if test="${sCard.weightFlag == 0}">none</c:if>">权重:</li>
                <li style="padding-right:10px;display:<c:if test="${sCard.weightFlag == 0}">none</c:if>">
                    <input type="text" name="weightValue" style="width:40px;" value="${scardParam.weightValue}">
                    <input type="hidden" name="id"  value="${scardParam.id}">
                </li>
                <li style="padding-right:10px">缺省路径分值:</li>
                <li style="padding-right:10px">
                    <input type="text" name="defaultScore" style="width:40px;" value="${scardParam.defaultScore}">
                </li>
                <li class="ul-btns left" style="padding-right:10px"><a href="javascript:void(0)" class="btn-blue" onclick="saveParamConfig()">保存</a></li>
            </ul>
        </form>
        <ul class="ul-add-shuxing clearfix">
            <li class="ul-btns left" style="padding-right:10px"><span style="display:inline-block;width:200px"></span></li>
            <li class="ul-btns left" style="padding-right:10px"><a href="javascript:void(0)" class="btn-blue" onclick="addRouteForParam(0,'','','',0)">添加路径</a></li>
            <li class="ul-btns left" style="padding-right:10px"><span style="display:inline-block;width:70px"></span></li>
            <li class="ul-btns left" style="padding-right:10px"><a href="javascript:void(0)" class="btn-blue left" onclick="closeWin()">关闭</a></li>
        </ul>

        <ul class="list-celue-ul" style="display: block;" id="routeFrame"></ul>
    </div>
</div>
<script type="text/javascript">
    var decreseValue = 0;
    function addRouteForParam(routeId, routeName, routeScore, routeExpression, typeFlag ){
        if ( typeFlag === 1 ){
            incValue = routeId;
        }else if ( typeFlag === 0 ){
            decreseValue--;
            incValue = decreseValue;
        }
        var htmlStr = '<li class="clearfix" id="route' + incValue + '" style="line-height:40px; padding:0px 0px;border-bottom:0px">';
        htmlStr+='<p class="list-icons">';
        htmlStr+='<i class="iconfont" title="保存路径" onclick="saveScardRoute(\'' + incValue + '\',' + typeFlag+')"></i>';
        htmlStr+='<i class="iconfont" title="删除指标" onclick="delScardRoute(\'' + incValue + '\',' + typeFlag +')"></i>';
        htmlStr+='</p><h2>';
        htmlStr+='<div class="label_tab_2" style="width:90%">';
        htmlStr+='<form method="post" id="paramRouteForm' + incValue +'">';
        htmlStr+='<input type="hidden" name="id" value="' + incValue +'">';
        htmlStr+='<input type="hidden" name="paramId" value="${scardParam.id}">';
        htmlStr+='<table width="100%"><tr><td style="width:60px;text-align:right;padding-right:5px">路径名称:</td>';
        htmlStr+='<td style="width:120px;text-align:left"><input type="text" name="routeName" value="' + routeName + '"></td>';
        htmlStr+='<td style="width:40px;text-align:right;padding-right:5px">分值:</td>';
        htmlStr+='<td style="width:100px;text-align:left"><input type="text" name="routeScore" value="' + routeScore + '" style="width:140px;"></td>';
        htmlStr+='<td style="width:60px;text-align:right;padding-right:5px">条件路径:</td>';
        htmlStr+='<td style="text-align:left"><input type="text" name="routeExpression" value="'+ routeExpression + '" style="width:180px;"></td></tr>';
        htmlStr+='</table>';
        htmlStr+='</form></div></h2></li>';
        $(htmlStr).appendTo($('#routeFrame'));
    }

    <c:forEach var="route" items="${scardParam.routeList}" varStatus="status">
        addRouteForParam('${route.id}','${route.routeName}','${route.routeScore}','${route.routeExpression}',1);
    </c:forEach>
</script>