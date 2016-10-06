(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('ConstitutionController', ConstitutionController);

    ConstitutionController.$inject = ['$scope', 'League', '$state'];

    function ConstitutionController ($scope, League, $state) {
        var vm = this;
        
        load();
        
        function load() {
        	vm.league = League.get({id: 1});
        }
    }
})();
