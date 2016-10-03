(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TrophyWinnerDialogController', TrophyWinnerDialogController);

    TrophyWinnerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TrophyWinner', 'Season', 'Team', 'Trophy'];

    function TrophyWinnerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TrophyWinner, Season, Team, Trophy) {
        var vm = this;

        vm.trophyWinner = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seasons = Season.query();
        vm.teams = Team.query({filter: 'trophywinner-is-null'});
        $q.all([vm.trophyWinner.$promise, vm.teams.$promise]).then(function() {
            if (!vm.trophyWinner.team || !vm.trophyWinner.team.id) {
                return $q.reject();
            }
            return Team.get({id : vm.trophyWinner.team.id}).$promise;
        }).then(function(team) {
            vm.teams.push(team);
        });
        vm.trophies = Trophy.query({filter: 'trophywinner-is-null'});
        $q.all([vm.trophyWinner.$promise, vm.trophies.$promise]).then(function() {
            if (!vm.trophyWinner.trophy || !vm.trophyWinner.trophy.id) {
                return $q.reject();
            }
            return Trophy.get({id : vm.trophyWinner.trophy.id}).$promise;
        }).then(function(trophy) {
            vm.trophies.push(trophy);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trophyWinner.id !== null) {
                TrophyWinner.update(vm.trophyWinner, onSaveSuccess, onSaveError);
            } else {
                TrophyWinner.save(vm.trophyWinner, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('piratesflApp:trophyWinnerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
