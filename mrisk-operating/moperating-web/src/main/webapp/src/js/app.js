/**
 * Created by lee on 16/9/12.
 *
 * js引用配置
 */

require.config({
    //context为operating,如果修改也需修改此处
    baseUrl : 'operating/src/js/',
    paths : {
        jquery : 'lib/jquery-3.1.0',

        // config : 'app/common/config-test',     //项目配置文件
        config : 'app/common/config-pro',     //项目配置文件
        dictionary :'app/common/dictionary',   //字典值文件
        urls : 'app/common/interface_url',    //服务接口地址
        utils : 'app/util/utils',             //工具类
        romate : 'app/util/romate',             //http请求工具类
        checker : 'app/common/checker',      //检测
        jqueryPage : 'app/component/jquery.page',
        echarts2Config: 'app/component/echarts2/config',
        //component
        echarts3 : 'app/component/echarts3/echarts',
        echarts2 : 'app/component/echarts2/echarts',

        //Ctrl
        deviceFingerPrintCtrl: 'app/controller/deviceFingerPrintCtrl',
        //page js
        deviceFingerPrintChart: 'app/model/riskevents/deviceFingerPrint', //设备获取率图

    },

    shim : {
        'underscore' : {
            exports : '_'
        },
        'awardRotate' :{
            deps : ['jquery']
        },
        "jqueryPage" : {
            deps : ['jquery']
        }
    }
});


