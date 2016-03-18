(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .service('modalFileViewer', modalFileViewer);

    /** @ngInject */
    function modalFileViewer($rootScope, $uibModal) {

        this.openModal = openModal;

        function openModal (filePath) {

            var scope = $rootScope.$new();
            scope.params = {filePath: filePath};
            var modalInstance = $uibModal.open({
                scope: scope,
                animation: true,
                templateUrl: 'app/components/modalFileViewer/modalContent.html',
                controller: 'ModalFileViewerController',
                size: 'xl',
                windowClass: 'fileViewer'
            });
        }
    }
})();
