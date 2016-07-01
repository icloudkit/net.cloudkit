define(['app'], function (app) {

	'use strict';

	return app.config([ '$routeProvider', '$locationProvider', '$stateProvider', function($routeProvider, $locationProvider, $stateProvider) {

		FastClick.attach(document.body);
		// delete $httpProvider.defaults.headers.common['X-Requested-With'];

		// partials
		$routeProvider.when('/', {
			templateUrl: 'resources/templates/blank.html'
		}).when('/:name/:url', {
			templateUrl: 'resources/templates/blank.html', controller: 'NavigationController'
		}).otherwise({
			// redirectTo: '/'
		});

		// TODO 需要History API支持

		// 让路径的前面加个叹号
		// Configure existing providers
		// $locationProvider.hashPrefix('!')

		/*
		// 不让链接里面带有符号"#"
		configure html5 to get links working on jsfiddle
		$locationProvider.html5Mode(true);
		*/


		$stateProvider.state('index', {
			url: "",
			views: {
				"viewA": { template: "index.viewA" },
				"viewB": { template: "index.viewB" }
			}
		})
		.state('route1', {
			url: "/route1",
			views: {
				"viewA": { template: "route1.viewA" },
				"viewB": { template: "route1.viewB" }
			}
		})
		.state('route2', {
			url: "/route2",
			views: {
				"viewA": { template: "route2.viewA" },
				"viewB": { template: "route2.viewB" }
			}
		})
		.state('state1', {
			url: "/state1",
			// partials
			templateUrl: "resources/templates/partial2.html"
		})
		//.state('state1.list', {
		//	url: "/list",
		//	templateUrl: "partials/state1.list.html",
		//	controller: function($scope) {
		//		$scope.items = ["A", "List", "Of", "Items"];
		//	}
		//})
	} ]);
});