var changeTab = function (thisObject, editTab) {
    if (!$(thisObject).hasClass("selected")) {
        $(thisObject).siblings("li").removeClass("selected");
        $(thisObject).addClass("selected");
        if (editTab == 0) {
            $("#editTab0").show();
            $("#editTab1").hide();
            $("#editTab2").hide();
        } else  if (editTab == 1) {
            if ($("#decisionTree").attr("isLoadedTree") != 1) {
                buildTree();
                $("#decisionTree").attr("isLoadedTree", 1);
            }
            $("#editTab0").hide();
            $("#editTab1").show();
            $("#editTab2").hide();
        } else  if (editTab == 2) {
            if ($("#nodeList").attr("isLoadedTree") != 1) {
                $("#nodeList").load($("#basePath").val() + "/dt/nodeList.do", {"policyId": $("#policy_id").val()}, function () {
                    $("#editTab0").hide();
                    $("#editTab1").hide();
                    $("#editTab2").show();
                    $("#nodeList").attr("isLoadedTree", 1);
                });
            }else{
                $("#editTab0").hide();
                $("#editTab1").hide();
                $("#editTab2").show();
            }
        }
    }
}

var buildTree = function () {
    $.ajax({
        type: "POST",
        url: $("#basePath").val() + "/dt/buildTree.do",
        data: {
            policyId: $("#policy_id").val()
        },
        dataType: 'json',
        timeout: 3000,
        success: function (data) {
            if (data.res.code < 0) {
                console.log(data);
            } else {
                var echartsVoString = data.echartsVoString;
                var jsonTree = JSON.parse(echartsVoString);
                setDecisionTree(jsonTree);
            }
        },
        error: function (data) {
            console.log(data);
        },
    });
}

var closeSilde = function () {
    $("#treeDetail").hide();
}

var editRoute = function (thisObject) {
    $(thisObject).closest(".item-shuxing").find(".item-cnt-shuxing").slideToggle();
}

var delRoute = function (thisObject) {
    var nodeId = $(thisObject).closest(".item-shuxing").attr("branchNodeId");
    if (typeof nodeId == 'undefined') {
        $(thisObject).closest(".item-shuxing").remove();
        return;
    }
    delNode(nodeId, function(){
        $(thisObject).closest(".item-shuxing").remove();
        buildTree();
    });
}
var delLeafNode = function(){
    var nodeId = $("#tdCurrNodeId").val();
    delNode(nodeId, function(){
        buildTree();
        closeSilde();
    });
}
var delNode = function(nodeId, onSuccessFunc){
    $.ajax({
        type: "POST",
        url: $("#basePath").val() + "/dt/delNodeById.do",
        data: {
            nodeId: nodeId
        },
        dataType: 'json',
        timeout: 3000,
        success: function (data) {
            if (data.res.code < 0) {
                showTempErrorPop("节点删除失败!");
            } else {
                onSuccessFunc();
            }
        },
        error: function (data) {
            showTempErrorPop("节点删除失败!");
        }
    });
}

//保存分叉节点
var saveBranchNode = function (thisObject) {
    var branchObject = $(thisObject).closest(".item-shuxing");
    if(!validateNode( branchObject.attr("nodeIndex"))){
        return false;
    }
    $("#tdUpdateType").val(2);
    saveBranchNodeInfo(thisObject, branchObject);
    saveNodeForm();
}
var saveBranchNodeInfo = function (thisObject, branchObject) {
    var nodeIndex = branchObject.attr("nodeIndex");
    saveCommonNodeInfo();
    var nodeId = branchObject.attr("branchNodeId");
    $("#tdId").val(nodeId);
    $("#tdNodeId").val(nodeId);
    $("#tdParentId").val($("#tdCurrNodeId").val());
    $("#tdNodeNo").val(branchObject.attr("nodeNo"));
    $("#tdRouteName").val($("#routeName_" + nodeIndex).val());
    $("#tdScore").val($("#score_" + nodeIndex).val());
    $("#tdIsJoin").val($("#isJoin_" + nodeIndex).val());
    var nodeType = $('input:radio[name="nodeTypeRadio"]:checked').val();
    if(typeof nodeId != 'undefined'){  //不能更新节点的类型
        nodeType = branchObject.attr("nodeType");
    }
    $("#tdNodeType").val(nodeType);
    var routeOper0 = $("#operation_0_"+nodeIndex+" option:selected");
    var routeOper1 = $("#operation_1_"+nodeIndex+" option:selected");
    $("#tdRoutes0_Operation").val(routeOper0.val());
    $("#tdRoutes0_Value").val($("#value_0_"+nodeIndex).val());
    $("#tdRoutes1_operation").val(routeOper1.val());
    $("#tdRoutes1_value").val($("#value_1_"+nodeIndex).val());
}
var validateNode = function (nodeIndex) {
    var routeName = $("#routeName_" + nodeIndex).val();
    if(typeof routeName == "undefined" || routeName.trim().length == 0){
        showTempErrorPop("路由名称不能为空!");
        return false;
    }
    var score = $("#score_" + nodeIndex).val();
    if(typeof score == "undefined" || score.trim().length == 0 || (score.length > 0 && isNaN(score))){
        showTempErrorPop("权重必须为数字!");
        return false;
    }
    var value0 = $("#value_0_"+nodeIndex).val()
    if(typeof value0 == "undefined" || value0.trim().length == 0){
        showTempErrorPop("第一条路径值不能为空!");
        return false;
    }
    var isJoin = $("#isJoin_" + nodeIndex).val();
    var value1 = $("#value_1_"+nodeIndex).val()
    if((isJoin == "true") && (typeof value1 == "undefined" || value1.trim().length == 0)){
        showTempErrorPop("第二条路径值不能为空!");
        return false;
    }
    return true;
}


//保存叶节点
var saveLeafNode = function (thisObject) {
    saveCommonNodeInfo();
    $("#tdNodeType").val(3);
    $("#tdUpdateType").val(3);
    saveNodeForm();
}

var saveCommonNodeInfo = function () {
    //指标部分
    var selectedQuota = $("#quotaId option:selected");
    $("#tdChineseName").val(selectedQuota.text());
    $("#tdQuotaId").val(selectedQuota.val());
    $("#tdParamName").val(selectedQuota.attr("paramName"));
    $("#tdRoutes0_paramDataType").val(selectedQuota.attr("paramDataType"));
    $("#tdRoutes1_paramDataType").val(selectedQuota.attr("paramDataType"));
    //节点为空的时候没有这些属性，所以需要从前面的策略那里提取
    $("#tdPolicyId").val($("#policy_id").val());
    $("#tdSceneNo").val($("#sceneNo").val());
}

var validateLeafNode = function () {
    if ($("#tdDescription").val().length == 0) {
        $("#tempErrorMsg").text("决策树节点描述不能为空");
        return false;
    }
}

var saveNodeForm = function () {
    var options = {
        formId: 'nodeForm',
        url: $("#basePath").val() + '/dt/saveNode.do'
    };
    submitForm(options, function (data) {
        if (data.res.code < 0) {
            $("#onErrorPop").show();
        } else {
            buildTree();
            closeSilde();
        }
    });
}


var changeNode = function (toNodeType) {
    if (toNodeType == 3) {
        $("#branchNodeId").hide();
        $("#leafNodeId").show();
    } else {
        $("#branchNodeId").show();
        $("#leafNodeId").hide();
    }
}