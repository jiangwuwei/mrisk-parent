/**
 * Created by lee on 16/9/12.
 *
 * 工具类
 */

define(['jquery'], function () {

    var utils = {

        setCookie: function (name, value) {
            if (typeof value == 'object') value = JSON.stringify(value);
            var exp = new Date();    //new Date("December 31, 9998");
            exp.setTime(exp.getTime() + 24 * 60 * 60 * 1000);//cookie 保存10分钟
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
        },

        getCookie: function (name) {
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) return unescape(arr[2]);
            return "";
        },

        removeCookie: function (name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = this.getCookie(name);
            if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/";
        },

        clearCookie: function () {
            var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
            if (keys) {
                for (var i = keys.length; i--;) {
                    delCookie(keys[i]);
                }
            }
        },

        setSessionStorage: function (name, value) {
            if (typeof value == 'object') value = JSON.stringify(value);
            window.sessionStorage.setItem(name, value);
        },

        getSessionStorage: function (name) {
            return window.sessionStorage.getItem(name);
        },

        removeSessionStorage: function (name) {
            window.sessionStorage.removeItem(name);
        },

        setLocalStorage: function (name, value) {
            if (typeof value == 'object') value = JSON.stringify(value);
            window.localStorage.setItem(name, value);
        },

        getLocalStorage: function (name) {
            return window.localStorage.getItem(name);
        },

        removeLocalStorage: function (name) {
            window.localStorage.removeItem(name);
        },

        getParamFromUrl: function (key) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            return r == null ? null : r[2];
        },

        redirect: function (url, history) {
            if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
                var referLink = document.createElement('a');
                referLink.href = url;
                document.body.appendChild(referLink);
                referLink.click();
            } else {
                location.href = url;
            }
        },

        kseparator: function (num) {
            if ((typeof(num) == "undefined") || (isNaN(num))) {
                return 0;
            }
            // if(typeof num == 'string') num= parseInt(num);
            var source = String(num).split(".");//按小数点分成2部分
            source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)', 'ig'), "$1,");
            return source.join(".");//再将小数部分合并进来
        },

    };

    return utils;


});

