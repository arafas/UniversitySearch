/**
 * Created by syedaarafasulaiman on 2016-03-02.
 */
(function() {
    'use strict';

    angular
        .module('angularWebApp')
        .controller('AddDeleteCourseController', AddDeleteCourseController);
    /** @ngInject */
    function AddDeleteCourseController($http) {
        var vm = this;
        vm.courses = [];
        vm.selectedCourse ="";

        $http.get('/rest/API/coursesForProf')
            .then(function (resp) {
                console.log(resp);
                vm.courses = resp.data;
                console.log(vm.courses);

            });

        vm.addCourse = function(course) {
            course.courseCode = course.courseCode.toUpperCase();
            return $http.post('rest/API/addCourse', course)
                .then(function (resp) {
                    console.log(resp);
                    vm.courses.push(resp.data);
                    console.log(vm.courses);
                });
        };

        vm.deleteCourse = function() {
            return $http.post('rest/API/deleteCourse/' + vm.selectedCourse.id)
                .then(function (resp) {
                    vm.courses = _.reject (vm.courses, function (course) {
                        return (course.id == vm.selectedCourse.id);
                    })
                });
        };

    }
})();
