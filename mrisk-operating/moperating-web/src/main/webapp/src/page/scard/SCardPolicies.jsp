<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>配置规则管理_风控策略管理</title>
    <%@include file="/src/page/common/TempErrorPop.jsp" %>
    <%@include file="/src/page/common/TempSuccessPop.jsp" %>
    <%@include file="/src/page/common/common.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/page/scenesPolicies.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/common/common.js"/>"></script>
    <script type="text/javascript">
        $(function () {
            $(".label_tab").on("click", function () {
                if ($(this).find('.iconfont').hasClass('up')) {
                    $(this).find('.iconfont').removeClass('up');
                    $(this).find('.iconfont').addClass('down');
                    $(this).find(".iconfont").html("&#xe612");
                } else {
                    $(this).find('.iconfont').removeClass('down');
                    $(this).find('.iconfont').addClass('up');
                    $(this).find(".iconfont").html("&#xe69a");
                }
//                $(this).find(".iconfont").html("&#xe612");
                $(this).closest(".list-celue").find(".list-celue-ul").slideToggle();
            });

            $("#search").on("click", function () {
                window.location = '${basePath}/scardPolicies/list.do?policiesName=' + $("#policiesName").val();
            });

            $("#selectSceneNo").on("change", function () {
                window.location = '${basePath}/scardPolicies/list.do?sceneNo=' + $("#selectSceneNo").val();
            });
            var addPolicies = new Pop({
                'wrapper': $("#pop-policies"),
                'trigger': $(".to_add_btn"),
                'submitBtn': $(".pop_submit_btn"),
                'submitHandel': function () {
                    savePolicies();
                },
                'beforeOpen': function ($wrapper) {
                    $wrapper.find(".pop_title").text("新建策略集");
                    $(".name").val("");
                    $(".description").val("");
                    $(".sceneNo").removeAttr("onfocus");
                    $(".sceneNo").removeAttr("onchange");
                    $(".sceneNo").removeAttr("style");
                    $wrapper.find(".input-policy").val("");
                    $("#editType").val(1);
                }
            });

            var editPolicies = new Pop({
                'wrapper': $("#pop-policies"),
                // 'trigger':{'parent':$(".policiesTable"), 'selector':'.to_edit_btn'},
                'trigger': {'parent': $(".tablebox"), 'selector': '.update_btn'},
                'submitHandel': function () {
                    savePolicies();
                },
                'beforeOpen': function ($wrapper, $trigger) {
                    $wrapper.find(".pop_title").text("编辑策略集");
                    $wrapper.find(".name").val($trigger.closest('.list-celue').attr('policiesName'));
                    $wrapper.find(".sceneNo").val($trigger.closest('.list-celue').attr('sceneNo'));
                    $wrapper.find(".sceneNo").attr('onfocus', 'this.si=this.selectedIndex;');
                    $wrapper.find(".sceneNo").attr('onchange', 'this.selectedIndex=this.si;');
                    $wrapper.find(".sceneNo").attr('style', 'width: 200px; background-color: #EEEEEE;');
                    $wrapper.find(".sceneNo").val($trigger.closest('.list-celue').attr('sceneNo'));
                    $wrapper.find(".description").val($trigger.closest('.list-celue').attr('description'));
                    $wrapper.find(".createdDateStr").val($trigger.closest('.list-celue').attr('createdDateStr'));
                    $("#editType").val(2);
                }
            });
            var confirm_del_policies = new Confirm({
                'wrapper': $("#confirm_del_policies"),
                'trigger': {'parent': $(".tablebox"), 'selector': '.delete_policies'},
                confirmHandel: function ($theBtn) {
                    delBySceneNo($theBtn.closest('.list-celue').attr('sceneNo'), '${basePath}/scardPolicies/delById.do');
                }
            });
            var confirm_del_policy = new Confirm({
                'wrapper': $("#confirm_del_policy"),
                'trigger': {'parent': $(".list-icons"), 'selector': '.delete_policy'},
                confirmHandel: function ($theBtn) {
                    delPolicyById($theBtn.attr('policyId'), $("#delScardUrl").val(),function () {
                        $theBtn.closest('.clearfix').remove();
                    });
                }
            });
            var confirm_update_status = new Confirm({
                'wrapper': $("#confirm_update_status"),
                'trigger': {'parent': $(".tablebox"), 'selector': '.to_update_status'},
                confirmHandel: function ($theBtn) {
                    var id = $theBtn.closest(".rule-run-state").attr('updateId');
                    var toStatus = $theBtn.attr('toStatus');
                    updateStatusById(id, $("#updateStatusUrl").val(), toStatus, $theBtn, function () {
                        var pixel = (toStatus == 1) ? "selected" : "closed";
                        $theBtn.closest('.rule-run-state').find(".to_update_status").attr('class', pixel + " to_update_status");
                        $theBtn.siblings("span").removeClass("selected");
                        $theBtn.addClass("selected");
                    }, false);
                }
            });

            var cloneFunc = new Confirm({
                'wrapper': $("#clone_scard"),
                'trigger': {'parent': $(".tablebox"), 'selector': '.clone_scard'},
                confirmHandel: function ($theBtn) {
                    var scardId = $theBtn.attr('scardId');
                    cloneScard(scardId);
                }
            });
            cloneFunc.init();
            confirm_update_status.init();
            confirm_del_policies.init();
            confirm_del_policy.init();
            addPolicies.init();
            editPolicies.init();
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
        })

        function delPolicyById(id, updateUrl, on_suss_func) {
            $.ajax({
                cache: true,
                type: "POST",
                url: updateUrl,
                data: {
                    'id': id
                },
                dataType: 'json',
                error: function (request) {
                    showTempErrorPop("Connection error");
                },
                success: function (data) {
                    if (data.res.code < 0) {
                        showTempErrorPop(data.res.msg);
                    } else {
                        on_suss_func();
                    }
                }
            });
        }
        function toEditPolicy(sceneNo, policyId) {
            window.location = '${basePath}/scard/toEdit.do?sceneNo=' + sceneNo + '&scardId=' + policyId;
        }
        function toEditPolicyRouter(sceneNo) {
            window.location = '${basePath}/scardRouter/toEdit.do?sceneNo=' + sceneNo;
        }

        function addParamForScard(sceneNo, scardId) {
            $("#pop-selectParam").load("<c:url value='/scardParam/selectParamList.do'/>", {"scardId":scardId}, function () {
                $("#pop-selectParam").show();
            })
        }

        function cloneParamForScard(sceneNo, scardId) {
            $("#pop-selectParam").load("<c:url value='/scardParam/selectParamList.do'/>", {"scardId":scardId}, function () {
                $("#pop-selectParam").show();
            })
        }

        function  closeWin() {
            $("#pop-selectParam").hide();
        }

        function saveSelectParam() {
            var jsonData = $("#selectParamForm").serialize();
            $.ajax({
                type: "POST",
                url: "<c:url value='/scardParam/saveSelectParams.do'/>",
                dataType:'json',
                data:jsonData,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.res.code == 0 ) {
                        showTempSuccessPop("保存成功");
                        var div = $("#pop-selectParam").hide();
                    }else{
                        showTempErrorPop("保存失败,可能原因是参数名称重复!");
                    }
                }
            })
        }


        function cloneScard(scardId, requestUrl, jumpToUrl) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/scard/cloneScard.do'/>",
                data: {"scardId": scardId},
                dataType: "json",
                timeout: 10000,
                error: function (request) {
                    showTempErrorPop("Connection error" + request );
                },
                success: function (data) {
                    console.log(data);
                    if (data.res.code < 0) {
                        showTempErrorPop(data.res.msg);
                    } else {
                        if (typeof(jumpToUrl) == 'undefined') {
                            window.location.reload();
                        } else {
                            // window.location = jumpToUrl;
                        }
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="content">
    <div class="search clearfix">
        <input type="hidden" id="updateUrl" value="${basePath}/scardPolicies/update.do">
        <input type="hidden" id="insertUrl" value="${basePath}/scardPolicies/insert.do">
        <input type="hidden" id="updateStatusUrl" value="${basePath}/scard/updateStatus.do">
        <input type="hidden" id="delScardUrl" value="${basePath}/scard/delById.do">
        <input type="hidden" id="editType">
        <ul class='search-ul left clearfix'>
            <li class="left">
                <label>场景名称</label>

                <select class="input-select" id="selectSceneNo">
                    <option value="all" <c:if test="${sceneNo eq 'all'}">selected="selected"</c:if>>全部</option>
                    <c:forEach items="${allScenes}" var="item">
                        <option value="${item.sceneNo}"
                                <c:if test="${item.sceneNo eq sceneNo}">selected="selected"</c:if>>${item.name}(${item.sceneNo})
                        </option>
                    </c:forEach>
                </select>
            </li>
            <li class="left">
                <label>策略集名称</label>
                <div class="input-text"><input type="text" class="input" value="${policiesName}" id="policiesName">
                </div>
            </li>
        </ul>
        <div class="btn left" id="search">查询</div>
    </div>
    <!--search end-->
    <!--新建场景按钮开始-->
    <div class="btn-add btn-add-long to_add_btn">新建策略集</div>
    <div class="list mar20 tablebox">
        <c:forEach var="pair" items="${list}" varStatus="status">
            <div class="list-celue" sceneNo="${pair.key.sceneNo}" policiesName="${pair.key.name}"  description="${pair.key.description}"  createdDateStr="${pair.key.createdDateStr}">
                <div class="list-celue-title clearfix">
                    <p class='list-icons'>
                        <i class="iconfont" title='添加策略' onclick="toEditPolicy('${pair.key.sceneNo}',0)">&#xe6df;</i>
                        <i class="iconfont" title='添加策略路由' onclick="toEditPolicyRouter('${pair.key.sceneNo}')">&#xe634;</i>
                        <i class="iconfont update_btn" title='编辑'>&#xe6f5;</i>
                        <i class="iconfont delete_policies" title='删除'>&#xe738;</i>
                    </p>
                    <h2>
                        <div class="label_tab" style="width: 70%">
                            <i class="iconfont up">&#xe69a;</i><span>${pair.left.name}(${pair.key.sceneNo})</span>
                            <c:if test="${pair.left.hasPolicyRouter}">
                                <strong>策略路由</strong>
                            </c:if>
                        </div>
                    </h2>
                </div>
                <ul class='list-celue-ul' style="display: block;" id="wrapper${status.index}">
                    <c:forEach var="policy" items="${pair.value}">
                        <li class='clearfix'>
                            <p class='list-icons'>
                                <i class='boxs rule-run-state' updateId="${policy.id}" status="${policy.status}" >
                                    <span class='${policy.status eq 1?"selected":"closed"} to_update_status' toStatus="1">关闭</span>
                                    <span class="${policy.status eq 2?"selected":"closed"} to_update_status" toStatus="2">正式</span>
                                </i>
                                <i class='boxs rule-run-state'>
                                    <span class="closed clone_scard" style="display:inline-block;width:80px;background-color:darkorange" scardId="${policy.id}">克隆该评分卡</span>
                                </i>
                                <i class="iconfont" title="添加参数变量" policyId="${policy.id}" onclick="addParamForScard('${pair.key.sceneNo}', ${policy.id})"></i>
                                <i title='编辑' class="iconfont icon-edit to_edit_policy" policyId="${policy.id}" onclick="toEditPolicy('${pair.key.sceneNo}', ${policy.id})">&#xe6f5;</i>
                                <i class="iconfont icon-delete delete_policy" policyId="${policy.id}" title="删除策略" title='删除'>&#xe738;</i>
                            </p>
                            <h2>
                                <i class="dot"></i><span>${policy.name}&nbsp;<span style="color:blue">(${policy.scardNo}-${policy.weightValue})</span></span>
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
            <h2 class="pop_title">新建场景</h2>
        </div>
        <form id="editPoliciesForm">
            <div class="pop-cnt">
                <table cellpadding='0' cellspacing='0' class='table-inside'>
                    <tr>
                        <td>策略集名称</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input name="name" type="text" class="input name input-policy"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>场景标识</td>
                        <td>
                            <select name="sceneNo" class="input-select sceneNo">
                                <c:forEach items="${allScenes}" var="item">
                                    <option value="${item.sceneNo}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>场景描述</td>
                        <td>
                            <div class="input-text" style='width:300px;'><input name="description" type="text" class="input description input-policy">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <div class="pop-btns">
                                <div class="btn-blue left pop_submit_btn" style="margin-right: 5%">保存</div>
                                <div class="btn-white left cancel_button" style="margin-left: 5%">返回</div>
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

<!--删除策略end-->
<div class="pop-wrapper" style='display:none;' id="clone_scard">
    <div class="pop-black"></div>
    <div class="pop-tips">
        <h2>您确定要克隆一个同样的评分卡吗？</h2>
        <div class="pop-btns">
            <a javascript:void(0); class="btn-blue left orange confirm_btn">确定</a>
            <a javascript:void(0); class="btn-white left cancel_btn">取消</a>
        </div>
    </div>
</div>
<!--评分卡参数配置-->
<div class="pop-wrapper new-pop-wrapper" style="display:none;"  id="pop-selectParam">
</div>
</body>
</html>