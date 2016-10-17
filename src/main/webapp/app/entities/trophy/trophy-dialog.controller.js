(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TrophyDialogController', TrophyDialogController);

    TrophyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trophy', 'League'];

    function TrophyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Trophy, League) {
        var vm = this;

        vm.trophy = entity;
        vm.clear = clear;
        vm.save = save;
        vm.leagues = League.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trophy.id !== null) {
                Trophy.update(vm.trophy, onSaveSuccess, onSaveError);
            } else {
                Trophy.save(vm.trophy, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('piratesflApp:trophyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
