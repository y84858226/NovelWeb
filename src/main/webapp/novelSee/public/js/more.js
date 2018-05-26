define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('more', []);
            var classifyName = this.getQueryString("classifyName");
            this.initClassifyBooks(classifyName);
            this.bindEvent();
        },
        /**
         * 获取url参数
         * @param name
         * @returns {null}
         */
        getQueryString : function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var url = decodeURI(decodeURI(window.location.search))
            var r = url.substr(1).match(reg);
            if (r != null)
                return unescape(r[2]);
            return null;
         },
        /**
         * 获取首页分类书籍
         */
        initClassifyBooks : function (classifyName) {
            var params = new Object();
            params.classifyName = classifyName;
            params.start = 1;
            params.end = 50;
            this.getClassifyBooks("moreBooksController",JSON.stringify(params),"$scope.moreBooks = dealWithResult;")
        },
        /**
         * 获取分类书籍通用方法
         */
        getClassifyBooks : function (controller,params,callback) {
            var _that = this;
            _that.app.controller(controller, function($scope, $http) {
                utils.service.doPost('../getClassifyBooksByPage',params,function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $.each(dealWithResult, function(key, val){
                                dealWithResult[key].description = val.description.replace(/<br>/g,"")
                            });
                            eval(callback);
                        });
                    }
                })
            });
        },
        /**
         * 绑定事件
         */
        bindEvent : function () {
            var _that = this;
            //小说点击事件
            $(".section").on("click","a",function (e) {
                _that._bookDetailClickEvent(e,_that);
            });
        },
        _bookDetailClickEvent : function (e,_that) {
            var target = e.target;
            var bookname = $(target).find('h4').prevObject["0"].innerText;
            var src = "/novel/novelSee/bookdetail.html?bookname=" + bookname;
            window.location.href = src;
        }

    }
    controller.initPage();
})

