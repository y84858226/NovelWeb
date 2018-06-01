define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('index', []);
            this.initRecommendVal();
            this.initRecommendBooks();
            this.initHotBooks();
            this.initIndexClassifyBooks();
            this.bindEvent();
        },
        /**
         * 获取搜索框推荐值
         */
        initRecommendVal : function () {
            var _that = this;
            _that.app.controller('searchController', function($scope, $http) {
                utils.service.doGetAsync('../getRecommendVal','',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON) && !utils.isNullOrEmpty(result.responseJSON.recommendVal)){
                        //angular强制刷新
                        $scope.$applyAsync(function () {
                            $scope.recommendVal = result.responseJSON.recommendVal;
                        });
                    }
                })
            });
        },
        /**
         * 获取推荐书籍
         */
        initRecommendBooks : function () {
            var _that = this;
            _that.app.controller('recommondBooksController', function($scope, $http) {
                utils.service.doPostAsync('../getRecommendBooks','',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $.each(dealWithResult, function(key, val){
                                dealWithResult[key].description = val.description.replace(/<br>/g,"")
                            });
                            $scope.recommendImageBooks = dealWithResult.slice(0,3);
                            $scope.recommendBooks = dealWithResult.slice(3,8);
                        });
                    }
                })
            });
        },
        /**
         * 获取热门书籍
         */
        initHotBooks : function () {
            var _that = this;
            _that.app.controller('hotBookController', function($scope, $http) {
                utils.service.doPostAsync('../getHotBooks','',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $.each(dealWithResult, function(key, val){
                                dealWithResult[key].description = val.description.replace(/<br>/g,"")
                            });
                            $scope.hotImageBooks = dealWithResult.slice(0,1);
                            $scope.hotBooks = dealWithResult.slice(1,5);
                        });
                    }
                })
            });
        },
        /**
         * 获取首页分类书籍
         */
        initIndexClassifyBooks : function () {
            var classify_XHQH = "玄幻小说";
            this.getClassifyBooks("xhqhBooksController",classify_XHQH,"$scope.xhqhImageBooks = dealWithResult.slice(0,1);$scope.xhqhBooks = dealWithResult.slice(1,5);")
            var classify_WXXX = "修真小说";
            this.getClassifyBooks("wxxxBooksController",classify_WXXX,"$scope.wxxxImageBooks = dealWithResult.slice(0,1);$scope.wxxxBooks = dealWithResult.slice(1,5);")
            var classify_DSYN = "都市小说";
            this.getClassifyBooks("dsynBooksController",classify_DSYN,"$scope.dsynImageBooks = dealWithResult.slice(0,1);$scope.dsynBooks = dealWithResult.slice(1,5);")
        },
        /**
         * 获取分类书籍通用方法
         */
        getClassifyBooks : function (controller,classifyName,callback) {
            var _that = this;
            _that.app.controller(controller, function($scope, $http) {
                utils.service.doPost('../getClassifyBooks',classifyName,function (result) {
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
            //更多点击事件
            $(".more").click(function (e) {
                e.stopPropagation();
                _that._moreBtnClickEvent(e,_that);
            });
            //小说点击事件
            $(".section").on("click","a",function (e) {
                _that._bookDetailClickEvent(e,_that);
            });
            //分类点击事件
            $(".top_class a").click(function (e) {
                _that._moreBtnClickEvent(e,_that);
            });
        },
        _moreBtnClickEvent : function (e,_that) {
            var target = e.target;
            var param = $(target).parent('li').attr("classify");
            var src = "/novel/novelSee/more.html?classifyName=" + param;
            window.location.href = src;
        },
        _bookDetailClickEvent : function (e,_that) {
            var target = e.target;
            var id = $(target).parents('li').find('h4')[0].attr("ng-value");
            var src = "/novel/novelSee/bookdetail.html?id=" + id;
            window.location.href = src;
        }
    }
    controller.initPage();
})

