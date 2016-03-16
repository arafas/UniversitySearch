(function () {
    'use strict';

    angular
        .module('angularWebApp')
        .directive('searchBar', searchBar);

    /** @ngInject */
    function searchBar() {
        var directive = {
            restrict: 'E',
            scope: {
                extraValues: '='
            },
            templateUrl: 'app/components/searchBar/searchBar.html',
            controller: SearchBarController,
            controllerAs: 'sbc'
        };

        return directive;

        /** @ngInject */
        function SearchBarController($http, $log, $location) {
            var vm = this;
            vm.selectedTerm = null;
            vm.search = search;
            vm.onSelect = onSelect;

            function search(val) {
                var url = "rest/API/search/" + val;
                return $http.get(url)
                    .then(function (response) {
                        var returnArr = [];

                        if (_.size(response.data.courses) > 0) {
                            returnArr.push({"course_divider": true});
                             _.each(response.data.courses, function (course) {
                                return returnArr.push(course);
                            })
                        }

                        if (_.size(response.data.files) > 0) {
                            returnArr.push({"file_divider": true});
                             _.each(response.data.files, function (file) {
                                return returnArr.push(file);
                            })
                        }
                        return returnArr;
                    });
            }

            function onSelect (item, model, label) {
                // TODO: make a call to either load the file selected or route to the course selected
                $location.path("/about");
                $log.log(item);
            }
        }
    }
})();
