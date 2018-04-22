<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>决策树策略管理</title>
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/page/jadeParams.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            $(".label_tab").on("click", function(){
                if ($(this).find('.iconfont').hasClass('up')) {
                    $(this).find('.iconfont').removeClass('up');
                    $(this).find('.iconfont').addClass('down');
                    $(this).find(".iconfont").html("&#xe612");
                }else{
                    $(this).find('.iconfont').removeClass('down');
                    $(this).find('.iconfont').addClass('up');
                    $(this).find(".iconfont").html("&#xe69a");
                }
//                $(this).find(".iconfont").html("&#xe612");
                $(this).closest(".list-celue").find(".list-celue-ul").slideToggle();
            });

            var addBusiTypeFun = new Pop({
                'wrapper': $("#pop-policies"),
                'trigger': $(".to_add_btn"),
                'submitBtn': $(".pop_submit_btn"),
                'submitHandel': function () {
                    saveBusiTypeForm();
                },
                'beforeOpen': function ($wrapper) {
                    $wrapper.find(".pop_title").text("新建评分卡变量的业务类型");
                    $wrapper.find(".name").val("");
                    $wrapper.find(".description").val("");
                    $wrapper.find(".inpt").val("");
                }
            });


            var addDefinitionFun = new Pop({
                'wrapper': $("#pop-definition"),
                'trigger': $(".to_add_btn_defin"),
                'submitBtn': $(".pop_submit_btn_defin"),
                'submitHandel': function () {
                    saveDefiniForm();
                },
                'beforeOpen': function ($wrapper, $trigger) {
                    $wrapper.find(".pop_title").text("新建评分卡变量");
                    $wrapper.find(".typeId").val($trigger.attr('typeId'));
                    $wrapper.find(".definId").val($trigger.attr('id'));
                    $wrapper.find(".chineseName").val($trigger.attr('chineseName'));
                    $wrapper.find(".paramName").val($trigger.attr('paramName'));
                    $wrapper.find(".dbType").val($trigger.attr('dbType'));
                }
            });

            var delBusiTypeFun = new Confirm({
                'wrapper': $("#confirm_del_policies"),
                'trigger': {'parent': $(".tablebox"), 'selector': '.delete_policies'},
                confirmHandel: function ($theBtn) {
                    delBusiType($theBtn.closest('.list-celue').attr('busiType'), '${basePath}/jadeParams/delBusiType.do');
                }
            });

            var delDefinitionFun = new Confirm({
                'wrapper': $("#confirm_del_policy"),
                'trigger': {'parent': $(".list-icons"), 'selector': '.delete_definition'},
                confirmHandel: function ($theBtn) {
                    delDefinition($theBtn.attr('definitionId'), $("#delDefinitionUrl").val(), $theBtn, function () {
                        $theBtn.closest('.clearfix').remove();
                    },false);
                }
            });
            delBusiTypeFun.init();
            addBusiTypeFun.init();
            delDefinitionFun.init();
            addDefinitionFun.init();

            $(".s_one").on("click", function () {
                var ul = $(".pop-wrapper");
                if (ul.css("display") === "none") {
                    ul.show();
                } else {
                    ul.hide();
                }
            });

            $(".cancel_button").on("click", function () {
                $(".pop-wrapper").hide();
            });

            $(".cancel_button_defin").on("click", function () {
                $(".pop-wrapper").hide();
            });
        })

        function delBusiType(busiTypeId, delUrl, jumpToUrl) {
            $.ajax({
                cache: true,
                type: "POST",
                url: delUrl,
                data: {'busiTypeId': busiTypeId},
                dataType: 'json',
                error: function (request) {
                    showTempErrorPop("Connection error");
                },
                success: function (data) {
                    console.log(data);
                    if (data.res.code < 0) {
                        showTempErrorPop("该类型被某个变量正在使用中");
                    } else {
                        if (typeof(jumpToUrl) == 'undefined') {
                            window.location.reload();
                        } else {
                            window.location = jumpToUrl;
                        }
                    }
                }
            });
        }

        function delDefinition(definitionId, updateUrl, $theBtn, on_suss_func, isAlertMsg) {
            $.ajax({
                cache: true,
                type: "POST",
                url: updateUrl,
                data: {
                    'definitionId': definitionId
                },
                dataType: 'json',
                error: function (request) {
                    showTempErrorPop("Connection error");
                },
                success: function (data) {
                    if (data.res.code < 0) {
                        showTempErrorPop("该参数被某个评分卡正在使用中");
                    } else {
                        if (typeof(isAlertMsg) == 'undefined') {
                            showTempErrorPop(data.res.msg);
                        }
                        on_suss_func();
                    }
                }
            });
        }

        function toEditPolicy(sceneNo, policyId) {
            window.location = '${basePath}/dtPolicy/toEdit.do?sceneNo=' + sceneNo + '&policyId=' + policyId;
        }
    </script>
</head>
<body>
<input type="hidden" name="saveBusiTypeUrl" id="saveBusiTypeUrl" value="${basePath}/jadeParams/saveBusiType.do">
<input type="hidden" name="delBusiTypeUrl" id="delBusiTypeUrl" value="${basePath}/jadeParams/delBusiType.do">
<input type="hidden" name="delDefinitionUrl" id="delDefinitionUrl" value="${basePath}/jadeParams/delDefinition.do">
<input type="hidden" name="saveDefinitionUrl" id="saveDefinitionUrl" value="${basePath}/jadeParams/saveDefinition.do">
    <div class="content">
        <div class="btn-add btn-add-long to_add_btn">新建参数类型</div>
        <div class="list mar20 tablebox">
            <c:forEach var="item" items="${jadeModeList}" varStatus="status">
                <div class="list-celue" busiType="${item.id}"  policiesName="${item.typeName}">
                <div class="list-celue-title clearfix">
                    <p class='list-icons'>
                        <i class="iconfont to_add_btn_defin" typeId="${item.id}" title='添加'>&#xe6df;</i>
                        <i class="iconfont delete_policies" policyId="${item.id}" title='删除'>&#xe738;</i>
                    </p>
                    <h2><div class="label_tab" style="width: 70%" >
                        <i class="iconfont up">&#xe69a;</i><span >${item.typeName}(${item.id})</span>
                    </div>
                    </h2>
                </div>
                <ul class='list-celue-ul' id="wrapper${status.index}">
                    <c:forEach var="modeDefinition" items="${item.listDefinitions}">
                    <li class='clearfix'>
                        <p class='list-icons'>
                            <i title='编辑' class="iconfont icon-edit to_add_btn_defin" id="${modeDefinition.id}" typeId="${modeDefinition.typeId}"
                               chineseName="${modeDefinition.chineseName}" paramName="${modeDefinition.paramName}" dbType="${modeDefinition.dbType}">&#xe6f5;</i>
                            <i class="iconfont icon-delete delete_definition" definitionId="${modeDefinition.id}" title="删除参数">&#xe738;</i>
                        </p>
                        <h2>
                            <i class="dot"></i><span>${modeDefinition.chineseName}(${modeDefinition.paramName})</span>
                        </h2>
                    </li>
                    </c:forEach>
                </ul>
                </div>
            </c:forEach>
        </div>
        </div>
    </div>
    <!--content end-->
    <div class="pop-wrapper" id="pop-policies" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop">
            <div class="pop-title">
                <div class="pop-close">关闭</div>
                <h2 class="pop_title">评分卡变量的业务类型</h2>
            </div>
            <form id="editBusiTypeForm">
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <td>参数类型编号</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input name="id" id="id" type="text" class="input name"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>类型名称</td>
                        <td><div class="input-text" style='width:300px;'><input name="typeName" type="text" class="input inpt"></div></td>
                    </tr>
                    <tr>
                        <td>场景描述</td>
                        <td><div class="input-text" style='width:300px;'><input name="description" type="text" class="input description"></div></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="pop-btns">
                                <div class="btn-blue left pop_submit_btn" style="margin-right: 5%">保存</div>
                                <div class="btn-white left cancel_button" style="margin-left: 5%">关闭</div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            </form>
        </div>
    </div>

    <!--definition end-->
    <div class="pop-wrapper" id="pop-definition" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop">
            <div class="pop-title">
                <div class="pop-close">关闭</div>
                <h2 class="pop_title">评分卡变量的业务类型</h2>
            </div>
            <form id="editDefinitionForm">
                <input type="hidden" name="id" class="definId" >
                <input type="hidden" name="typeId" class="typeId" >
                <div class="pop-cnt">
                    <table cellpadding='0' cellspacing='0' class='table-inside'>
                        <tr>
                            <td>参数中文名称</td>
                            <td>
                                <div class="input-text" style='width:300px;'><input name="chineseName" type="text" class="input chineseName"></div>
                            </td>
                        </tr>
                        <tr>
                            <td>参数名称</td>
                            <td><div class="input-text" style='width:300px;'><input name="paramName"  type="text" class="input paramName"></div></td>
                        </tr>
                        <tr>
                            <td>数据类型</td>
                            <td><div class="input-text" style='width:300px;color:blue'><input name="dbType" type="text" class="input dbType" style="width:80px;">0:字符串类型 2:整数类型 4:小数类型</div></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <div class="pop-btns">
                                    <div class="btn-blue left pop_submit_btn_defin" style="margin-right: 5%">保存</div>
                                    <div class="btn-white left cancel_button_defin" style="margin-left: 5%">关闭</div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>

    <!--新建策略集end-->
    <div class="pop-wrapper" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop-tip">
            <i class="iconfont ok">&#xe72d;</i>
            <p>新建策略集成功</p>
        </div>
    </div>
    <!--新建/编辑策略集成功 end-->
    <div class="pop-wrapper" style='display:none;'>
        <div class="pop-black"></div>
        <div class="pop-tip">
            <i class="iconfont error">&#xe6f2;</i>
            <p>新建策略集失败</p>
        </div>
    </div>
    <!--新建/编辑策略集失败 end-->
    <div class="pop-wrapper" style='display:none;' id="confirm_update_status">
        <div class="pop-black"></div>
        <div class="pop-tips">
            <h2>您确定要执行该操作吗？</h2>
             <div class="pop-btns">
                <a javascript:void(0); class="btn-blue left orange confirm_btn">确定</a>
                <a javascript:void(0); class="btn-white left cancel_btn">取消</a>
            </div>
        </div>
    </div>
    <!--删除策略集 end-->
    <div class="pop-wrapper" style='display:none;' id="confirm_del_policies">
        <div class="pop-black"></div>
        <div class="pop-tips">
            <h2>您确定要执行该操作吗？</h2>
            <div class="pop-btns">
                <a javascript:void(0); class="btn-blue left orange confirm_btn">确定</a>
                <a javascript:void(0); class="btn-white left cancel_btn">取消</a>
            </div>
        </div>
    </div>
    <!--删除策略end-->
    <div class="pop-wrapper" style='display:none;' id="confirm_del_policy">
        <div class="pop-black"></div>
        <div class="pop-tips">
            <h2>您确定要执行该操作吗？</h2>
            <div class="pop-btns">
                <a javascript:void(0); class="btn-blue left orange confirm_btn">确定</a>
                <a javascript:void(0); class="btn-white left cancel_btn">取消</a>
            </div>
        </div>
    </div>
</body>
</html>