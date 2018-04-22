/**
 * Created by liyi8 on 2017/3/17.
 */

function getEventToday(){
    $.ajax({
        cache: true,
        type: "POST",
        url: $("#contextPath").val()+"/riskFraud/eventQuantityToday.do",
        dataType:'json',
        data: {
            platform: ''
        },
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
           for(var i=0; i<data.length; i++){
               var countId = "#count"+data[i].decisionCode;
               var ratioId = "#ratio"+data[i].decisionCode;
               $(countId).text(data[i].quantity);
               if(data[i].decisionCode != 4){
                   $(ratioId).text(data[i].ratio+"%");
               }
           }
        }
    });
}

function getEventData(data) {
    $.ajax({
        cache: true,
        type: "POST",
        url: $("#contextPath").val()+'/riskFraud/eventQuantity.do',
        dataType: 'json',
        data: {
            queryDate: data.queryDate,
            scene: data.scene,
            platform: data.platform
        },
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            getChart(data);
            $("#7dayRefuseTotalCount").text(data.rateMap.refuseTotalCount);
            $("#7dayVerifyTotalCount").text(data.rateMap.verifyTotalCount);
            $("#7dayPassTotalCount").text(data.rateMap.passTotalCount);
            $("#7dayTotalCount").text(data.rateMap.totalCount);
            getPie('canvasScoreOne', {
                name: '拒绝',
                totalCount: data.rateMap.totalCount-data.rateMap.refuseTotalCount,
                count: data.rateMap.refuseTotalCount,
                ratio: data.rateMap.refuseRatio+"%",
                colorArr: ['#cccccc','#FF6C5C'],
                minAngle: '10'
            });
            getPie('canvasScoreTwo', {
                name: '人工审核',
                totalCount: data.rateMap.totalCount-data.rateMap.verifyTotalCount,
                count: data.rateMap.verifyTotalCount,
                ratio: data.rateMap.verifyRatio+"%",
                colorArr: ['#cccccc','#F8D436'],
                minAngle: '10'
            });
            getPie('canvasScoreThree', {
                name: '通过',
                totalCount: data.rateMap.totalCount-data.rateMap.passTotalCount,
                count: data.rateMap.passTotalCount,
                ratio: data.rateMap.passRatio+"%",
                colorArr: [ '#cccccc','#A7DB65'],
                minAngle: '10'
            });
            getPie('canvasScoreFour', {
                name: 'ALL',
                totalCount: data.rateMap.totalCount,
                count: 0,
                ratio: 'ALL',
                colorArr: ['#cccccc','#980000'],
                minAngle: '0'
            });
        }
    });
}

function getSceneConfig() {
    $.ajax({
        cache: true,
        type: "POST",
        url: $("#contextPath").val()+"/sceneConfig/sceneDefinitionVoList.do",
        dataType: 'json',
        data: {
            sceneNo: ''
        },
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            getSceneList(data);
        }
    });
}

function getPie(targetId, data) {
    var options = {
        name: data.name,
        totalCount: data.totalCount,
        count: data.count,
        ratio: data.ratio,
        colorArr: data.colorArr,
        minAngle: data.minAngle
    }
    setPieChart(targetId, options);
}
function getChart(data) {
    var options = {
        verifyCountArr: data.verifyCountArr,
        passCountArr: data.passCountArr,
        refuseCountArr: data.refuseCountArr,
        dateArr: data.dateArr
    }
    setChart('eventStatistic', options);
}

function getSceneList(sceneList) {
    var htmlStr = "<option value='' >全部</option>";
    for (var i = 0; i < sceneList.length; i++) {
        htmlStr += "<option value='" + sceneList[i].sceneNo + "01'>" + sceneList[i].sceneName + "(" + sceneList[i].sceneNo + ")</option>";
    }
    $("#sceneNo").html(htmlStr);
}

function getNow() {
    var mydate = new Date();
    var month = mydate.getMonth() + 1;
    var date = mydate.getDate();
    var str = mydate.getFullYear() + "-" + (month < 10 ? ("0" + month) : month) + "-" + (date < 10 ? ("0" + date) : date);
    $("#queryDate").val(str);
}