<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>名单库管理_名单库场景配置</title>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            var actionConfirmFunc = new Confirm({
                'wrapper': $("#refresh_cache"),
                'trigger': {'parent': $(".list-box"),'selector':'.refresh_cache_action'},
                confirmHandel: function ($theBtn) {
                    var action = $theBtn.attr('action');
                    sendRefreshAction(action);
                }
            });
            actionConfirmFunc.init();
        });

        function sendRefreshAction(action) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/config/refresh/cache.do'/>",
                data: {"action": action},
                dataType: "json",
                timeout: 10000,
                error: function (request) {
                    showTempErrorPop("Connection error" + request );
                },
                success: function (data) {
                    console.log(data);
                    if (data.status  == 0) {
                        showTempErrorPop(data.res.msg);
                    }else{
                        showTempSuccessPop("操作成功!");
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="content">
    <!--search end-->
    <div class="list-box mar20">
        <ul class='list-celue-ul' style="display:block;">
            <li class='clearfix' style="border-bottom: 0px;">
                <h2>
                    <span class="btn-blue mar-auto refresh_cache_action" style="width:160px;float:left" action="antifraud">刷新反欺诈缓存</span>
                    <span style="padding-left:10px;font-size:13px">当对反欺诈相关的策略,路由或者指标做任何相关的改动时,刷新后可以立即生效</span>
                </h2>
            </li>
            <li class='clearfix' style="border-bottom: 0px;">
                <h2>
                    <span class="btn-blue mar-auto refresh_cache_action" style="width:160px;float:left" action="dtree">刷新决策树缓存</span>
                    <span style="padding-left:10px;font-size:13px">当对决策树或者路由做任何相关的改动时,刷新后可以立即生效</span>
                </h2>
            </li>
            <li class='clearfix' style="border-bottom: 0px;">
                <h2>
                    <span class="btn-blue mar-auto refresh_cache_action" style="width:160px;float:left" action="scard">刷新评分卡缓存</span>
                    <span style="padding-left:10px;font-size:13px">当对评分卡,评分卡参数的路由,路由或者评分卡规则做任何相关的改动时,刷新后可以立即生效</span>
                </h2>
            </li>
            <li class='clearfix' style="border-bottom: 0px;">
                <h2>
                    <span class="btn-blue mar-auto refresh_cache_action" style="width:160px;float:left" action="rosterconfig">刷新名单库配置缓存</span>
                    <span style="padding-left:10px;font-size:13px">当黑名单应用到相关的业务场景做任何相关的改动时,刷新后可以立即生效</span>
                </h2>
            </li>
            <li class='clearfix' style="border-bottom: 0px;">
                <h2>
                    <span class="btn-blue mar-auto refresh_cache_action" style="width:160px;float:left" action="jadescene">刷新场景配置缓存</span>
                    <span style="padding-left:10px;font-size:13px">当对业务场景接入做任何相关的改动时,刷新后可以立即生效</span>
                </h2>
            </li>
        </ul>
    </div>
</div>
<!--删除策略end-->
<div class="pop-wrapper" style='display:none;' id="refresh_cache">
    <div class="pop-black"></div>
    <div class="pop-tips">
        <h2>您确定要执行刷新操作吗？</h2>
        <div class="pop-btns">
            <a javascript:void(0); class="btn-blue left orange confirm_btn">确定</a>
            <a javascript:void(0); class="btn-white left cancel_btn">取消</a>
        </div>
    </div>
</div>
</body>
</html>