'use strict';

/* Services */
define([ ], function () {
	// Demonstrate how to register services
	// In this case it is a simple value service.
	angular.module('common.services', [ 'ngResource' ], function($provide) {
		// $provide.provider('PP', pp);
	
		$provide.factory('PP', function() {
			return {
				'hello' : '123'
			};
		});
	})
	//.factory('notify', function($window) {
	//	var msgs = [];
	//	return function(msg) {
	//		msgs.push(msg);
	//		if (msgs.length == 3) {
	//			$window.alert(msgs.join("\n"));
	//			msgs = [];
	//		}
	//	};
	//}).factory('phone', [ '$resource', function($resource) {
	//	return $resource('phones/:phoneId.json', {}, {
	//		query : {
	//			method : 'GET',
	//			params : {
	//				phoneId : 'phones'
	//			},
	//			isArray : true
	//		}
	//	});
	//} ])
	.service('CommonService', function($resource) {
		
		this.getData = function() {
			return $resource('https://graph.facebook.com/cocacola?callback=JSON_CALLBACK', {}, {
				jsonp_query: {
					method: 'JSONP'
				}
		    });
		};
		
		// 获取菜单
		this.getMenus = function() {
			return {
		   		index : {name:'首页', children : []},
				// product
		   		product : {name:'商品', path:'product_list', children : [ 
		            {name:'商品管理', path:'product_list'},
		    		{name:'商品分类', path:'product_002'},
		    		{name:'商品参数', path:'product_003'},
		    		{name:'商品属性', path:'product_004'},
		    		{name:'规格管理', path:'product_005'},
		    		{name:'品牌管理', path:'product_006'},
		    		{name:'到货通知', path:'product_007'}
		        ]},
		        // order
		        order : {name:'定单', path:'order_list', children : [ 
		            {name:'订单管理', path:'order_list'},
		    		{name:'收款管理', path:'order_001'},
		    		{name:'退款管理', path:'order_002'},
		    		{name:'发货管理', path:'order_003'},
		    		{name:'退货管理', path:'order_004'},
		    		{name:'发货点管理', path:'order_005'},
		    		{name:'快递单模板', path:'order_006'}
		        ]},
		        // member
		        member : {name:'会员', path:'member_list', children : [ 
		            {name:'会员管理', path:'member_list'},
		    		{name:'会员等级', path:'member_002'},
		    		{name:'会员注册项', path:'member_003'},
		    		{name:'评论管理', path:'member_004'},
		    		{name:'咨询管理', path:'member_005'}
		        ]},
		        // article
		        article : {name:'内容', path:'article_list', children : [ 
		            {name:'文章管理', path:'article_list'},
		            {name:'导航管理', path:'article_001'},
		    		{name:'文章分类', path:'article_002'},
		    		{name:'标签管理', path:'article_003'},
		    		{name:'友情链接', path:'article_004'},
		    		{name:'广告位', path:'article_005'},
		    		{name:'广告管理', path:'article_006'},
		    		{name:'模板管理', path:'article_007'},
		    		{name:'缓存管理', path:'article_008'},
		    		{name:'静态化管理', path:'article_009'},
		    		{name:'索引管理', path:'article_010'}
		        ]},
		        // statistics
		        statistics : {name:'统计', path:'statistics_sales', children : [ 
		            {name:'销售统计', path:'statistics_sales'},
		            {name:'访问统计', path:'statistics_002'},
		    		{name:'统计设置', path:'statistics_003'},
		    		{name:'销售排行', path:'statistics_004'},
		    		{name:'消费排行', path:'statistics_005'},
		    		{name:'预存款', path:'statistics_006'}
		        ]},
		        // system
		        system : {name:'系统', path:'system_setting', children : [ 
		            {name:'系统设置', path:'system_setting'},
		    		{name:'地区管理', path:'system_001'},
		    		{name:'支付方式', path:'system_002'},
		    		{name:'配送方式', path:'system_003'},
		    		{name:'物流公司', path:'system_004'},
		    		{name:'支付插件', path:'system_005'},
		    		{name:'存储插件', path:'system_006'},
		    		{name:'管理员', path:'system_007'},
		    		{name:'角色管理', path:'system_008'},
		    		{name:'发送消息', path:'system_009'},
		    		{name:'消息列表', path:'system_010'},
		    		{name:'草稿箱', path:'system_011'},
		    		{name:'日志管理', path:'system_012'}
		        ]}
			};
		};

	}).value('version', '0.1');
});
