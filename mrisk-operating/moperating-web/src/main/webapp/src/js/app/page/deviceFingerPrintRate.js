$(function () {
    $("#search_btn").on('click', function () {
        var queryDate = $("#queryDate").val();
        var scene = $("#sceneNo option:selected").val();
        var platform = $("#platform option:selected").val();
        getDeviceData({
            queryDate: queryDate,
            scene: scene,
            platform: platform
        });
    });
    getDeviceData({
        queryDate: null,
        scene: null,
        platform: null
    });
    getSceneConfig();
    getNow();

})

var getDeviceData = function (options) {
    var params = {
        queryDate: options.queryDate,
        scene: options.scene,
        platform: options.platform
    };
    asyncReq({
            url: $("#basePath").val() + '/riskFraud/deviceQuantity.do',
           data: params
        }
        , function (data) {
            getChart(data);
            getRightList(data.list);
        }, function (data) {
            showTempErrorPop("Connection error");
        });
}

var getSceneConfig = function () {
    asyncReq({url: $("#basePath").val() + '/sceneConfig/sceneDefinitionVoList.do', sceneNo: ''}
        , function (data) {
            getSceneList(data);
        }, function (data) {
            showTempErrorPop("Connection error");
        });
}

var getChart = function (data) {
    var options = {
        failedCount: data.failCountArr,
        successCount: data.successCountArr,
        dateArr: data.dateArr
    }
    setChart('deviceFingerPrint', options);
}
var getRightList = function getRightList(list) {
    $("#tableList").empty();
    var htmlStr = "";
    for (var i = list.length - 1; i > 0; i--) {
        htmlStr += "<tr>";
        htmlStr += "<td>" + list[i].dateStr + "</td>";
        htmlStr += "<td>" + (list[i].totalCount - list[i].successCount) + "/" + list[i].totalCount + "</td>";
        htmlStr += "<td>" + list[i].successRatio + "%</td>";
        htmlStr += "</tr>";
    }
    $("#tableList").html(htmlStr);
}

var getSceneList = function (sceneList) {
    var htmlStr = "<option value='' >全部</option>";
    for (var i = 0; i < sceneList.length; i++) {
        htmlStr += "<option value='" + sceneList[i].sceneNo + "01'>" + sceneList[i].sceneName + "(" + sceneList[i].sceneNo + ")</option>";
    }
    $("#sceneNo").html(htmlStr);
}

var getNow = function () {
    var mydate = new Date();
    var month = mydate.getMonth() + 1;
    var date = mydate.getDate();
    var str = mydate.getFullYear() + "-" + (month < 10 ? ("0" + month) : month) + "-" + (date < 10 ? ("0" + date) : date);
    $("#queryDate").val(str);
}