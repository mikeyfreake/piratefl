(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('ConstitutionController', ConstitutionController);

    ConstitutionController.$inject = ['$scope', 'League', 'AlertService', '$state'];

    function ConstitutionController ($scope, League, AlertService, $state) {
        var vm = this;
        
        loadAll();
        
        function load() {
        	vm.league = League.get();
        }
        
        function loadAll () {
            League.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                try {
                	vm.league = data[0];
                } catch (e) {
                	AlertService.error(e);
                }
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
