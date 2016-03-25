(function () {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('ModalFileViewerController', ModalFileViewerController);
    /** @ngInject */

    function ModalFileViewerController($scope, $uibModalInstance, $http, Upload, $sce) {
        var vm = $scope;
        vm.tags = [];
        vm.oldTags = [];

        vm.getTags = function () {
            $http({
                    method: "GET",
                    url: '/rest/API/getTags/' + vm.params.fileId
                })
                .success(function(data) {
                    _.each(data, function(tag) {
                        vm.oldTags.push(_.pick(tag, 'text'));
                        vm.tags.push(_.pick(tag, 'text'));
                        console.log(vm.tags);
                    });
                    console.log(vm.oldTags);
                })
                .error(function() {

                })
        };

        vm.submitTags = function () {
            vm.newTags = [];
            var lenOld = _.allKeys(vm.oldTags);
            var lenNew = _.allKeys(vm.tags);

            var i,j;
            var found = false;
            for ( i=0; i < lenNew.length; i++){
                found = false;
                for (j=0; j<lenOld.length; j++) {
                    if (vm.tags[i].text == vm.oldTags[j].text) {
                        found = true;
                    }
                }
                if (!found) {
                    vm.newTags.push(vm.tags[i]);
                }
            }

            Upload.upload({
                    url: '/rest/API/addTags/' + vm.params.fileId,
                    data: {tags: JSON.stringify(vm.newTags)}
                })
                .success(function() {
                    vm.fileUploadSuccess = true;
                })
                .error(function() {
                    vm.fileUploadError = true;
                })
        };

        vm.ok = function () {
            $uibModalInstance.close();
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.trustSrc = function(src) {
            return $sce.trustAsResourceUrl(src);
        };

        vm.getTags();
    }
})();
