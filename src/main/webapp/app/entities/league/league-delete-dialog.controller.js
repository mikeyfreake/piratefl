(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('LeagueDeleteController',LeagueDeleteController);

    LeagueDeleteController.$inject = ['$uibModalInstance', 'entity', 'League'];

    function LeagueDeleteController($uibModalInstance, entity, League) {
        var vm = this;

        vm.league = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            League.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
