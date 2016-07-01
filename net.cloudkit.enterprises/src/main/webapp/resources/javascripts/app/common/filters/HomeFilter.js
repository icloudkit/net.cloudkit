'use strict';

/* Filters */
define([ ], function () {
	
	angular.module('common.filters', []).filter('interpolate', [ 'version', function(version) {
		return function(text) {
			return String(text).replace(/\%VERSION\%/mg, version);
		}
	} ]);

});
