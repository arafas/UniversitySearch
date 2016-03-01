(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .config(routeConfig);

  function routeConfig($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'main'
      })
      .when('/register', {
        templateUrl: 'app/main/register.html',
        controller: 'RegisterController',
        controllerAs: 'reg'
      })
      .otherwise({
        redirectTo: '/'
      });
  }

})();
