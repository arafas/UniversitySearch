(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .controller('RegisterController', RegisterController);
  /** @ngInject */
  function RegisterController($timeout, webDevTec, toastr, $location, $http) {
    var vm = this;

    vm.awesomeThings = [];
    vm.classAnimation = '';
    vm.creationDate = 1456267200629;
    vm.showToastr = showToastr;
    vm.submit = submit;

    vm.users=[{username:'sulaim11',password:'123'},
      {username:'user',password:'p'}];
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

    function submit(){

        window.alert("sign up");
        $location.path("/sent");

    }



  }
})();
