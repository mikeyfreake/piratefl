(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('SeasonDialogController', SeasonDialogController);

    SeasonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Season', 'League', 'TeamStats', 'PowerRanking', 'TrophyWinner'];

    function SeasonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Season, League, TeamStats, PowerRanking, TrophyWinner) {
        var vm = this;

        vm.season = entity;
        vm.clear = clear;
        vm.save = save;
        vm.leagues = League.query();
        vm.teamstats = TeamStats.query();
        vm.powerrankings = PowerRanking.query();
        vm.trophywinners = TrophyWinner.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.season.id !== null) {
                Season.update(vm.season, onSaveSuccess, onSaveError);
            } else {
                Season.save(vm.season, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('piratesflApp:seasonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
