(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('fileUpload', fileUpload)
        .controller('FileUploadController', FileUploadController);

    /** @ngInject */
    function fileUpload() {
        var directive = {
            restrict: 'E',
            scope: {
                extraValues: '='
            },
            templateUrl: 'app/components/fileUpload/templateToRenderModal.html',
            controller: FileUploadController,
            controllerAs: 'vm'
        };

        return directive;



    }
    /** @ngInject */
    function FileUploadController(Upload, fileUploadModal) {
        var vm = this;
        vm.progressPercentage = 0;
        vm.fileUploadSuccess = false;
        vm.fileUploadError = false;
        vm.selectedCourse = null;


        vm.courses = [{courseId: 1, courseCode: "CSC301", courseName: "Software Engineering", profId: 3},
            {courseId: 2, courseCode: "CSC427", courseName: "Computer Security", profId: 2}];

        vm.tags = [];

        vm.upload = function (files) {
            console.log(files);
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    if (!file.$error) {
                        vm.fileUploadSuccess = false;
                        vm.fileUploadError = false;
                        console.log("adding course");
                        Upload.upload({
                            url: '/rest/API/fileUpload/' + vm.selectedCourse.courseId,
                            data: {"file": file, "tags": JSON.stringify(vm.tags)}
                        })
                            .success(function() {
                                vm.fileUploadSuccess = true;
                            })
                            .error(function() {
                                vm.fileUploadError = true;
                            })
                    }
                }
            }
        };

        vm.openModal = function() {
            fileUploadModal.openModal();
        };
    }

})();
