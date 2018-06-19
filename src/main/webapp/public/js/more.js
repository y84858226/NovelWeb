define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('more', []).config(function($compileProvider){
                //注:有些版本的angularjs为$compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|javascript):/);,两种都试一下可以即可
                $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|itms\-apps|ftp|mailto|file|javascript):/);
            });
            this.classifyName = this.getQueryString("classifyName");
            if(!utils.isNullOrEmpty(this.classifyName)){
                this.initClassifyBooks(this.classifyName);
                $(".cur").removeClass("cur");
                $("[classify="+this.classifyName+"]").find("a").addClass("cur");
                this.getClassifyBooksCount();
            }
            this.searchStr = this.getQueryString("searchStr");
            if(!utils.isNullOrEmpty(this.searchStr)){
                this.initSearchBooks(this.searchStr);
            }
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
            params.start = 0;
            params.end = 9;
            this.getClassifyBooks("moreBooksController",JSON.stringify(params),"$scope.moreBooks = dealWithResult;")
        },
        getClassifyBooksCount : function(){
            var _that = this;
            _that.app.controller("paginationController", function($scope, $http,$sce) {
                utils.service.doPost('../getClassifyBooksCount',_that.classifyName,function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            _that.booksCount = result.responseJSON;
                            _that.initPagination();                                        
                            $scope.allPage = _that.booksCount;
                        });
                    }
                })
            });
        },
        /**
         * 初始化分页
         */
        initPagination : function(){
            var _that = this;
            $("#Pagination").pagination(_that.booksCount,{
                jump: false, //跳转到指定页数
                jumpIptCls: 'jump-ipt', //文本框内容
                jumpBtnCls: 'page-btn', //跳转按钮
                jumpBtn: '跳转', //跳转按钮文本
                callback: function (index) {
                    pageIndex = index + 1;
                    pageSize = 10;
                    var params = {};         ;
                    params.classifyName = _that.classifyName;
                    params.start = (pageIndex - 1) * 10;
                    params.end = pageIndex * 10 - 1;
                    _that.getClassifyBooks("moreBooksController",JSON.stringify(params),"$scope.moreBooks = dealWithResult;")
                }
            });
        },
        /**
         * 获取搜索的书籍
         */
        initSearchBooks : function(searchStr){
        	var _that = this;
            _that.booksCtrl = _that.app.controller("moreBooksController", function($scope, $http,$sce) {
                    utils.service.doPost('../searchIndex',searchStr,function (result) {
                        if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                            $scope.$applyAsync(function () {
                                var dealWithResult = result.responseJSON;
                                $scope.moreBooks = dealWithResult;
                                $scope.html = function(n){
                                    return $sce.trustAsHtml(n);  
                                }
                                $('.item_more').attr('hidden',true)
                            });
                        }
                    })
                });
        },
        /**
         * 获取分类书籍通用方法
         */
        getClassifyBooks : function (controller,params,callback) {
            var _that = this;
            if(!_that.booksCtrl){
                _that.booksCtrl = _that.app.controller(controller, function($scope, $http, $sce) {
                	console.log($sce)
                    utils.service.doPost('../getClassifyBooksByPage',params,function (result) {
                        if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                            $scope.$applyAsync(function () {
                                var dealWithResult = result.responseJSON;
                                eval(callback);
                                $scope.html = function(n){
                                    _that.$sce = $sce;
                                    return _that.$sce.trustAsHtml(n);  
                                }
                            });
                        }
                    })
                });
            }else{
            	var appElement = document.querySelector('[ng-controller=moreBooksController]');
            	var $scope = angular.element(appElement).scope();
                utils.service.doPost('../getClassifyBooksByPage',params,function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function ($sce) {
                            var dealWithResult = result.responseJSON;
                            $scope.moreBooks = dealWithResult;
                            $scope.html = function(n){
                                return _that.$sce.trustAsHtml(n);  
                            }
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
            //分类点击事件
            $(".top_menu a").click(function (e) {
                _that._moreBtnClickEvent(e,_that);
            });
        },
        _bookDetailClickEvent : function (e,_that) {
            var target = e.target;
            var id = $($(target).parents('li').find('h4')[0]).attr("ng-value");
            var src = "/bookdetail.html?id=" + id;
            window.location.href = src;
        },
        _moreBtnClickEvent : function (e,_that) {
            var target = e.target;
            var param = $(target).parents('li').attr("classify");
            var src;
            if(!utils.isNullOrEmpty(param)){
            	src = "/more.html?classifyName=" + param;
            }else{
            	src="/index.html"
            }
            window.location.href = src;
        }
    }
    controller.initPage();
})

