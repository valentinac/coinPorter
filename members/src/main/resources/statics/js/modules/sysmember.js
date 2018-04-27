$(function () {
	$("#jqGrid").jqGrid({
		url:'sys/member/list',
		datatype: "json",
		colModel: [
			{ label: '会员ID', name: 'memberId', index: 'member_id', width: 50, key: true },
			{ label: '会员', name: 'memberName',  width: 80 },
			{ label: '邮箱', name: 'email', width: 80 },
			{ label: '手机号', name: 'mobile', width: 80 },
			{ label: '谷歌code', name: 'goolgeCode', width: 80 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ?
					'<span class="label label-danger">禁用</span>' :
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', width: 80 }
		],
		viewrecords: true,
		height: 385,
		rowNum: 10,
		rowList : [10,30,50],
		rownumbers: true,
		rownumWidth: 25,
		autowidth:true,
		multiselect: true,
		pager: "#jqGridPager",
		jsonReader : {
			root: "page.list",
			page: "page.currPage",
			total: "page.totalPage",
			records: "page.totalCount"
		},
		prmNames : {
			page:"page",
			rows:"limit",
			order: "order"
		},
		gridComplete:function(){
			//隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
		}
	});
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysMember: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysMember = {};
		},
		update: function (event) {
			var memberId = getSelectedRow();
			if(memberId == null){
				return ;
			}
			vm.showList = false;
			vm.title = "修改";

			vm.getInfo(memberId)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysMember.memberId == null ? "sys/member/save" : "sys/member/update";
			$.ajax({
				type: "POST",
				url: url,
				contentType: "application/json",
				data: JSON.stringify(vm.sysMember),
				success: function(r){
					if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var memberIds = getSelectedRows();
			if(memberIds == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
					url: "sys/member/delete",
					contentType: "application/json",
					data: JSON.stringify(memberIds),
					success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(memberId){
			$.get("sys/member/info/"+memberId, function(r){
				vm.sysMember = r.sysMember;
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				page:page
			}).trigger("reloadGrid");
		}
	}
});