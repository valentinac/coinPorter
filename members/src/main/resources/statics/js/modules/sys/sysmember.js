$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'sys/sysmember/list',
		datatype: "json",
		colModel: [
			{ label: '会员ID', name: 'userId', index: "user_id", width: 45 },
			{ label: '会员', name: 'username', width: 75 },
			{ label: '邮箱', name: 'email', width: 90 },
			{ label: '手机号', name: 'mobile', width: 100 },
			{ label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
				return value === 0 ?
					'<span class="label label-danger">禁用</span>' :
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 85}
		],
		viewrecords: true,
		height: 385,
		rowNum: 10,
		rowList : [10,30,50],
		rownumbers: false,
		rownumWidth: 25,
		autowidth:true,
		multiselect: false,
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
		q:{
			username: null
		},
		showList: true,
		title:null,
		user:{
			status:1
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		update: function () {
			vm.showList = false;
			vm.title = "修改";
			vm.getUser();
		},
		saveOrUpdate: function () {
			var url = vm.user.userId == null ? "sys/sysmember/save" : "sys/sysmember/update";
			$.ajax({
				type: "POST",
				url: baseURL + url,
				contentType: "application/json",
				data: JSON.stringify(vm.user),
				success: function(r){
					if(r.code === 0){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getUser: function(userId){
			$.get(baseURL + "sys/sysmember/info/", function(r){
				vm.user = r.user;
				vm.user.password = null;
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{'username': vm.q.username},
				page:page
			}).trigger("reloadGrid");
		}
	}
});