(function() {
  'use strict';

  angular
    .module('angularWebApp')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
