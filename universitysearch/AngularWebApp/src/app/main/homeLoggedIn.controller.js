/**
 * Created by syedaarafasulaiman on 2016-03-03.
 */
(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .controller('HomeLoggedInController', HomeLoggedInController);
  /** @ngInject */
  function HomeLoggedInController($timeout, webDevTec, toastr, $rootScope, $cookies, $location, $http) {
    var vm = this;

    vm.awesomeThings = [];
    vm.classAnimation = '';
    vm.creationDate = 1456267200629;
    vm.showToastr = showToastr;
    vm.logout = logout;
    vm.user_info = $cookies.getObject('globals').currentUser.firstName;//.username;
    //vm.user = $rootScope.globals.currentUser;

    //vm.users=[{username:'sulaim11',password:'123'},
      //{username:'user',password:'p'}];
    vm.loggedin = [];

    vm.orderProp = 'age';

    activate();

    function activate() {
      getWebDevTec();
      $timeout(function () {
        vm.classAnimation = 'rubberBand';
      }, 4000);
    }

    function showToastr() {
      toastr.info('Fork <a href="https://github.com/Swiip/generator-gulp-angular" target="_blank"><b>generator-gulp-angular</b></a>');
      vm.classAnimation = '';

    }

    function getWebDevTec() {
      vm.awesomeThings = webDevTec.getTec();

      angular.forEach(vm.awesomeThings, function (awesomeThing) {
        awesomeThing.rank = Math.random();
      });
    }

    function logout(){

      //var globalCookie = $cookie.get('globals');
      $http.post('/rest/API/signOut')
          .then(function () {
            $rootScope.globals = {};
            //$cookies.put('globals', $rootScope.globals);
            $cookies.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
            //$cookies.globals = $rootScope.globals;
            $location.path("/");
          });
    }





  }
})();

