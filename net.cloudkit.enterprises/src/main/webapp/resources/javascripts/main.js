/**
 * main.js requrie main entrance
 * 
 * @Created by hongquanli<hongquanli@qq.com>
 * @Date 2014-03-14 17:35:00
 */
/*
 * http://www.ruanyifeng.com/blog/2012/11/require_js.html
 * http://www.w3school.com.cn/tags/index.asp
 *
 * require(['moduleA', 'moduleB', 'moduleC'], function (moduleA, moduleB,
 * moduleC){ // some code here });
 */
;
(function() {

	// third-party vendor
	var vendorPath = '../../resources/third-party';
	var applicationPath = '../../resources/javascripts/app/';
	require.config({
		// 为了在IE中正确检错，强制define/shim导出检测
		// enforceDefine : true,
		packages : [ {
			name : 'dojo',
			location : 'dojo',
			main : 'lib/main-browser',
			lib : '.'
		} ],
		// By default load any module IDs from resources/scripts/
		baseUrl : 'resources/javascripts/',
		skipDataMain: true,
		// the paths config could be for a directory.
		paths : {
			'domReady' : vendorPath + '/angular.js/1.3.0-beta.13/domReady',
			'angular' : vendorPath + '/angular.js/1.3.0-beta.13/angular.min',
			'angular-route' : vendorPath + '/angular.js/1.3.0-beta.13/angular-route.min',
			'angular-resource' : vendorPath + '/angular.js/1.3.0-beta.13/angular-resource.min',
			'angular-animate' : vendorPath + '/angular.js/1.3.0-beta.13/angular-animate.min',
			'angular-cookies' : vendorPath + '/angular.js/1.3.0-beta.13/angular-cookies.min',
			'angular-sanitize' : vendorPath + '/angular.js/1.3.0-beta.13/angular-sanitize.min',
			'angular-growl' : vendorPath + '/angular-growl/angular-growl',
			'angular-loading-bar' : vendorPath + '/angular-loading-bar/build/loading-bar',
			// ui.router
			'angular-ui-router' : vendorPath + '/angular-ui-router/0.2.10/angular-ui-router.min',
			// ui.bootstrap
			'angular-ui-bootstrap' : vendorPath + '/angular-ui-bootstrap/0.11.0/ui-bootstrap-tpls.min',
			'angular-ui-validate': vendorPath +'/angular-ui-validate/validate.min',
			'angular-slider' : vendorPath + '/angular-slider/angular-slider',
			'angular-colorpicker' : vendorPath + '/angular-bootstrap-colorpicker/js/bootstrap-colorpicker-module',
			'angular-locale-zh-cn': vendorPath + '/angular-locale/angular-locale_zh-cn',
			'jQuery' : [ 
	            'http://cdn.staticfile.org/jquery/2.1.1/jquery.min',
				// 若CDN加载错，则从如下位置重试加载
				vendorPath + '/jquery/2.1.1/jquery.min'
			],
			'jquery-mobile' : vendorPath + '/jquery-mobile/jquery.mobile-1.3.0',
			'jstorage' : vendorPath + '/jstorage/jstorage', 
			'underscore': [ 
	            'http://cdn.staticfile.org/underscore.js/1.6.0/underscore-min',
	            vendorPath + '/underscore/underscore'
			],
			'bootstrap' : vendorPath + '/bootstrap/3.3.5/js/bootstrap.min',
			'highcharts' : vendorPath + '/highcharts/highcharts',
			'spinjs' : vendorPath + '/spinjs/spin',
			'app-bootstrap' : applicationPath + '/app-bootstrap',
			'app' : applicationPath + '../app',
			'bootstrap-tpls' : 'bootstrap-tpls'
		},
		waitSeconds: 15,
		shim : {
			'jQuery' : {
				exports : 'jQuery'
			},
			'jquerymobile' : {
				deps: [ 'css!../css/jquery.mobile-1.3.0.min.css' ]
			},
			'underscore': { 
				exports: '_' 
	        },
			'angular' : {
				exports : 'angular',
				deps : [ 'jQuery' ]
			},
			'angular-animate' : {
				deps : [ 'angular' ]
			},
			'angular-resource' : {
				deps : [ 'angular' ]
			},
			'angular-cookies' : {
				deps : [ 'angular' ]
			},
			'angular-sanitize' : {
				deps : [ 'angular' ]
			},
			'angular-growl' : {
				deps : [ 'angular' ]
			},
			'angular-slider' : {
				deps : [ 'angular' ]
			},
			'angular-colorpicker' : {
				deps : [ 'angular' ]
			},
			'angular-loading-bar' : {
				deps : [ 'angular' ]
			},
			'angular-ui-validate': { 
				deps:['angular']
			},
			'angular-ui-router' : {
				deps : [ 'angular' ]
			},
			'bootstrap' : {
				deps : [ 'jQuery' ]
			},
			'bootstrap-tpl' : {
				deps:['angular']
			},
			'angular-ui-bootstrap' : { 
				deps:['bootstrap-tpls']
			},
			'angular-locale-zh-cn' : { 
				deps:['angular']
			},
			'highcharts' : {
				deps : [ 'jQuery' ]
			},
			'spinjs' : {

			}
		},
		// // kick start application
		// deps: ['./bootstrap'],
		priority: [
			"angular"
		],
		ready : function() {
			alert('');
			// require([ 'my/module' ], function(module) {
			// });
		}
	});

	/*
	//http://code.angularjs.org/1.2.1/docs/guide/bootstrap#overview_deferred-bootstrap
	window.name = "NG_DEFER_BOOTSTRAP!";

	require( [
		'angular',
		'app',
		'app/common/routes'
	], function(angular, app, routes) {
		'use strict';
		var $html = angular.element(document.getElementsByTagName('html')[0]);

		angular.element().ready(function() {
			angular.resumeBootstrap([app['name']]);
		});
	});
	*/

}());

// require([ 'domReady' ], function(domReady) {
// domReady(function() {
// // 一旦DOM准备就绪，本回调就执行。
// // 在此函数中查询及处理DOM是安全的。
// });
// });

/*
 main.js
 require(['app/app-bootstrap'], function(bootstrap){

 });
 */

// Start the main app logic.
require([ 'require', 'angular', 'jQuery', 'bootstrap', 'app' ],function(require, angular){
	angular.element(document).ready(function() {
		// 此处引用了所需的所有模块，并且只做了一件事，用angular把页面驱动起来。
		angular.bootstrap(document, ['app']);
	});
});


/*
require([ 'domReady!', 'jQuery', 'bootstrap' ], function(doc, $, bootstrap) {
	// 本函数会在DOM ready时调用。
    // 注意'domReady!'的值为当前的document
	
	console.log('jQuery version:', $.fn.jquery);
});
*/