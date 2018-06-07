var controller = {
	initPage : function() {
		this.initHotBooks();
	},
	/**
	 * 获取热门书籍
	 */
	initHotBooks : function() {
		$.ajax({
			type:"post",
			url:"../getHotBooks",
			dataType:"json",
			success:function(result){
				console.log(result);
				var html='';
				$.each(result, function(key, val) {
					if(key==0){
						html+='<li>';
						html+='<a href="">';
						html+='<div class="bcover fl">';
						html+='<img src="/data/'+val.id+'/'+val.id+'.jpg" alt="'+val.name+'" onerror="this.src="/public/img/nopic.jpg";this.onerror="";" height="130" width="85" />';
						html+='</div>';
						html+='<div class="bintro pl10">';
						html+='<h4 ng-value="'+val.id+'">'+val.name+'</h4>';
						html+='<p>';
						html+= val.typeName+'<br>'+val.description;
						html+='</p>';
						html+='</div>';
						html+='</a>';
						html+='</li>';
					}else{
						html+='<li>';
						html+='<a href="">';
						html+='<div class="bintro">';
						html+='<h4 ng-value="'+val.id+'">'+val.name+'</h4>';
						html+='<p>'+val.typeName+'<br>'+val.description+'</p>';
						html+='</div>';
						html+='</a>';
						html+='</li>';
					}
				});
				$("#rmnovel").html(html);
			}
				
		})
		
//		var _that = this;
//		_that.app.controller('hotBookController', function($scope, $http) {
//			utils.service.doPostAsync('../getHotBooks', '', function(result) {
//				if (!utils.isNullOrEmpty(result)
//						&& !utils.isNullOrEmpty(result.responseJSON)) {
//					$scope.$applyAsync(function() {
//						var dealWithResult = result.responseJSON;
//						
//						$scope.hotImageBooks = dealWithResult.slice(0, 1);
//						$scope.hotBooks = dealWithResult.slice(1, 5);
//					});
//				}
//			})
//		});
	}
}
controller.initPage();
