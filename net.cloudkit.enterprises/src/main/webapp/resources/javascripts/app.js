
define([ 'angular', 'angular-cookies', 'angular-animate', 'angular-resource', 'angular-route', 'angular-ui-router', 'angular-ui-bootstrap', 'jQuery', 'bootstrap', 'app/common/filters/HomeFilter', 'app/common/controllers/HomeController', 'app/common/services/HomeService', 'app/common/directives/HomeDirective' ], function (angular) {

    'use strict';

    // http://docs.angularjs.org/api
    // http://angularjs.cn/A007

    // angular.module(name, [requires], configFn);
    // Declare app level module which depends on filters, and services
    // Then you can create an injector and load your modules like this: var injector
    // = angular.injector(['ng', 'myModule'])
    // 'ngMock', 'ngMockE2E',
    return angular.module('app', [ 'ngCookies', 'ngAnimate', 'ngResource', 'ngRoute', 'ui.router', 'ui.bootstrap', 'common.filters', 'common.services', 'common.directives', 'common.controllers', "bootstrap.tpls" ]);

});
