define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('more', []);
            this.classifyName = this.getQueryString("classifyName");
            this.initClassifyBooks(this.classifyName);
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
            params.end = 20;
            this.getClassifyBooks("moreBooksController",JSON.stringify(params),"$scope.moreBooks = dealWithResult;")
        },
        /**
         * 获取分类书籍通用方法
         */
        getClassifyBooks : function (controller,params,callback) {
            var _that = this;
            if(!_that.booksCtrl){
                _that.booksCtrl = _that.app.controller(controller, function($scope, $http) {
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
            }else{
                utils.service.doPost('../getClassifyBooksByPage',params,function (result) {
                    var appElement = document.querySelector('[ng-controller=moreBooksController]');
                    var $scope = angular.element(appElement).scope();
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $.each(dealWithResult, function(key, val){
                                dealWithResult[key].description = val.description.replace(/<br>/g,"")
                            });

                            $scope.moreBooks = dealWithResult;
                        });
                    }
                })
            }

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
            $(".more").on("click",function (e) {
                e.stopPropagation();
                _that._moreBooksBtnClick(e,_that);
            });
        },
        _bookDetailClickEvent : function (e,_that) {
            var target = e.target;
            var id = $($(target).parents('li').find('h4')[0]).attr("ng-value");
            var src = "/bookdetail.html?id=" + id;
            window.location.href = src;
        },
        _moreBooksBtnClick : function (e,_that) {
            var target = e.target;
            var index = $(target).parents('li').attr("clickNum");
            var params = new Object();
            params.classifyName = this.classifyName;
            params.start = 1;
            params.end = index * 20;
            this.getClassifyBooks("moreBooksController",JSON.stringify(params),"$scope.moreBooks = dealWithResult;")
            $(target).parents('li').attr('clickNum',(index+1))
        }

    }
    controller.initPage();
})

