(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TeamStatsDeleteController',TeamStatsDeleteController);

    TeamStatsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TeamStats'];

    function TeamStatsDeleteController($uibModalInstance, entity, TeamStats) {
        var vm = this;

        vm.teamStats = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TeamStats.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
