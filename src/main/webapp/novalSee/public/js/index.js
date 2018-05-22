define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils')
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('index', []);
            this.initRecommendVal();
            this.initRecommendBooks();
            this.initHotBooks();
        },
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
        }
    }
    controller.initPage();
})

