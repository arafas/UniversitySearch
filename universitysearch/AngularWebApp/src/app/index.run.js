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


        $rootScope.$on( "$routeChangeStart", function(event, next, current) {
            console.log(next.templateUrl);
            console.log($rootScope.globals);
            if ( $rootScope.globals.currentUser == null ) {
                // no logged user, we should be going to #login

                if ( next.templateUrl == "app/main/register.html" ) {
                    $location.path( "register" );
                    // already going to #login, no redirect needed
                } else {
                    // not going to #login, we should redirect now
                    $location.path( "/" );
                }
            }
        });

		}
})();
