    var asyncReq = function (options, onSuccess, onError) {

        options = options || {};
        onSuccess = onSuccess || function () {};
        onError = onError || function () {};

        options.wait = options.wait || 0;

        $.ajax({
            type: options.type || "GET",
            url: options.url,
            data: options.data || {},
            dataType: options.dataType || 'json',
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
