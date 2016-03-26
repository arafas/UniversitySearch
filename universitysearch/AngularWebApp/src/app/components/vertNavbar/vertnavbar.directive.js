/**
 * Created by syedaarafasulaiman on 2016-03-24.
 */


(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .directive('sideNavbar', sideNavbar);

  /** @ngInject */
  function sideNavbar() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/vertNavbar/vertnavbar.html',
      scope: {
        creationDate: '='
      },
      controller: sideNavbarController,
      controllerAs: 'side',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function sideNavbarController(moment,$http,$cookies,$location) {
      var vm = this;

      // "vm.creation" is avaible by directive option "bindToController: true"
      vm.relativeDate = moment(vm.creationDate).fromNow();
      //vm.user_info = $cookies.getObject('globals').currentUser.firstName; Needs to be commented out

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
  }

})();
