layui.config({
	base : '../../lib/winui/' // 指定 winui 路径
	,
	version : '1.0.0-beta'
}).define(
		[ 'table', 'jquery', 'winui' ],
		function(exports) {
			winui.renderColor();
			var table = layui.table, $ = layui.$, tableId = 'tableid';
			var id = getQueryString("id");
			// 表格渲染
			table.render({
				id : tableId,
				elem : '#config',
				url : '../../../selectCrawlerConfig?crawlerId=' + id,
				even : false, // 隔行变色
				page : false,
				// height: 'full-65', //自适应高度
				// size: '', //表格尺寸，可选值sm lg
				// skin: '', //边框风格，可选值line row nob
				limits : [ 8, 16, 24, 32, 40, 48, 56 ],
				limit : 8,
				cols : [ [ {
					field : 'configName',
					title : '配合名称',
					width : 100
				}, {
					field : 'selector',
					title : '样式选择器',
					width : 100,
					edit : 'text'
				}, {
					field : 'num',
					title : '样式个数',
					width : 100,
					edit : 'text'
				}, {
					field : 'attrName',
					title : '属性名称',
					width : 100,
					edit : 'text'
				}, {
					field : 'reg',
					title : '正则表达式',
					width : 100,
					edit : 'text'
				}, {
					field : 'regGroupNum',
					title : '正则组数',
					width : 100,
					edit : 'text'
				}, {
					field : 'headAppendResult',
					title : '前置追加',
					width : 100,
					edit : 'text'
				}, {
					field : 'tailAppendResult',
					title : '后置追加',
					width : 100,
					edit : 'text'
				}, {
					field : 'replaceResult',
					title : '结果替换',
					width : 100,
					edit : 'text'
				} ] ]
			});

			// 监听单元格编辑
			table.on('edit(configfilter)', function(obj) {
				var value = obj.value // 得到修改后的值
				, data = obj.data // 得到所在行所有键值
				, field = obj.field; // 得到字段
				layer.msg('[Name: ' + data.configName + '] ' + field
						+ ' 字段更改为：' + value);
				var configlist = {};
				configlist[field] = value;
				configlist["id"]=data.id;
				$.ajax({
					type : 'post',
					url : '../../../updateCrawlerConfig',
					data : configlist,
					async : true,
					success : function(data) {
						layer.close(index);
						top.winui.window.msg(data, {
							time : 2000
						});
					},
					error : function(xml) {
						top.winui.window.msg("获取页面失败", {
							icon : 2,
							time : 2000
						});
						console.log(xml.responseText);
					}
				});

			});

			// 表格重载
			function reloadTable() {
				table.reload(tableId, {});
			}

			// get url param
			function getQueryString(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var url = decodeURI(decodeURI(window.location.search))
				var r = url.substr(1).match(reg);
				if (r != null)
					return unescape(r[2]);
				return null;
			}

			$('#reloadTable').on('click', reloadTable);
		});
