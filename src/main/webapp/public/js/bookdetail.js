define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('bookDetail', []).config(function($compileProvider){
                //注:有些版本的angularjs为$compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|javascript):/);,两种都试一下可以即可
                $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|itms\-apps|ftp|mailto|file|javascript):/);
            });
            this.bookid = this.getQueryString("id");
            this.initBookDeatil(this.bookid);
            this.initRecommendBooks();
            this.bindEvent();
            this.createChapterListJsonFile(this.bookid);
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
        initBookDeatil : function (id) {
            var _that = this;
            _that.app.controller("bookDetailController", function($scope, $http) {
                utils.service.doGet('../../../data/' + id + "/novel.json",'',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON[0];
                            dealWithResult.description = dealWithResult.description.replace(/<br>/g,"");
                            $scope.book = dealWithResult;
                            _that.bookname = dealWithResult.name;
                            _that.chapterNum = dealWithResult.lastChapterNum;
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
            //搜索
             $("#bottomSearchbtn").click(function (e) {
                _that._searchbtnClickEvent(e,_that);
            });
            //马上阅读
            $("#readingBtn").click(function (e) {
                 e.stopPropagation();
                _that._readingbtnClickEvent(e,_that);
            });
            //分类点击
            $("#classifyName").click(function (e) {
                e.stopPropagation();
                _that._classifyNameClickEvent(e,_that);
            });
            //最新章节点击
            $("#lastChapterBtn").click(function (e) {
                e.stopPropagation();
                _that._lastChapterBtnClickEvent(e,_that);
            });
        },
        _lastChapterBtnClickEvent : function (e,_that) {
            var novelId = this.bookid;
            var chapterNum = this.chapterNum;
             window.location.href = '../selectChapter?novelId=' +novelId+'&chapterNum='+chapterNum;
        },
        _readingbtnClickEvent : function(e,_that){
            var novelId = this.bookid;
            var chapterNum = 1;
            window.location.href = '../selectChapter?novelId=' +novelId+'&chapterNum='+chapterNum;
        },
        _classifyNameClickEvent : function(e,_that){
             var target = e.target;
             var classifyName = target.innerText;
             var src;
             if(!utils.isNullOrEmpty(classifyName)){
            	src = "/more.html?classifyName=" + classifyName;
             }else{
             	src="/index.html"
             }
             window.location.href = src;
        },
        _bookDetailClickEvent : function (e,_that) {
            var target = e.target;
            var id = $($(target).parents('li').find('h4')[0]).attr("ng-value");
            var src = "/bookdetail.html?id=" + id;
            window.location.href = src;
        },
        _seeDirectoryBtnClickEvent : function (e,_that) {
            var src = "/directory.html?id=" + this.bookid + "&bookname=" + this.bookname;
            window.location.href = src;
        },
        _searchbtnClickEvent : function(e,_that,flag){
            var searchStr = $($(".t_i")[0]).val();
            var src = "/more.html?searchStr=" + searchStr;
            window.location.href = src;
        },
        /**
         * 更新目录页json文件
         */
        createChapterListJsonFile:function(id){
        	$.ajax({
        		url:"../updateChapterListJsonFile",
        		type:"post",
        		data:{
        			novelId:id
        		},
        		dataType:"json"
        	})
        }

    }
    controller.initPage();
})

