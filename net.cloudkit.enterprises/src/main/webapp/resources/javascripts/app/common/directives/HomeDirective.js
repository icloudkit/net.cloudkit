'use strict';

/* Directives */
define([ ], function () {


//	angular.module("lattice.admin.bootstrap.tpls", ["resources/templates/common/navbar.html", "resources/templates/partials/diskList.html"]);
//
//
//	angular.module("resources/templates/admin/common/navbar.html", []).run([ "$templateCache", function($templateCache) {
//		"use strict";
//		$templateCache.put("resources/templates/admin/common/navbar.html", "<div>=========</div>");
//	} ]);
//
//	angular.module("resources/templates/partials/diskList.html", []).run([ "$templateCache", function($templateCache) {
//		"use strict";
//		$templateCache.put("resources/templates/partials/diskList.html", 
//			"<h4>$templateCache</h4>" + 
//			"<p>这是直接调用$templateCache服务获取模板文件的方式</p>" + 
//			"<a href=\"http://www.baidu.com\">服务启用templateCache方式</a>");
//	}]);
	
	angular.module('common.directives', [ ]).directive('version', [ 'version', function(version) {
		return function(scope, element, attrs) {
	
			// var type = attrs.type || 'text';
			// var required = attrs.hasOwnProperty('required') ?
			// "required='required'" : "";
	
			element.text(version);
			// element.html(htmlText);
			// element.replaceWith(htmlText);
		};
	} ]).directive('ngFocus', [ '$parse', function($parse) {
		return function(scope, element, attr) {
			var fn = $parse(attr['ngFocus']);
			element.bind('focus', function(event) {
				scope.$apply(function() {
					fn(scope, {
						$event : event
					});
				});
			});
		}
	} ]).directive('ngBlur', [ '$parse', function($parse) {
		
		return function(scope, element, attr) {
			var fn = $parse(attr['ngBlur']);
			element.bind('blur', function(event) {
				scope.$apply(function() {
					fn(scope, {
						$event : event
					});
				});
			});
		}
	} ]).directive('currentTime', function($interval, dateFilter) {
	
		function link($scope, $element, $attrs) {
	
			// // current-time="'yyyy-MM-dd HH:mm:ss'"
	
			// scope.format = 'M/d/yy h:mm:ss a';
			// scope.format = 'yyyy-MM-dd HH:mm:ss';
	
			var format, timeoutId;
	
			function updateTime() {
				$element.text(dateFilter(new Date(), format));
			}
	
			$scope.$watch($attrs.currentTime, function(value) {
				format = value;
				updateTime();
			});
	
			$element.on('$destroy', function() {
				$interval.cancel(timeoutId);
			});
	
			// start the UI update process; save the timeoutId for canceling
			timeoutId = $interval(function() {
				// updateTime(); // update DOM
			}, 1000);
		}
	
		return {
			link : link
		};
	}).directive('uiAccordion', function() {
		return {
			restrict : 'EA',
			replace : true,
			transclude : true,
			template : '<div ng-transclude></div>',
			controller : function() {
	
				var expanders = [];
				this.gotOpened = function(selectedExpander) {
					angular.forEach(expanders, function(expander) {
						if (selectedExpander != expander) {
							expander.showMe = false;
						}
					});
				}
				this.addExpander = function(expander) {
					expanders.push(expander);
				}
			}
		}
	}).directive('expander', function() {
		return {
			// E = Element, A = Attribute, C = Class, M = Comment
			// E元素<my-directive></my-directive>
			// A属性<div my-directive="var"></div>
			// C样式类<div class="my-directive:var"></div>
			// M注释<!-- directive: my-directive var -->
			restrict : 'EA',
			replace : true,
			transclude : true,
			require : '^?uiAccordion',
			scope : {
				title : '=expanderTitle'
			},
			template : '<div>' + 
				'  <div class="title" ng-click="toggle()">{{title}}</div>' + 
				'  <div class="body" ng-show="showMe" ng-transclude></div>' + 
				'</div>',
			link : function(scope, element, attrs, accordionController) {
				scope.showMe = false;
				accordionController.addExpander(scope);
				scope.toggle = function toggle() {
					scope.showMe = !scope.showMe;
					accordionController.gotOpened(scope);
				}
			}
		};
	}).directive('header', function($compile, $parse, $templateCache, CommonService) {
		
		console.log($templateCache.get("resources/templates/common/header.html"));
	
		// $scope, $element, $attrs
		function link($scope, $element, $attrs, $location) {
	
			$scope.keys = function(obj){
				return obj? Object.keys(obj) : [];
			}

			$scope.menus = CommonService.getMenus();
			// console.log(CommonService.getData().jsonp_query());
			
		}
	
		var headerController = function($scope, $location, $routeParams) {
			
			$scope.isActive = function(path, key, type){

				var bool = false;

				if(type == 'parent') {
					// if(path.substring(1) != '') {
						// console.log($routeParams.name + '-' + $routeParams.url);
						// console.log(path.substring(1) + '----' + key);
						bool = ($location.path().substring(1).indexOf(key) == 0)? true : false;
						bool = ($routeParams.name == key)? true : false;
					// }
					bool = bool || (path == '/' && ($location.path() == '/index/' || $location.path() == '/'));

					// 子菜单
					if(bool) {
						$scope.parent = key;
						$scope.navs = $scope.menus[key].children;
					}
				} else if(type == 'children') {
					// console.log($location.path().substring(1) + '-' + path.substring(1) + '-' + (($location.path().substring(1).indexOf(path.substring(1)) == 0)? true : false));
					// console.log($location.path() + '-' + ('/' + $scope.parent + path));
					bool = bool || ($location.path() == ('/' + $scope.parent + path));
				}

				return bool;
			}
			
			//$scope.select = function(index) {
				
			//}
		}
	
		return {
			// E = Element, A = Attribute, C = Class, M = Comment
			restrict : 'EA',
			replace : true,
			transclude : true,
			scope : {},
			// template : '<div>' + '</div>',
			// template: function(element, attrs) { },
			// $templateCache.get('resources/templates/common/header.html')
			templateUrl : 'resources/templates/common/header.html',
			// compile : function(element, attrs, transclude) {
			// 	// do whatever else is necessary
			// 	var fieldGetter = $parse(attrs.field);
			//
			// 	return function (scope, element, attrs) {
			// 		var template, field, id;
			// 		field = fieldGetter(scope);
			// 		template = '..your dom structure here...';
			// 		alert($compile(template));
			// 		// element.replaceWith($compile(template)(scope));
			// 	}
			// },
			// scope: {
			// 	label: 'bind',
			// 	formId: 'bind'
			// 	data: '=info'
			// },
			link : link
			// controller : headerController
		}
	}).directive('footer', function($compile, $parse, $templateCache, CommonService) {

		// $scope, $element, $attrs
		function link($scope, $element, $attrs, $location) {

		}

		var footerController = function($scope, $location, $routeParams) {

		}

		return {
			// E = Element, A = Attribute, C = Class, M = Comment
			restrict : 'EA',
			replace : true,
			transclude : true,
			scope : {},
			// template : '<div>' + '</div>',
			// template: function(element, attrs) { },
			templateUrl : 'resources/templates/common/footer.html',
			// compile : function(element, attrs, transclude) {
			// 	// do whatever else is necessary
			// 	var fieldGetter = $parse(attrs.field);
			//
			// 	return function (scope, element, attrs) {
			// 		var template, field, id;
			// 		field = fieldGetter(scope);
			// 		template = '..your dom structure here...';
			// 		alert($compile(template));
			// 		// element.replaceWith($compile(template)(scope));
			// 	}
			// },
			// scope: {
			// 	label: 'bind',
			// 	formId: 'bind'
			// 	data: '=info'
			// },
			link : link
			// controller : footerController
		}
	});
	
});
