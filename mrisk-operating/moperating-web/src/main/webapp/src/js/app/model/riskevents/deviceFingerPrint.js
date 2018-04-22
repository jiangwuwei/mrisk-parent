var setChart = function (targetid, options) {
    var option = {
        color: ['#93c47d', '#3398DB'],
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
            data: ['获取成功数', '获取失败数']
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
                name: '获取成功数',
                type: 'bar',
                stack: 'count',
                barMaxWidth: '30%',
                barMinHeight: '5%',
                data: options.successCount
            },
            {
                name: '获取失败数',
                type: 'bar',
                stack: 'count',
                barMaxWidth: '30%',
                barMinHeight: '5%',
                data: options.failedCount
            }
        ]
    };

    var myChart = echarts.init(document.getElementById(targetid));
    myChart.setOption(option);
}