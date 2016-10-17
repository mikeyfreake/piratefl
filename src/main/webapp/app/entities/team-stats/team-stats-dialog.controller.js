(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TeamStatsDialogController', TeamStatsDialogController);

    TeamStatsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TeamStats', 'Season', 'Team'];

    function TeamStatsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TeamStats, Season, Team) {
        var vm = this;

        vm.teamStats = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seasons = Season.query();
        vm.teams = Team.query({filter: 'teamstats-is-null'});
        $q.all([vm.teamStats.$promise, vm.teams.$promise]).then(function() {
            if (!vm.teamStats.team || !vm.teamStats.team.id) {
                return $q.reject();
            }
            return Team.get({id : vm.teamStats.team.id}).$promise;
        }).then(function(team) {
            vm.teams.push(team);
        });

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
