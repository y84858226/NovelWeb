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
                        $scope.$apply(function () {
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
                        $scope.$apply(function () {
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
                        $scope.$apply(function () {
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
            var classify_XHQH = base64._encode("玄幻奇幻");
            this.getClassifyBooks("xhqhBooksController",classify_XHQH,"$scope.xhqhImageBooks = dealWithResult.slice(0,1);$scope.xhqhBooks = dealWithResult.slice(1,5);")
            var classify_WXXX = base64._encode("武侠仙侠");
            this.getClassifyBooks("wxxxBooksController",classify_WXXX,"$scope.wxxxImageBooks = dealWithResult.slice(0,1);$scope.wxxxBooks = dealWithResult.slice(1,5);")
            var classify_DSYN = base64._encode("都市异能");
            this.getClassifyBooks("dsynBooksController",classify_DSYN,"$scope.dsynImageBooks = dealWithResult.slice(0,1);$scope.dsynBooks = dealWithResult.slice(1,5);")
        },
        /**
         * 获取分类书籍通用方法
         */
        getClassifyBooks : function (controller,classifyName,callback) {
            _that.app.controller(controller, function($scope, $http) {
                utils.service.doPostAsync('../getClassifyBooks',classifyName,function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$apply(function () {
                            var dealWithResult = result.responseJSON;
                            $.each(dealWithResult, function(key, val){
                                dealWithResult[key].description = val.description.replace(/<br>/g,"")
                            });
                            eval(callback);
                        });
                    }
                })
            });
        }
    }
    controller.initPage();
})

