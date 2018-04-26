$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysmbexchangecofig/list',
        datatype: "json",
        colModel: [			
			{ label: 'cofigId', name: 'cofigId', index: 'cofig_id', width: 50, key: true },
			{ label: '交易所id', name: 'exchangeId', index: 'exchange_id', width: 80 }, 			
			{ label: '交易所名称', name: 'exchangeName', index: 'exchange_name', width: 80 }, 			
			{ label: 'apiKey', name: 'apiKey', index: 'api_key', width: 80 }, 			
			{ label: 'secretKey', name: 'secretKey', index: 'secret_key', width: 80 }, 			
			{ label: '状态  0：禁用   1：正常', name: 'status', index: 'status', width: 80 }, 			
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysMbExchangeCofig: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysMbExchangeCofig = {};
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
			var url = vm.sysMbExchangeCofig.cofigId == null ? "sys/sysmbexchangecofig/save" : "sys/sysmbexchangecofig/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
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
				    url: baseURL + "sys/sysmbexchangecofig/delete",
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
			$.get(baseURL + "sys/sysmbexchangecofig/info/"+cofigId, function(r){
                vm.sysMbExchangeCofig = r.sysMbExchangeCofig;
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