(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('LeagueDialogController', LeagueDialogController);

    LeagueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'League', 'Season', 'Team', 'Trophy'];

    function LeagueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, League, Season, Team, Trophy) {
        var vm = this;

        vm.league = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seasons = Season.query();
        vm.teams = Team.query();
        vm.trophies = Trophy.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.league.id !== null) {
                League.update(vm.league, onSaveSuccess, onSaveError);
            } else {
                League.save(vm.league, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('piratesflApp:leagueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
