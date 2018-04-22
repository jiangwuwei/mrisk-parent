/**
 * Created by liyi8 on 2017/2/27.
 */
$(function () {

    $(".radio").on("click", function () {
        if ($(this).val() == 1) { //use sql
            $(this).closest(".tablewrapper_sub").find(".template").hide();
            $(this).closest(".tablewrapper_sub").find(".quotaContent").show();
            return;
        }
        //use template
        var templateId = $(this).closest(".tablewrapper_sub").find(".quotaTemplateId option:selected").val();
        if (templateId == 1) {
            $(this).closest(".tablewrapper_sub").find(".computeType").show();
            $(this).closest(".tablewrapper_sub").find(".computeField").hide();
        } else if (templateId == 2) {
            $(this).closest(".tablewrapper_sub").find(".computeType").hide();
            $(this).closest(".tablewrapper_sub").find(".computeField").show();
        } else if( templateId == 3){
            $(this).closest(".tablewrapper_sub").find(".computeType").hide();
            $(this).closest(".tablewrapper_sub").find(".computeField").hide();
        }
        $(this).closest(".tablewrapper_sub").find(".template").show();
        $(this).closest(".tablewrapper_sub").find(".quotaContent").hide();
    });

    //根据模板调整可选项
    $(".quotaTemplateId").on("change", function () {
        var templateId = $(this).find("option:selected").val();
        var self = $(this);
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath+"/quotaTemplate/getTemplateById.do",
            data: {'id': templateId},
            dataType: 'json',
            error: function (request) {
                showTempErrorPop("Connection error");
            },
            success: function (data) {
                var quotaContent = JSON.parse(data.quotaContent);
                if (quotaContent.computeType.show) {
                    self.closest(".tablewrapper_sub").find(".computeType").show();
                }else{
                    self.closest(".tablewrapper_sub").find(".computeType").hide();
                }
                if (quotaContent.computeField.show) {
                    self.closest(".tablewrapper_sub").find(".computeField").show();
                }else{
                    self.closest(".tablewrapper_sub").find(".computeField").hide();
                }
                var templateFunction = (typeof quotaContent.function == 'undefined')?'':quotaContent.function;
                var limit = (typeof quotaContent.limit == 'undefined')?'':quotaContent.limit;
                var offset = (typeof quotaContent.offset == 'undefined')?'':quotaContent.offset;
                var orderField = (typeof quotaContent.orderField == 'undefined')?'':quotaContent.orderField;
                var order = (typeof quotaContent.order == 'undefined')?'':quotaContent.order;
                self.closest(".tablewrapper_sub").find("input[name='function']").val(templateFunction);
                self.closest(".tablewrapper_sub").find("input[name='limit']").val(limit);
                self.closest(".tablewrapper_sub").find("input[name='offset']").val(offset);
                self.closest(".tablewrapper_sub").find("input[name='orderField']").val(orderField);
                self.closest(".tablewrapper_sub").find("input[name='order']").val(order);

                self.closest(".tablewrapper_sub").find("select[name='quotaDataType'] option").each(function () {
                    if($(this).val() == data.quotaDataType){
                        $(this)[0].selected = true;
                    }
                });
                self.closest(".tablewrapper_sub").find("select[name='sourceType'] option").each(function () {
                    if($(this).val() == data.sourceType){
                        $(this)[0].selected = true;
                    }
                });
               self.closest(".tablewrapper_sub").find("select[name='accessSource'] option").each(function () {
                   if($(this).val() == data.accessSource){
                       $(this)[0].selected = true;
                   }
               });

            }
        });
    });

    $(".timeShardType").on("change", function () {
        var timeShardType = $(this).find("option:selected").val();
        if (timeShardType == 1) {
            $(this).closest(".tablewrapper_sub").find(".timeShardValue").show();
        } else if (timeShardType == 2) {
            $(this).closest(".tablewrapper_sub").find(".timeShardValue").hide();
        }
    });

    $(".quotaDetail").on("click", function () {
        $(this).closest(".tab").find('.tablewrapper_sub').slideToggle();
    });

    $(".to_add_quota_btn").on("click", function () {
        if (!$(this).parents('td').find(".policy-toggle-icon").hasClass('open')) {
            $(this).parents('td').find(".policy-toggle-icon").addClass('open');
            $(this).parents('td').find(".tablewrapper").slideToggle();
        }
        $(this).parents('td').find('.tab.addClass').slideToggle();
    });

    $(".addClass_close_btn").on("click", function () {
        $(this).closest(".tab.addClass").slideToggle();
    });

    $(".close_btn").on("click", function () {
        $(this).closest(".tablewrapper_sub").slideToggle();
    });

})

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

function del_rule_cond($this) {
    $this.closest('.s_one').remove();
}

