(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('gridView', gridView);

    /** @ngInject */
    function gridView() {
        var directive = {
            restrict: 'E',
            scope: {
                courses: '=',
                files: '='
            },
            templateUrl: 'app/components/gridView/gridView.html',
            controller: GridViewController,
            controllerAs: 'gvc'
        };

        return directive;

        /** @ngInject */
        function GridViewController($scope, modalFileViewer) {
            var vm = this;

            vm.files = $scope.files;
            vm.courses = $scope.courses;

            vm.trustSrc = function(src) {
                return $sce.trustAsResourceUrl(src);
            };

            vm.getFileURI = function (file) {
                var URIprefix = 'http://localhost:8081/static/files/';
                return URIprefix + file.fileName;
            };

            vm.openFileModal = function (file) {
                modalFileViewer.openModal(vm.getFileURI(file));
            };
        }
    }
})();
