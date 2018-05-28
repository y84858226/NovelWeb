layui.config({
	base : '../../lib/winui/' // 指定 winui 路径
	,
	version : '1.0.0-beta'
}).define([ 'table', 'jquery', 'winui' ], function(exports) {
	winui.renderColor();
	var table = layui.table, $ = layui.$, tableId = 'tableid';
	var form = layui.form;
	// 表格渲染
	table.render({
		id : tableId,
		elem : '#crawler',
		url : '../../../selectCrawler',
		even : false, // 隔行变色
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
			field : 'crawlerName',
			title : '名称'
		}, {
			field : 'crawlerUrl',
			title : '网址'
		}, {
			field : 'crawlerStatus',
			title : '自动运行',
			width : 100,
			templet : '#stateTpl'
		}, {
			title : '操作',
			fixed : 'right',
			align : 'center',
			toolbar : '#crawlerOption',
			width : 320
		} ] ]
	});
	// 监听工具条
	table.on('tool(crawlerfilter)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值
		var tr = obj.tr; // 获得当前行 tr 的DOM对象
		var ids = ''; // 选中的Id
		$(data).each(function(index, item) {
			ids += item.id + ',';
		});
		if (layEvent === 'del') { // 删除
			deleteCrawler(ids, obj);
		} else if (layEvent === 'configList') {// 查看配置
			top.winui.window.open({
				id : 'configlist',
				type : 2,
				title : '查看配置',
				content : 'views/crawler/configlist.html?id=' + data.id,
				area : [ '60vw', '70vh' ],
				offset : [ '15vh', '20vw' ],
			});
		} else if (layEvent === 'run') {// 运行
			$.ajax({
				type : 'post',
				url : '../../../runCrawler',
				data : {
					id : data.id
				},
				async : true,
				success : function(data) {
					layer.close(index);
					top.winui.window.msg(data, {
						time : 2000
					});
				},
				error : function(xml) {
					layer.close(index);
					top.winui.window.msg("获取页面失败", {
						icon : 2,
						time : 2000
					});
					console.log(xml.responseText);
				}
			});
		}
	});

	form.on('switch(switchFilter)', function(data) {
		console.log(data.elem); // 得到checkbox原始DOM对象
		console.log(data.elem.checked); // 开关是否开启，true或者false
		console.log(data.value); // 开关value值，也可以通过data.elem.value得到
		console.log(data.othis); // 得到美化后的DOM对象
	});

	// 表格重载
	function reloadTable() {
		table.reload(tableId, {});
	}

	// 删除爬虫
	function deleteCrawler(ids, obj) {
		var msg = obj ? '确认删除爬虫【' + obj.data.crawlerName + '】吗？' : '确认删除选中数据吗？'
		top.winui.window.confirm(msg, {
			icon : 3,
			title : '删除爬虫'
		}, function(index) {
			layer.close(index);
			// 向服务端发送删除指令
			$.ajax({
				url : "../../../deleteCrawler",
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
	
	// 绑定按钮事件
	$('#addCrawler').on('click', function() {
		top.winui.window.open({
			id : 'crawleradd',
			type : 2,
			title : '新建爬虫',
			content : "views/crawler/crawleradd.html",
			area : [ '60vw', '70vh' ],
			offset : [ '15vh', '20vw' ],
		});
	});
	
	$('#deleteCrawler').on('click', function() {
		var checkStatus = table.checkStatus(tableId);
		var checkCount = checkStatus.data.length;
		if (checkCount < 1) {
			top.winui.window.msg('请选择一条数据', {
				time : 2000
			});
			return false;
		}
		var ids = '';
		$(checkStatus.data).each(function(index, item) {
			ids += item.id + ',';
		});
		deleteCrawler(ids);
	});
	
	$('#reloadTable').on('click', reloadTable);
});
