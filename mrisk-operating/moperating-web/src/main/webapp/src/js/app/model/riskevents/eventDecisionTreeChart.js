//json格式的数据，包含样式
var setDecisionTree = function (treeStrInJson) {
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('decisionTree'), 'macarons');
    window.myChart = myChart;
    var option = {
        title: {
            text: '决策树',
            subtext: ''
        },
        toolbox: {
            show: false,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: false,
        roam: true,
        series: [
            {
                name: '树图',
                type: 'tree',
                orient: 'vertical',  // vertical horizontal
                direction: '', //方向反转，可选：'inverse'
                rootLocation: {x: 'center', y: 80}, // 根节点位置  {x: 100, y: 'center'}
                nodePadding: 100,
                layerPadding: 40,
                symbolSize: 16,
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            formatter: "{b}",
                            position: 'top',
                            textStyle: {
                                color: '#333333'
                            }
                        },
                        lineStyle: {
                            color: '#D9D9D9',
                            // shadowColor: '#000',
                            // shadowBlur: 3,
                            // shadowOffsetX: 3,
                            // shadowOffsetY: 5,
                            type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
                        }
                    },
                    emphasis: {
                        label: {
                            show: true
                        }
                    }
                },

                data: [
                    treeStrInJson
                ]
            }
        ]
    };

    myChart.setOption(option);
    myChart.on('click', eConsole);
}

function eConsole(param) {
   console.log(param)
}