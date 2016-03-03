(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('fileUpload', fileUpload);

    /** @ngInject */
    function fileUpload() {
        var directive = {
            restrict: 'E',
            scope: {
                extraValues: '='
            },
            templateUrl: 'app/components/fileUpload/fileUpload.html',
            controller: FileUploadController,
            controllerAs: 'vm'
        };

        return directive;

        /** @ngInject */
        function FileUploadController(Upload, $http) {
            var vm = this;

            vm.files = [];
            vm.upload = function (files) {
                if (files && files.length) {

                    for (var i = 0; i < files.length; i++) {
                        var file = files[i];
                        if (!file.$error) {
                            Upload.upload({
                                url: '/rest/fileUpload',
                                data: {"file": file}
                            });

                        }
                    }
                }
            };
        }
    }

})();
