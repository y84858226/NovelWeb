layui.config({
	base : '../../lib/winui/' // 指定 winui 路径
	,
	version : '1.0.0-beta'
}).define([ 'table', 'jquery', 'winui' ], function(exports) {

	winui.renderColor();

	var table = layui.table, $ = layui.$, tableId = 'tableid';
	// 表格渲染
	table.render({
		id : tableId,
		elem : '#crawler',
		url : '../../../selectNovelType',
		even : true, // 隔行变色
		page : true,
		// height: 'full-65', //自适应高度
		// size: '', //表格尺寸，可选值sm lg
		// skin: '', //边框风格，可选值line row nob
		limits : [ 8, 16, 24, 32, 40, 48, 56 ],
		limit : 8,
		cols : [ [ {
			field : 'id',
			type : 'checkbox'
		}, {
			field : 'typeName',
			title : '类型'
		}] ]
	});
	
	// 表格重载
	function reloadTable() {
		table.reload(tableId, {});
	}

	// 绑定按钮事件
	$('#selectType').on('click', function(){
		$.ajax({
			url : "../../../addNovelType",
			type : "post",
			dataType : "json",
			success : function() {
				reloadTable();
			}
		})
	});
	$('#reloadTable').on('click', reloadTable);
	exports('rolelist', {});
});
