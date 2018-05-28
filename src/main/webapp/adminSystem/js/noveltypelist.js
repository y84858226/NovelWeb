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
		elem : '#novelType',
		url : '../../../selectNovelType',
		even : false, // 隔行变色
		page : true,
		// height: 'full-65', //自适应高度
		// size: '', //表格尺寸，可选值sm lg
		// skin: '', //边框风格，可选值line row nob
		limits : [ 8, 16, 24, 32, 40, 48, 56 ],
		limit : 8,
		cols : [ [ {
			field : 'originalTypeName',
			title : "原类型",
		}, {
			field : 'typeName',
			title : '现类型'
		}, {
			title : '操作',
			fixed : 'right',
			align : 'center',
			toolbar : '#novelTypeOption',
			width : 320
		} ] ]
	});

	table.on('tool(novelTypefilter)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值
		var tr = obj.tr; // 获得当前行 tr 的DOM对象
		var ids = ''; // 选中的Id
		$(data).each(function(index, item) {
			ids += item.id + ',';
		});
		if (layEvent === 'del') { // 删除
			deleteNovelType(ids, obj);
		}
	});

	// 删除类型
	function deleteNovelType(ids, obj) {
		var msg = obj ? '确认删除原类型【' + obj.data.originalTypeName + '】吗？' : '确认删除选中数据吗？'
		top.winui.window.confirm(msg, {
			icon : 3,
			title : '删除类型映射'
		}, function(index) {
			layer.close(index);
			// 向服务端发送删除指令
			$.ajax({
				url : "../../../deleteNovelType",
				type : "post",
				dataType : "json",
				data : {
					"ids" : ids
				},
				success : function() {
				}
			})
			// 刷新表格
			if (obj) {
				top.winui.window.msg('删除成功', {
					icon : 1,
					time : 2000
				});
				obj.del(); // 删除对应行（tr）的DOM结构
			} else {
				top.winui.window.msg('删除成功', {
					time : 2000
				});
				reloadTable(); // 直接刷新表格
			}
		});
	}

	// 表格重载
	function reloadTable() {
		table.reload(tableId, {});
	}

	// 绑定按钮事件
	$('#selectType').on('click', function() {
		top.winui.window.open({
			id : 'typenameadd',
			type : 2,
			title : '添加类型',
			content : "views/noveltype/noveltypeadd.html",
			area : [ '60vw', '70vh' ],
			offset : [ '15vh', '20vw' ],
		});
	});
	$('#reloadTable').on('click', reloadTable);
	exports('rolelist', {});
});
