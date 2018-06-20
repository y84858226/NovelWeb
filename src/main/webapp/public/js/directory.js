define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('directory', []).config(function($compileProvider){
                //注:有些版本的angularjs为$compileProvider.urlSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|javascript):/);,两种都试一下可以即可
                $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|itms\-apps|ftp|mailto|file|javascript):/);
            });
            this.bookid = this.getQueryString("id");
            this.bookName = this.getQueryString("bookName");
            this.initBookDirectory(this.bookid);
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
         * 获取书籍目录
         */
        initBookDirectory : function (id) {
            var _that = this;
            _that.bookDirCtrl =  _that.app.controller("directoryCtrl", function($scope, $http) {
                utils.service.doGet('../../../../data/' + id + "/chapter.json",'',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            _that.directoryCount = dealWithResult.length;
                            $scope.allPage = _that.directoryCount;
                            $scope.bookDirectory = dealWithResult.slice(0,49);
                            $scope.bookName = _that.bookName;
                            _that.initPagination();
                            localStorage.setItem('bookDirectory', JSON.stringify(dealWithResult));
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
            $("#Pagination").pagination(_that.directoryCount,{
                callback: function (index) {
                    var start =  index * 50 ,end = (index + 1) * 50 - 1;
                    var appElement = document.querySelector('[ng-controller=directoryCtrl]');
                    var $scope = angular.element(appElement).scope();
                    var dealWithResult = JSON.parse(localStorage.getItem('bookDirectory'));
                    $scope.$applyAsync(function () {
                        $scope.bookDirectory = dealWithResult.slice(start,end);
                    })
                }
            });
            
            
        },
        /**
         * 绑定事件
         */
        bindEvent : function () {
            var _that = this;
            //目录点击事件
            $(".dir_list").on("click","a",function (e) {
                _that._directoryClickEvent(e,_that);
            });
           $("#reverseDir").on("click",$.proxy(this.reverseDirBtnClick,this));
           $("#nomalDir").on("click",$.proxy(this.nomalDirBtnClick,this));
           //分页点击跳转事件
           $('.page-btn').click(function(e){
               _that._pagebtnClickEvent(e,_that);
           })
        },
        _pagebtnClickEvent : function(e,_that){
            var $go = $(".page-go input").val();
            if(utils.isNullOrEmpty($go)) {
                 return
            }
            var goNum = Number($go),re = /^[1-9]+[0-9]*]*$/; 
            if(!re.test($go) || goNum < 0 || goNum > _that.booksCount){
                return 
            }
            //跳转页
            $("#Pagination").trigger('setPage', [goNum - 1]);
            var start =  (goNum -1) * 50 ,end = goNum * 50 - 1;
            var appElement = document.querySelector('[ng-controller=directoryCtrl]');
            var $scope = angular.element(appElement).scope();
            var dealWithResult = JSON.parse(localStorage.getItem('bookDirectory'));
            $scope.$applyAsync(function () {
                $scope.bookDirectory = dealWithResult.slice(start,end);
            })
        },
        /**
         *  目录点击
         */
        _directoryClickEvent : function (e,_that) {
            var target = e.target;
            var filePath = $(target).attr('ng-filePath');
            var novelId = $(target).attr('ng-novelId');
            var chapterNum = $(target).attr('ng-chapterNum');
            if(utils.isNullOrEmpty(filePath)){
                window.location.href = '../selectChapter?novelId=' +novelId+'&chapterNum='+chapterNum;
            }else{
                window.location.href = filePath;
            }
        },
        /**
         * 反序点击
         */
         reverseDirBtnClick : function(){
             var _that = this;
             var data = JSON.parse(localStorage.getItem('bookDirectory'));
             if(_that.bookDirCtrl){
                var appElement = document.querySelector('[ng-controller=directoryCtrl]');
                var $scope = angular.element(appElement).scope();
                $scope.$applyAsync(function () {
                    var tempData = data.reverse();
                    // $scope.bookDirectory =  tempData.slice(0,49);
                    localStorage.setItem('bookDirectory', JSON.stringify( tempData));
                    $("#Pagination").trigger('setPage', [0]); 
                })    
             }
             $("#nomalDir").addClass("cur");
             $("#reverseDir").removeClass("cur");
         },
        /**
         * 正序点击
         */
         nomalDirBtnClick : function(){
             var _that = this;
             var data = JSON.parse(localStorage.getItem('bookDirectory'));
             if(_that.bookDirCtrl){
                var appElement = document.querySelector('[ng-controller=directoryCtrl]');
                var $scope = angular.element(appElement).scope();
                $scope.$applyAsync(function () {
                    var tempData = data.reverse();
                    // $scope.bookDirectory = tempData.slice(0,49);
                    localStorage.setItem('bookDirectory', JSON.stringify( tempData));
                    $("#Pagination").trigger('setPage', [0]);
                }) 
             }
             $("#reverseDir").addClass("cur");
             $("#nomalDir").removeClass("cur");
         }
         
    }
    controller.initPage();
})

