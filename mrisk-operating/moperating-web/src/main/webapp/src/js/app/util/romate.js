/**
 * Created by lee on 16/9/12.
 *
 * http请求服务
 */
define(['jquery'],function($){

    var asyncReq = function (options, onSuccess, onError) {

        options = options || {};
        options.urlPath = options.urlPath || [];
        options.url = options.url || '';
        onSuccess = onSuccess || function () {};
        onError = onError || function () {};
        var url = options.url + options.urlPath;

        options.wait = options.wait || 0;

        $.ajax({
            type: options.type || "GET",
            url: url,
            data: options.data || {},
            crossDomain:  options.crossDomain || false,
            dataType: options.dataType || 'json',
            timeout: options.timeout || 4000,
                success: function (data) {
                onSuccess(data);
            },
            error: function (data) {
                onError(data);
            },
            beforeSend: function () {
                if(options.wait){
                    //loading开始
                }
            },
            complete: function () {
                if(options.wait){
                    //loading结束
                }
            }
        });
    };

    var asyncReqJsonp = function (options, onSuccess, onError) {

        options = options || {};
        options.urlPath = options.urlPath || [];
        onSuccess = onSuccess || function () {};
        onError = onError || function () {};
        var url = options.url + (options.urlPath ? ("/" + options.urlPath.join("/")) : "");

        options.wait = options.wait || 0;

        $.ajax({
            type: options.type || "GET",
            url: url,
            data: options.data || {},
            dataType: 'jsonp',
            jsonp: options.jsonpCallback || 'callback',//服务端用于接收callback调用的function名的参数
            timeout: options.timeout || 3000,
            success: function (data) {
                onSuccess(data);
            },
            error: function (data) {
                onError(data);
            },
            beforeSend: function () {
                if(options.wait){
                    //loading开始
                }
            },
            complete: function () {
                if(options.wait){
                    //loading结束
                }
            }
        });
    };

    return {
        asyncReq : asyncReq,
        asyncReqJsonp : asyncReqJsonp
    }
});

