(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TeamStatsDialogController', TeamStatsDialogController);

    TeamStatsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TeamStats', 'Season'];

    function TeamStatsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TeamStats, Season) {
        var vm = this;

        vm.teamStats = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seasons = Season.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.teamStats.id !== null) {
                TeamStats.update(vm.teamStats, onSaveSuccess, onSaveError);
            } else {
                TeamStats.save(vm.teamStats, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('piratesflApp:teamStatsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
