$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysexchange/list',
        datatype: "json",
        colModel: [			
			{ label: '交易所ID', name: 'exchangeId', index: 'exchange_id', width: 50, key: true },
			{ label: '交易所名称', name: 'exchangeName',  width: 80 },
			{ label: '访问地址', name: 'exchangeUrl',  width: 160 },
			{ label: '状态', name: 'status',  width: 80 , formatter: function(value, options, row){
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysExchange: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysExchange = {};
		},
		update: function (event) {
			var exchangeId = getSelectedRow();
			if(exchangeId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(exchangeId)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysExchange.exchangeId == null ? "sys/sysexchange/save" : "sys/sysexchange/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysExchange),
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
			var exchangeIds = getSelectedRows();
			if(exchangeIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/sysexchange/delete",
                    contentType: "application/json",
				    data: JSON.stringify(exchangeIds),
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
		getInfo: function(exchangeId){
			$.get(baseURL + "sys/sysexchange/info/"+exchangeId, function(r){
                vm.sysExchange = r.sysExchange;
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