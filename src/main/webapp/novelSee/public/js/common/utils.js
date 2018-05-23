define(function (require, exports, module) {
    var $ = require('jquery');
    var service= {
        doGetAsync : function(url, parameters, callBack) {
            var data = {
                async : true,
                url : url,
                type : "GET",
                contentType : "application/x-www-form-urlencoded;charset=UTF-8",
                dataType : "json"
            };
            if(parameters){
                var pValue = $.toJSON(parameters);
                data.data = pValue;
            }
            if (callBack) {
                data.complete = callBack;
            }
            return $.ajax(data);
        },
        doPostAsync : function(url, parameters, callBack) {
            var data = {
                async : true,
                url : url,
                type : "Post",
                contentType : "application/x-www-form-urlencoded;charset=UTF-8",
                dataType : "json"
            };
            if(parameters){
                var pValue = $.toJSON(parameters);
                data.data = pValue;
            }
            if (callBack) {
                data.complete = callBack
            }
            return $.ajax(data);
        },
        doPost : function(url, parameters, callBack) {
            var data = {
                async : false,
                url : url,
                type : "Post",
                contentType : "application/x-www-form-urlencoded;charset=UTF-8",
                dataType : "json"
            };
            if(parameters){
                var pValue = $.toJSON(parameters);
                data.data = pValue;
            }
            if (callBack) {
                data.complete = callBack
            }
            return $.ajax(data);
        },
        doGet : function(url, parameters, callBack) {
            var data = {
                async : false,
                url : url,
                type : "GET",
                contentType : "application/x-www-form-urlencoded;charset=UTF-8",
                dataType : "json"
            };
            if(parameters){
                var pValue = $.toJSON(parameters);
                data.data = pValue;
            }
            if (callBack) {
                data.complete = callBack
            }
            return $.ajax(data);
        },
    };
    function isNullOrEmpty(str) {
        return str == undefined || str == null || str == "" || str.length == 0;
    };
    module.exports = {
        service:service,
        isNullOrEmpty : isNullOrEmpty
    }
})