'use strict';

/* Controllers */
define([ ], function () {

	angular.module('common.controllers', ['ngResource']).controller('MyCtrl1', [ '$scope', function($scope, $templateCache) {
		
		
	} ]).controller('NavigationController', function($scope, $http, $route, $routeParams, $compile, $location) {
		// console.log($location.path());
		$route.current.templateUrl = '../resources/templates/' + $routeParams.url + ".html";
		
		$http.get($route.current.templateUrl).then(function (result) {
			$('#views').html($compile(result.data)($scope));
			
		});
		
	}).controller('PaginationDemoCtrl', function($scope, $route, $routeParams, $location) {
		
		$scope.setPage = function (pageNo) {
			$scope.currentPage = pageNo;
		};

		$scope.pageChanged = function() {
			console.log('Page changed to: ' + $scope.currentPage);
		};

		// 显示5个分页button
		$scope.maxSize = 5;

		$scope.itemsPerPage = 20;

		$scope.totalItems = 201;
		$scope.currentPage = 1;

		$scope.users = [{
			id : '0',
			first_name : 'Hong',
			last_name : 'Quanli',
			username : '@hongquanli',

		}, {
			id : '1',
			first_name : 'Mark',
			last_name : 'Otto',
			username : '@mdo'
		}, {
			id : '2',
			first_name : 'Jacob',
			last_name : 'Thornton',
			username : '@fat'
		}, {
			id : '3',
			first_name : 'Larry',
			last_name : 'the Bird',
			username : '@twitter'
		}];

	}).controller('DatepickerDemoCtrl', function($scope, $route, $routeParams, $location) {
		
		$scope.today = function() {
			$scope.dt = new Date();
		};
		// $scope.today();

		$scope.clear = function () {
			$scope.dt = null;
		};

		// Disable weekend selection
		$scope.disabled = function(date, mode) {
			// return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
			return false;
		};

		$scope.toggleMin = function() {
			$scope.minDate = $scope.minDate ? null : new Date();
		};
		// $scope.toggleMin();

		// $scope.maxDate = '2015-06-22';

		$scope.open = function($event) {
			$event.preventDefault();
			$event.stopPropagation();

			$scope.opened = true;
		};

		$scope.dateOptions = {
			formatYear: 'yyyy',
			startingDay: 1
		};

		// $scope.initDate = new Date('2016-15-20');
		// $scope.formats = ['yyyy-MM-dd', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
		// $scope.format = $scope.formats[0];
		$scope.format = 'yyyy-MM-dd';

	}).controller('MyCtrl2', function($scope, $route, $routeParams, $location) {
		
		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;
		
		// $scope.name = "BookCntl";
		// $scope.params = $routeParams;
	}).controller('MyController', function($scope) {
	
		$scope.person = {
			name : "Ari Lerner"
		};
	}).controller('AlertDemoCtrl', function($scope, $http) {
	
		$scope.alerts = [ {
			type : 'danger',
			msg : 'Oh snap! Change a few things up and try submitting again.'
		}, {
			type : 'success',
			msg : 'Well done! You successfully read this important alert message.'
		} ];
	
		$scope.addAlert = function() {
			$http({  
				method: 'JSONP',  
				url: 'http://www.cloudkit.net/json?callback=JSON_CALLBACK'
			}).success(function(data, status, headers, config) {  
				// data contains the response  
				// status is the HTTP status  
				// headers is the header getter function  
				// config is the object that was used to create the HTTP request
				// alert(data);
			}).error(function(data, status, headers, config) {
				// alert();
			});
			
			$scope.alerts.push({
				msg : "Another alert!"
			});
		};
	
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
	}).controller("SomeController", function($scope) {
		
	    $scope.expanders = [{
	        title : 'Click me to expand',
	        text : 'Hi there folks, I am the content that was hidden but is now shown.'
	    }, {
	        title : 'Click this',
	        text : 'I am even better text than you have seen previously'
	    }, {
	        title : 'Test',
	        text : 'test'
	    }];
	}).controller("CurrentController", function($scope) {
		
	    $scope.format = 'yyyy-MM-dd HH:mm:ss';
	    
	}).constant('topbarConfig', {
		
		// 常量
		activeClass: 'active',
		toggleEvent: 'click'
	});

});
