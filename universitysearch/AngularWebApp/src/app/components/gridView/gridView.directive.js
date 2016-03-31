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


            vm.openFileModal = function (file) {
                modalFileViewer.openModal(file, $scope.files);
            };

            $scope.$on("FILES_CHANGED", function (evt, files) {
                vm.files = files;
            });

            $scope.$on("FILE_CHANGED", function (evt, file) {
                vm.approvedFile = file;
                vm.files = _.each (vm.files, function (file) {

                    if (vm.approvedFile == file.id) {
                        file.isApprov = true;
                    }
                });
            });

            $scope.$watch('courses', function () {
                console.log("courses loaded");
            })
        }
    }
})();
