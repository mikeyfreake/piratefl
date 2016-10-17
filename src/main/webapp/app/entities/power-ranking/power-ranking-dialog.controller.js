(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('PowerRankingDialogController', PowerRankingDialogController);

    PowerRankingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PowerRanking', 'Team', 'Season'];

    function PowerRankingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PowerRanking, Team, Season) {
        var vm = this;

        vm.powerRanking = entity;
        vm.clear = clear;
        vm.save = save;
        vm.teams = Team.query();
        vm.seasons = Season.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.powerRanking.id !== null) {
                PowerRanking.update(vm.powerRanking, onSaveSuccess, onSaveError);
            } else {
                PowerRanking.save(vm.powerRanking, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('piratesflApp:powerRankingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
