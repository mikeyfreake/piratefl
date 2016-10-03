(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TrophyWinnerDeleteController',TrophyWinnerDeleteController);

    TrophyWinnerDeleteController.$inject = ['$uibModalInstance', 'entity', 'TrophyWinner'];

    function TrophyWinnerDeleteController($uibModalInstance, entity, TrophyWinner) {
        var vm = this;

        vm.trophyWinner = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TrophyWinner.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
