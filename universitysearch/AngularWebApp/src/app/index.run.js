(function() {
	'use strict';

	angular.module('angularWebApp').run(runBlock);

	/** @ngInject */
	function runBlock($log, $rootScope, $cookies, $http, $location) {

		$log.debug('runBlock end');
		$rootScope.globals = $cookies.get('globals') || {};
		if ($rootScope.globals.currentUser) {
			$http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.username; // jshint
																// ignore:line
		}

		var redirectIfNotLoggedIn = $rootScope.$on('$locationChangeStart', redirect);
		
		$rootScope.$on('$destroy', redirectIfNotLoggedIn);

		function redirect() {
			// redirect to login page if attempting to access user home page but
			// not logged in
			if ($location.path() == '/home' && !$cookies.getObject('globals')) {
				$location.path('/');
			}
		}

	}

})();
