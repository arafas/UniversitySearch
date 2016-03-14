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
      .when('/sent', {
        templateUrl: 'app/main/email.html',
        controller: 'VerifyController',
        controllerAs: 'verify'
      })
      .when('/about',{
        templateUrl: 'app/main/about.html',
        controller: 'AboutController',
        controllerAs: 'about'
      })
      .when('/contact', {
        templateUrl: 'app/main/contact.html',
        controller: 'ContactController',
        controllerAs: 'cont'
      })
      .when('/home', {
        templateUrl: 'app/main/homeLoggedIn.html',
        controller: 'HomeLoggedInController',
        controllerAs: 'home'
      })
      .when('/activate/email/:email/hash/:hash', {
        templateUrl: 'app/main/activate.html',
        controller: 'ActivateController',
        controllerAs: 'activate'
      })
      .otherwise({
        redirectTo: '/'
      });

   // $locationProvider.html5Mode(true);
  }



})();
