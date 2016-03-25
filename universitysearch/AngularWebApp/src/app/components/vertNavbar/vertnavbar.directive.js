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
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    /** @ngInject */
    function sideNavbarController(moment) {
      var vm = this;

      // "vm.creation" is avaible by directive option "bindToController: true"
      vm.relativeDate = moment(vm.creationDate).fromNow();
    }
  }

})();
