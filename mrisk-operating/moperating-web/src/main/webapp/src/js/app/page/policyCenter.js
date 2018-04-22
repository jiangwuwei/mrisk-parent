var del_rule_cond = function (thisObject) {
    $(thisObject).closest("ul").remove();
}
var change_cond_ext = function (obj, attrTypeIdIndex) {
    var selectText = $(obj).find('option:selected').text();
    var attrTypeId = "attrType" + attrTypeIdIndex;
    $("#" + attrTypeId).val($(obj).find('option:selected').attr('attrType'));
    if ((selectText.indexOf('金额') >= 0) && (selectText.indexOf('[系统]') < 0)) {//包含金额的指标
        $(obj).closest('ul').find('.cond_extent').show();
        return;
    }
    $(obj).closest('ul').find('.cond_extent').hide();
    $(obj).closest('ul').find('.cond_extent')[0].options[0].selected = true; //将金额属性置为无
}

var changeTab = function (thisObject, editTab) {
    if (!$(thisObject).hasClass("selected")) {
        $(thisObject).siblings("li").removeClass("selected");
        $(thisObject).addClass("selected");
        if (editTab == 0) {
            $("#editTab0").show();
            $("#editTab1").hide();
        } else {
            $("#editTab0").hide();
            $("#editTab1").show();
        }
    }
}

var editRule = function (ruleId, ruleMode) {
    $("#ruleDetail").load($("#basePath").val() + '/rule/getRuleDetail.do', {
        id: ruleId,
        ruleMode: ruleMode,
        sceneNo: $("#sceneNo").val()
    }, function () {
        $("#ruleDetail").show();
    });
}

var saveRule = function () {
    var formId = "#ruleForm";
    $(formId + " input[name='policyId']").val($("#policy_id").val());
    $(formId + " input[name='sceneNo']").val($("#sceneNo").val());
    // //验证
    if(!validateRule(formId)){
        return;
    }
    var ruleId = $(formId + " input[name='id']").val();
    var jump_to_url = $("#basePath").val()+'/policy/toEdit.do?sceneNo='+$("#sceneNo").val()+'&policyId=' + $("#policy_id").val() + '&editTab=1';
    var options = {
        formId: 'ruleForm',
        url: $("#basePath").val()+'/rule/insertOrUpdate.do'
    };
    submitForm(options, function(){
        if(ruleId == 0){
            window.location.href = jump_to_url;
        }else{
            if($("#oldRuleName").val() !== $("#newRuleName").val()){
                $("#ruleName_"+ruleId).text($("#newRuleName").val());
            }
            $("#ruleDetail").hide();
            $("#onSuccessPop").show();
        }
    });

};
var checkRuleContent =function(){
    if(!validateRule("#ruleForm")){
        return;
    }
    submitAjaxForm2("#ruleForm", $("#basePath").val()+'/rule/checkRuleContent.do', function (data) {
        $("#finalRuleContent").text(data.ruleContent);
        $("#finalRuleContentTr").show();
    }, 'noAlert');
};


var validateRule = function(formId){
    var ruleMode = $(formId + " input[name='ruleMode']").val();
    if ($(formId + " input[name='name']").val().length == 0) {
        showTempErrorPop('规则名称不能为空！');
        return false;
    }
    var score = $(formId + " input[name='score']").val();
    if ((score.length == 0) || (score.length > 0 && isNaN(score))) {
        showTempErrorPop('风险权重应当为数字')
        return false;
    }
    if (Number(score) <= 0 || Number(score) > 1000) {
        showTempErrorPop('风险权重应当大于0并且小于等于1000');
        return false;
    }
    var intPattern = new RegExp("^\\d+$");
    var weightValue = $(formId + " input[name='weightValue']").val();
    if (!intPattern.test(weightValue)) {
        showTempErrorPop('执行顺序必须为正整数！');
        return false;
    }
    if ($(formId + " input[name='description']").val().length == 0) {
        showTempErrorPop('规则描述不能为空！');
        return false;
    }
    var condIndex = $(formId).find("#addRuleCond").attr('condIndex');

    var isAnyTime = $(formId + " input[name='isAnyTime']:checked").val();
    var isAnd = $(formId + " input[name='isAnd']:checked").val();
    if (parseInt(ruleMode) == 0 && (isAnyTime === 'true') && parseInt(condIndex) <= 0) {
        showTempErrorPop("规则条件不能为空！");
        return false;
    }
    if (parseInt(ruleMode) == 0 && isAnyTime === 'false') {
        var minTime = $(formId + " input[name='minTime']").val();
        var maxTime = $(formId + " input[name='maxTime']").val();
        if ((!intPattern.test(minTime)) || (!intPattern.test(maxTime))) {
            showTempErrorPop("时间格式输入不正确");
            return false;
        }
        if (parseInt(minTime) > 23 || parseInt(maxTime) > 23) {
            showTempErrorPop("时间范围大于等于0小于24");
            return false;
        }
    }
    return true;
}

var closePop = function () {
    $("#ruleDetail").hide();
}

var closeRulePop = function () {
    $("#ruleDetail").hide();
}

var Select = {
    del: function (obj, e, isvalueInputId) {
        if ((e.keyCode || e.which || e.charCode) == 8) {
            var opt = obj.options[0];
            opt.text = opt.value = opt.value.substring(0, opt.value.length > 0 ? opt.value.length - 1 : 0);
            if (opt.text.length == 0) {
                $('#' + isvalueInputId).val('false');
            }
        }
    },
    write: function (obj, e, isvalueInputId) {
        if ((e.keyCode || e.which || e.charCode) == 8)return;
        var opt = obj.options[0];
        opt.selected = "selected";
        opt.text = opt.value += String.fromCharCode(e.charCode || e.which || e.keyCode);

        $('#' + isvalueInputId).val('true');
    },
    onchange: function (obj, e, isvalueInputId) {
        var opt = obj.options[0];
        if (opt.selected) {
            $('#' + isvalueInputId).val('true');
        } else {
            $('#' + isvalueInputId).val('false');
        }
    }
}
