//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

var vm = new Vue({
	el:'#rrapp',
	data:{
		main:"main.html",
		navTitle:"欢迎页",
		user:{},
		password:'',
		newPassword:'',
	},
	methods: {
		donate: function () {
			layer.open({
				type: 2,
				title: false,
				area: ['806px', '467px'],
				closeBtn: 1,
				shadeClose: false,
				content: ['http://cdn.renren.io/donate.jpg', 'no']
			});
		},
		getUser: function(){
			$.getJSON("sys/sysmember/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					vm.password =$("#password").val();
					vm.newPassword =$("#newPassword").val();
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
						url: "sys/sysmember/password",
						data: data,
						dataType: "json",
						success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
				}
			});
		},
	}
});

//路由
var router = new Router();
var menus = ["main.html","modules/sys/sysmember.html","modules/sys/sysmbexchangecofig.html"];
routerList(router, menus);
router.start();
vm.getUser();

function routerList(router, menus){
	for(var index in menus){
		router.add('#'+menus[index], function() {
			var url = window.location.hash;

			//替换iframe的url
			vm.main = url.replace('#', '');

			//导航菜单展开
			$(".treeview-menu li").removeClass("active");
			$("a[href='"+url+"']").parents("li").addClass("active");

			vm.navTitle = $("a[href='"+url+"']").text();
		});
	}
}
