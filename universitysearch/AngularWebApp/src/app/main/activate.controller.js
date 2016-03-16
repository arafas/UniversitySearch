(function() {
	'use strict';

	angular
    .module('angularWebApp')
    .controller('ActivateController', ActivateController);

	/** @ngInject */

	function ActivateController($http, $routeParams) {
		var vm = this;

		var email = $routeParams.email;
		var hash = $routeParams.hash;

		var url = 'rest/activate/email/' + email + '/hash/' + hash;

		$http.get(url).success(function(data, status, headers, config) {
			vm.activateResponse = data;
		}).error(function(data, status, headers, config) {
			alert("AJAX failed to get data, status=" + status);
		});
	}
})();
