$(function () {
    $(".pop-tip").on("click", function () {
        $(this).closest(".pop-wrapper").hide();
    });
    $(".pop-black").on("click", function () {
        $(this).closest(".pop-wrapper").hide();
    });

    $(".pop-close").on("click", function () {
        $(this).closest(".pop-wrapper").hide();
    });
});

//jumpToUrl为跳转ajax成功之后的跳转url，默认为reload
function submitAjaxForm(form_exp, url, jumpToUrl) {

    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        dataType: 'json',
        data: $(form_exp).serialize(),
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        error: function (request) {
            showTempErrorPop("Connection error");
        },
        success: function (data) {
            if (data.res.code < 0) {
                showTempErrorPop(data.res.msg);
                return;
            } else {
                showTempSuccessPop(data.res.msg);
                if (typeof(jumpToUrl) != 'undefined') {
                    window.location = jumpToUrl;
                }
                if (jumpToUrl === 'reload') {
                    window.location.reload();
                }
            }
        }
    });
}

//on_suss_func为成功之后要执行的函数
function submitAjaxForm2(form_exp, url, on_suss_func, isAlertMsg) {

    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        dataType: 'json',
        data: $(form_exp).serialize(),
        xhrFields: {
            withCredentials: true
        },
        crossDomain: true,
        error: function (request) {
            showTempErrorPop("Connection error");
        },
        success: function (data) {
            if (data.res.code < 0) {
                showTempErrorPop(data.res.msg);
            } else {
                if (typeof(isAlertMsg) == 'undefined') {
                    showTempSuccessPop(data.res.msg);
                }
                on_suss_func(data);
            }
        }
    });
}

function delBySceneNo(sceneNo, delUrl, jumpToUrl) {
    $.ajax({
        cache: true,
        type: "POST",
        url: delUrl,
        data: {'sceneNo': sceneNo},
        dataType: 'json',
        error: function (request) {
            showTempErrorPop("Connection error");
        },
        success: function (data) {
            console.log(data);
            if (data.res.code < 0) {
                showTempErrorPop(data.res.msg);
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

function delById(id, delUrl, jumpToUrl) {
    $.ajax({
        cache: true,
        type: "POST",
        url: delUrl,
        data: {'id': id},
        dataType: 'json',
        error: function (request) {
            showTempErrorPop("Connection error");
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

var submitForm = function (options, on_suss_func, on_error_func) {
    if (typeof options.formId == 'undefined') {
        showTempErrorPop("formid为必传参数");
        return;
    }
    if (typeof options.url == 'undefined') {
        showTempErrorPop("url为必传参数");
        return;
    }
    on_error_func = on_error_func || function () {
            $("#onErrorPop").show();
        };
    on_suss_func = on_suss_func || function () {
            $("#onSuccessPop").show();
        };

    $.ajax({
        cache: true,
        type: "POST",
        url: options.url,
        dataType: 'json',
        data: $("#" + options.formId).serialize(),
        error: function (request) {
            on_error_func();
        },
        success: function (data) {
            if (data.res.code < 0) {
                on_error_func();
            } else {
                on_suss_func(data);
            }
        }
    });
}

function updateStatusById(id, updateUrl, toStatus, $theBtn, on_suss_func, isAlertMsg) {
    $.ajax({
        cache: true,
        type: "POST",
        url: updateUrl,
        data: {
            'id': id,
            'toStatus': toStatus
        },
        dataType: 'json',
        error: function (request) {
            showTempErrorPop("Connection error");
        },
        success: function (data) {
            if (data.res.code < 0) {
                showTempErrorPop(data.res.msg);
            } else {
                if (typeof(isAlertMsg) == 'undefined') {
                    showTempErrorPop(data.res.msg);
                }
                on_suss_func();
            }
        }
    });
}

function updateStatus(options, on_suss_func, on_error_func) {
    if (typeof options.id == 'undefined') {
        showTempErrorPop("id为必传参数!");
        return;
    }
    if (typeof options.updateStatusUrl == 'undefined') {
        showTempErrorPop("updateUrl为必传参数!");
        return;
    }
    if (typeof options.toStatus == 'undefined') {
        showTempErrorPop("toStatus为必传参数!");
        return;
    }
    on_error_func = on_error_func || function () {
            $("#onErrorPop").show();
        };
    on_suss_func = on_suss_func || function () {
            $("#onSuccessPop").show();
        };
    $.ajax({
        cache: true,
        type: "POST",
        url: options.updateStatusUrl,
        data: {
            'id': options.id,
            'toStatus': options.toStatus
        },
        dataType: 'json',
        error: function (request) {
            on_error_func();
        },
        success: function (data) {
            on_suss_func(data);
        }
    });
}
var showTempErrorPop = function(errorMsg){
    $("#tempErrorMsg").text(errorMsg);
    $("#tempErrorPop").show();
}

var showTempSuccessPop = function(successMsg){
    $("#tempSuccessMsg").text(successMsg);
    $("#tempSuccessPop").show();
}