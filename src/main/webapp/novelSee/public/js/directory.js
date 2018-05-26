define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('directory', []);
            this.bookName = this.getQueryString("bookname");
            this.initBookDirectory(this.bookName);
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
         * 获取书籍目录
         */
        initBookDirectory : function (bookName) {
            var _that = this;
            _that.app.controller("directoryCtrl", function($scope, $http) {
                utils.service.doPost('../getBookDirectory',bookName,function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $scope.bookDirectory = dealWithResult;
                            $scope.bookName = _that.bookName;
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
            //目录点击事件
            $(".section").on("click","a",function (e) {
                _that._bookDetailClickEvent(e,_that);
            });
        },
        _bookDetailClickEvent : function (e,_that) {

        }
    }
    controller.initPage();
})

