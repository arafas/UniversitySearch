(function () {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('ModalFileViewerController', ModalFileViewerController);
    /** @ngInject */

    function ModalFileViewerController($scope, $uibModalInstance, $log, $sce) {
        var vm = $scope;

        vm.ok = function () {
            $uibModalInstance.close();
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        }
    }
})();
