var setPieChart = function (targetid,options) {
    //风险大盘柱状图
    var option = {
        color:options.colorArr,
        title: {
            text: options.ratio,
            x: 'center',
            y: 'center',
            textStyle:{
                fontSize:14
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'right',
            y: 'center',
            itemWidth: 10,             // 图例图形宽度
            itemHeight: 10,            // 图例图形高度
            show:false,
            data: [{
                name:options.name,
                textStyle:{
                    fontSize: 20,
                    color: '#3398DB',
                    fontWeight:'bold'
                }
            }, options.count]
        },
        series: [
            {
                name: options.name,
                type: 'pie',
                minAngle: options.minAngle,
                radius: ['80%', '90%'],
                avoidLabelOverlap: false,
                clockwise: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {value: options.totalCount, name: options.name},
                    {value: options.count, name: options.count}
                ]
            }
        ]
    };


    var myChart = echarts.init(document.getElementById(targetid));
    myChart.setOption(option);
};

