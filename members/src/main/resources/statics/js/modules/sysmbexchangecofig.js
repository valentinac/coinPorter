$(function () {
    $("#jqGrid").jqGrid({
        url: 'sys/mbexchangecofig/list',
        datatype: "json",
        colModel: [			
			{ label: '配置ID', name: 'cofigId', index: 'cofig_id', width: 50, key: true },
			{ label: '交易所名称', name: 'exchangeName', width: 80 },
			{ label: 'apiKey', name: 'apiKey',  width: 80 },
			{ label: 'secretKey', name: 'secretKey', width: 80 },
			{ label: '状态', name: 'status',  width: 80, formatter: function(value, options, row){
				return value === 0 ?
					'<span class="label label-danger">禁用</span>' :
					'<span class="label label-success">正常</span>';
			}},
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }			
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
var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "exchangeId",
			pIdKey: "1",
			rootPId: -1
		},
		key: {
			url:"nourl",
			name:"exchangeName"
		}
	}
};
var ztree;
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysMbExchangeCofig: {exchangeId:"",exchangeName:""}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysMbExchangeCofig = {exchangeId:"",exchangeName:""};
			vm.getExchange();
		},
		update: function (event) {
			var cofigId = getSelectedRow();
			if(cofigId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(cofigId)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysMbExchangeCofig.cofigId == null ? "sys/mbexchangecofig/save" : "sys/mbexchangecofig/update";
			$.ajax({
				type: "POST",
			    url:  url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysMbExchangeCofig),
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
			var cofigIds = getSelectedRows();
			if(cofigIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "sys/mbexchangecofig/delete",
                    contentType: "application/json",
				    data: JSON.stringify(cofigIds),
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
		getInfo: function(cofigId){
			$.get("sys/mbexchangecofig/info/"+cofigId, function(r){
                vm.sysMbExchangeCofig = r.sysMbExchangeCofig;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		getExchange: function(){
			//加载部门树
			$.get("sys/mbexchangecofig/selectexchange", function(r){
				ztree = $.fn.zTree.init($("#exchangeTree"), setting, r.list);
				var node = ztree.getNodeByParam("exchangeId", vm.sysMbExchangeCofig.exchangeId);
				ztree.selectNode(node);
				if(node){
					vm.sysMbExchangeCofig.exchangeName = node.name;
				}
			})
		},
		exchangeTree: function(){
			layer.open({
				type: 1,
				offset: '50px',
				skin: 'layui-layer-molv',
				title: "选择交易所",
				area: ['300px', '450px'],
				shade: 0,
				shadeClose: false,
				content: jQuery("#exchangeLayer"),
				btn: ['确定', '取消'],
				btn1: function (index) {
					var node = ztree.getSelectedNodes();
                    console.log(JSON.stringify(node));
					vm.sysMbExchangeCofig.exchangeId = node[0].exchangeId;
					vm.sysMbExchangeCofig.exchangeName = node[0].exchangeName;
					layer.close(index);
				}
			});
		},
	}
});