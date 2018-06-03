define(function (require, exports, module) {
    var $ = require('jquery');
    var utils = require('utils');
    var base64 = require('base64');
    'use strict'
    var controller = {
        initPage : function () {
            this.app = angular.module('directory', []);
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
                utils.service.doGet('../../../../novel/data/' + id + "/chapter.json",'',function (result) {
                    if(!utils.isNullOrEmpty(result) && !utils.isNullOrEmpty(result.responseJSON)){
                        $scope.$applyAsync(function () {
                            var dealWithResult = result.responseJSON;
                            $scope.bookDirectory = dealWithResult;
                            $scope.bookName = _that.bookName;
                            localStorage.setItem('bookDirectory', JSON.stringify(dealWithResult));
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
            $(".dir_list").on("click","a",function (e) {
                _that._directoryClickEvent(e,_that);
            });
            $("#reverseDir").on("click",$.proxy(this.reverseDirBtnClick,this));
            $("#nomalDir").on("click",$.proxy(this.nomalDirBtnClick,this));
        },
        /**
         *  目录点击
         */
        _directoryClickEvent : function (e,_that) {
            var target = e.target;
            var href = $($(target).parents('li').find('a')).attr('ng-value');
            var id = $(target).parents('li').find('a').attr('ng-id');
            if(utils.isNullOrEmpty(href)){
                utils.service.doPost('../selectChapter?id=' + id,'','');
            }else{
                window.location.href = href;
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
                     $scope.bookDirectory =  data.reverse();
                })
             }
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
                     $scope.bookDirectory = data;
                })
             }
         }
         
    }
    controller.initPage();
})

