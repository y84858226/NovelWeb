define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('bookDetail', []);
            this.bookName = this.getQueryString("bookname");
            this.initBookDeatil(this.bookName);
            this.initRecommendBooks();
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
         * 获取详细书籍信息
         */
        initBookDeatil : function (bookName) {
            var _that = this;
            _that.app.controller("bookDetailController", function($scope, $http) {
                utils.service.doPost('../getBookDetail',bookName,function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            dealWithResult.description = dealWithResult.description.replace(/<br>/g,"");
                            $scope.book = dealWithResult;
                        });
                    }
                })
            });
        },
        /**
         * 获取首页分类书籍
         */
        initRecommendBooks : function () {
            var _that = this;
            _that.app.controller("detailRecommendCtrl", function($scope, $http) {
                utils.service.doPost('../getRecommendBooks','',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $.each(dealWithResult, function(key, val){
                                dealWithResult[key].description = val.description.replace(/<br>/g,"")
                            });
                            $scope.detailRecommendImageBooks = dealWithResult.slice(0,1);
                            $scope.detailRecommendBooks = dealWithResult.slice(1,5);
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
            $("#seeDirectoryBtn").click(function (e) {
                e.stopPropagation();
                _that._seeDirectoryBtnClickEvent(e,_that);
            });
        },
        _bookDetailClickEvent : function (e,_that) {
            var target = e.target;
            var bookname = $(target).find('h4').prevObject["0"].innerText;
            var src = "/novel/novelSee/bookdetail.html?bookname=" + bookname;
            window.location.href = src;
        },
        _seeDirectoryBtnClickEvent : function (e,_that) {
            var src = "/novel/novelSee/directory.html?bookname=" + this.bookName;
            window.location.href = src;
        }

    }
    controller.initPage();
})

