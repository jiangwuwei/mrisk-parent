
var editRouter = function (thisObject) {
    $(thisObject).closest(".item-shuxing").find(".item-cnt-shuxing").slideToggle();
}

var delRouter = function (thisObject) {
    var routerId = $(thisObject).closest(".item-shuxing").attr("routerId");
    if (typeof routerId == 'undefined') {
        $(thisObject).closest(".item-shuxing").remove();
        return;
    }
    ajaxDelRouter(routerId, function(){
        $(thisObject).closest(".item-shuxing").remove();
        if($(".item-shuxing").length == 0 || typeof $(".item-shuxing").length == 'undefined'){
            $("#sceneAttribute").removeAttr("disabled");
        }

    });
}

var ajaxDelRouter = function(routerId, onSuccessFunc){
    $.ajax({
        type: "POST",
        url: basePath + "/policyRouter/delRouter.do",
        data: {
            routerId: routerId
        },
        dataType: 'json',
        timeout: 3000,
        success: function (data) {
            if (data.res.code < 0) {
                showTempErrorPop("路由删除失败!");
            } else {
                onSuccessFunc();
            }
        },
        error: function (data) {
            showTempErrorPop("路由删除失败!");
        }
    });
}

var saveRouter = function (thisObject) {
    var routerObject = $(thisObject).closest(".item-shuxing");
    if(!validateNode( routerObject.attr("routerIndex"))){
        return false;
    }
    saveRouterInfo(thisObject, routerObject);
    saveRouterForm();
}
var saveRouterInfo = function (thisObject, routerObject) {
    var routerIndex = routerObject.attr("routerIndex");
    $("#tdId").val(routerObject.attr("routerId"));
    $("#tdSceneNo").val($("#sceneNo").val());
    $("#tdName").val($("#routeName_" + routerIndex).val());
    $("#tdWeightValue").val($("#weightValue_" + routerIndex).val());
    $("#tdPolicyId").val($("#policy_" + routerIndex).val());
    var selectedAttr = $("#sceneAttribute option:selected");
    $("#tdRoutes0_paramDataType").val(selectedAttr.attr("paramDataType"));
    $("#tdRoutes1_paramDataType").val(selectedAttr.attr("paramDataType"));
    $("#tdRoutes0_paramName").val(selectedAttr.val());
    $("#tdRoutes1_paramName").val(selectedAttr.val());
    $("#tdIsJoin").val($("#isJoin_" + routerIndex).val());
    var routeOper0 = $("#operation_0_"+routerIndex+" option:selected");
    var routeOper1 = $("#operation_1_"+routerIndex+" option:selected");
    $("#tdRoutes0_Operation").val(routeOper0.val());
    $("#tdRoutes0_Value").val($("#value_0_"+routerIndex).val());
    $("#tdRoutes1_operation").val(routeOper1.val());
    $("#tdRoutes1_value").val($("#value_1_"+routerIndex).val());
}
var validateNode = function (routerIndex) {
    var routeName = $("#routeName_" + routerIndex).val();
    if(typeof routeName == "undefined" || routeName.trim().length == 0){
        showTempErrorPop("路由名称不能为空!");
        return false;
    }
    var score = $("#weightValue_" + routerIndex).val();
    if(typeof score == "undefined" || score.trim().length == 0 || (score.length > 0 && isNaN(score))){
        showTempErrorPop("权重必须为数字!");
        return false;
    }
    var value0 = $("#value_0_"+routerIndex).val()
    if(typeof value0 == "undefined" || value0.trim().length == 0){
        showTempErrorPop("第一条路径值不能为空!");
        return false;
    }
    var isJoin = $("#isJoin_" + routerIndex).val();
    var value1 = $("#value_1_"+routerIndex).val()
    if((isJoin == "true") && (typeof value1 == "undefined" || value1.trim().length == 0)){
        showTempErrorPop("第二条路径值不能为空!");
        return false;
    }
    return true;
}

var saveRouterForm = function () {
    var options = {
        formId: 'routerForm',
        url: basePath + '/policyRouter/saveRouter.do'
    };
    submitForm(options, function (data) {
        if (data.res.code < 0) {
           showTempErrorPop("路由保存失败!")
        } else {
            window.location.reload();
        }
    });
}
