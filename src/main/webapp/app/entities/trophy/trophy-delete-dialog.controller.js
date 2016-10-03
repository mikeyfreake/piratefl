(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TrophyDeleteController',TrophyDeleteController);

    TrophyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trophy'];

    function TrophyDeleteController($uibModalInstance, entity, Trophy) {
        var vm = this;

        vm.trophy = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Trophy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
