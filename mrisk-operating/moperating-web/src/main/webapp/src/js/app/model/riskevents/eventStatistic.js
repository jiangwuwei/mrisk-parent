var setChart = function (targetid, options) {
    option = {
        color: ['#A7DB65','#FF6C5C', '#F8D436'],
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
            }
        },
        calculable: true,
        legend: {
            data: [ '通过','拒绝', '人工审核']
        },
        xAxis: [
            {
                type: 'category',
                data: options.dateArr
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '',
                nameTextStyle: {
                    color: '#FF0000'
                },
                axisLine: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: '通过',
                type: 'bar',
                stack: 'count',
                barMaxWidth: '30%',
                barMinHeight: '5%',
                data: options.passCountArr
            },
            {
                name: '拒绝',
                type: 'bar',
                stack: 'count',
                barMaxWidth: '30%',
                barMinHeight: '5%',
                data: options.refuseCountArr
            },
            {
                name: '人工审核',
                type: 'bar',
                stack: 'count',
                barMaxWidth: '30%',
                barMinHeight: '5%',
                data: options.verifyCountArr
            }
        ]
    };

    var myChart = echarts.init(document.getElementById(targetid));
    myChart.setOption(option);
}


