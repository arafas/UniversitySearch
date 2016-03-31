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

        $rootScope.$on( "$routeChangeStart", function(event, next, current) {
            console.log(next);
            console.log($rootScope.globals);
            if ( $rootScope.globals.currentUser == null ) {
                // no logged user, we should be going to #login
                if ( next.templateUrl == "/" ) {
                    // already going to #login, no redirect needed
                } else {
                    // not going to #login, we should redirect now
                    $location.path( "/" );
                }
            }
        });

		$rootScope.$on('$destroy', redirectIfNotLoggedIn);

		function redirect() {
			// redirect to login page if attempting to access user home page but
			// not logged in
            // register listener to watch route changes

            //if ($location.path() == '/home' && !$cookies.getObject('globals')) {
             //   $location.path('/');
            //}
            //if ($location.path() == '/manageFile' && !$cookies.getObject('globals')) {
            //$location.path('/');
            //}
            //if ($location.path() == '/uploadFile' && !$cookies.getObject('globals')) {
            //$location.path('/');
            //}
            //if ($location.path() == '/advancedSearch' && !$cookies.getObject('globals')) {
             //   $location.path('/');
            //}
      /*if ($location.path() == '/advancedSearch' && !$cookies.getObject('globals')) {
        $location.path('/');
      }*/
		}

	}

})();
