(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .controller('MainController', MainController);
  /** @ngInject */
  function MainController($timeout, webDevTec, toastr, $location, $window, $cookies, $rootScope) {
    var vm = this;

    vm.url = $location.absUrl;
    vm.awesomeThings = [];
    vm.classAnimation = '';
    vm.creationDate = 1456267200629;
    vm.showToastr = showToastr;
    vm.submitCredentials = submitCredentials;
    vm.register = register;
    vm.names = ['Egbert', 'Edwina', 'Peanut Butter'];
    vm.phones = [
      {'name': 'Nexus S',
        'snippet': 'Fast just got faster with Nexus S.', 'age':3},
      {'name': 'Motorola XOOM™ with Wi-Fi',
        'snippet': 'The Next, Next Generation tablet.', 'age':1},
      {'name': 'MOTOROLA XOOM™',
        'snippet': 'The Next, Next Generation tablet.', 'age':5}
    ];
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
      //$window.alert("sign up");
      //window.location(src/main/register.html);

    }

    function getWebDevTec() {
      vm.awesomeThings = webDevTec.getTec();

      angular.forEach(vm.awesomeThings, function (awesomeThing) {
        awesomeThing.rank = Math.random();
      });
    }

    function submitCredentials(username, password){
      var found = 0;
      var user, pass;

      angular.forEach(vm.users,function(value) {

        if (value.username == username && value.password == password) {

          found = 1;
          user = value.username;
          pass = value.password;
          vm.loggedin.push({username:user,password:pass});

        }
      })


      if(found == 1) {
          $window.alert("welcome "+user);


          $rootScope.globals = {
            currentUser: {
            username: username
            }
          };
          $cookies.put('globals', $rootScope.globals);
          $location.path("/home");


      }else{
          $window.alert("Invalid Login");
      }


    }

    function register() {
      $window.alert("sign up now");
      //window.location = "register.html";
      $location.path("/register");
    }

  }
})();
