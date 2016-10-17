(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('PowerRankingDeleteController',PowerRankingDeleteController);

    PowerRankingDeleteController.$inject = ['$uibModalInstance', 'entity', 'PowerRanking'];

    function PowerRankingDeleteController($uibModalInstance, entity, PowerRanking) {
        var vm = this;

        vm.powerRanking = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PowerRanking.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
