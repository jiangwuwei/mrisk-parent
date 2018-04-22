var selectConfig = {"uid": "", "deviceFingerprint": "", "riskId": "", "riskDate": "", "tabIndex": 0, "currentTab": 0};
function trOnclick(uid, deviceFingerprint, riskId, riskDate, tabIndex, riskBusiType, thisObject) {
    if ( riskBusiType == '' ){
        riskBusiType = 1;
    }
    if (!$(thisObject).closest("tr").hasClass("selected")) {
        $(thisObject).closest("tr").addClass("selected");
        $(thisObject).siblings("tr").removeClass("selected");
    }
    selectConfig = $.extend(selectConfig, {
        "uid": uid,
        "deviceFingerprint": deviceFingerprint,
        "riskId": riskId,
        "riskDate": riskDate
    });
    if (tabIndex == 0) {
        if (selectConfig.currentTab == 0) {
            //初始化div数据
            $("#antiFraudEventDiv").load($("#basePath").val() + "/monitor/eventDetailDiv.do", {
                "riskId": riskId,
                "riskDate": riskDate
            }, function (response, status, xhr) {
                selectConfig.currentTab = 1;
                $("#slideInfoDetail").show(); //展示侧边栏整体
                $("#localtable").addClass("selected"); //选中事件详情页
                $("#antiFraudEventDiv").show(); //展示反欺诈事件详情
                $("#slideInfoContentSaas").hide(); //隐藏关联分析页面
                $("#Saastable").removeClass("selected"); //去除关联分析tab的选中状态
                cssAdd();
                buildTree(riskBusiType);
            });
            if (skipSaas(riskBusiType)) {
                return;
            }
            $("#slideInfoContentSaas").load($("#basePath").val() + "/monitor/relationshipDiv.do", {
                "uid": uid,
                "deviceFingerprint": deviceFingerprint,
                "riskDate": riskDate
            });
        } else {
            //事件变更时从新装载数据
            $("#antiFraudEventDiv").load($("#basePath").val() + "/monitor/eventDetailDiv.do", {
                "riskId": riskId,
                "riskDate": riskDate
            }, function (response, status, xhr) {
                buildTree(riskBusiType);
                cssAdd();
            });
            if (skipSaas(riskBusiType)) {
                return;
            }
            $("#slideInfoContentSaas").load($("#basePath").val() + "/monitor/relationshipDiv.do", {
                "uid": uid,
                "deviceFingerprint": deviceFingerprint,
                "riskDate": riskDate
            });
        }
    } else {
        showTab(tabIndex);
    }
}

function getChange(element, tabIndex) {
    trOnclick(selectConfig.uid, selectConfig.deviceFingerprint, selectConfig.riskId, selectConfig.riskDate, tabIndex);
}

function showTab(tabIndex) {
    selectConfig.currentTab = tabIndex;
    if (tabIndex == 1) {
        $("#antiFraudEventDiv").show();
        if (!$("#localtable").hasClass("selected")) {
            $("#localtable").addClass("selected");
        }
        $("#slideInfoContentSaas").hide();
        if ($("#Saastable").hasClass("selected")) {
            $("#Saastable").removeClass("selected");
        }
    } else if (tabIndex == 2) {
        $("#antiFraudEventDiv").hide();
        if ($("#localtable").hasClass("selected")) {
            $("#localtable").removeClass("selected");
        }
        $("#slideInfoContentSaas").show();
        if (!$("#Saastable").hasClass("selected")) {
            $("#Saastable").addClass("selected")
        }
    }
}

function resetValue(event) {
    selectConfig.currentTab = 0;
    $("#slideInfoDetail").hide();
    event.stopImmediatePropagation();

}

function cssAdd() {
    $("tr.parentItem").on("click", function () {
        if ($(this).attr('hitQuotasCount') == 0) {
            return;
        }
        if ($(this).hasClass('open')) {
            $(this).next('tr.childItem').hide();
            $(this).removeClass('open');
        } else {
            $(this).next('tr.childItem').show();
            $(this).addClass('open');
        }
    });
}

function popHitRule(thisObject) {
    var ruleIndex = $(thisObject).attr("ruleIndex");
    $("#ruleName" + ruleIndex).addClass("selected");
    $("#ruleDetail" + ruleIndex).show();
    $("#hitRuleDetailDiv").show();
}

function clickPopHitRule(thisObject) {
    var ruleIndex = $(thisObject).attr("ruleIndex");
    if (!$("#ruleName" + ruleIndex).hasClass("selected")) {
        $(thisObject).siblings("li").removeClass("selected");
        $("#ruleName" + ruleIndex).addClass("selected");
        $(".guize-cnt").hide();
        $("#ruleDetail" + ruleIndex).show();
    }
}

function popClose() {
    $("#hitRuleDetailDiv").hide();
}

//决策树没有关联分析
function skipSaas(riskBusiType) {
    if (riskBusiType == 2 || riskBusiType == 3) {
        $("#Saastable").hide();
        return true;
    }
    $("#Saastable").show();
    return false;
}

//构建决策树
function buildTree(riskBusiType) {
    if (riskBusiType != 2) {
        return ;
    }
    $.ajax({
        type: "POST",
        url: $("#basePath").val() + "/dt/buildEventTree.do",
        data: {
            policyId: $("#dt_policy_id").val(),
            nodeIdStr: $("#dt_hitNodeId_str").val()
        },
        dataType: 'json',
        timeout: 3000,
        success: function (data) {
            if (data.res.code < 0) {
                showTempErrorPop("决策树构建失败！")
            } else {
                var echartsVoString = data.echartsVoString;
                var jsonTree = JSON.parse(echartsVoString);
                setDecisionTree(jsonTree);
            }
        },
        error: function (data) {
            showTempErrorPop("决策树构建失败！")
        },
    });
}

function initCondBlock() {
    if ($("#deepSearch").val() == 1) {
        $("#deepSearchId1").show();
        $("#deepSearchId2").show();
        $("#deepSearchBtn").addClass("deep-search-up");
    } else {
        $("#deepSearchId1").hide();
        $("#deepSearchId2").hide();
    }
}

function deepSearch() {
    if (!$("#deepSearchId1").is(":visible")) {
        $("#deepSearchId1").show();
        $("#deepSearchId2").show();
        $("#deepSearch").val(1);
        $("#deepSearchBtn").addClass("deep-search-up");
    } else {
        $("#deepSearchId1").hide();
        $("#deepSearchId2").hide();
        $("#deepSearch").val(0);
        $("#deepSearchBtn").removeClass("deep-search-up");
    }
}

function searchEvent() {
    $("#searchForm").submit();
}