(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .service('modalFileViewer', modalFileViewer);

    /** @ngInject */
    function modalFileViewer($rootScope, $uibModal) {
        var vm = this;

        this.openModal = openModal;

        vm.getFileURI = function (fileName) {
            var URIprefix = 'http://localhost:8081/static/files/';
            return URIprefix + fileName;
        };


        function openModal (file) {
            console.log(file);
            var filePath = vm.getFileURI(file.fileName);

            var scope = $rootScope.$new();
            scope.params = {filePath: filePath, fileId: file.id, courseId: file.courseId, fileName: file.fileName};
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
