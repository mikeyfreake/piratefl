(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TrophyDetailController', TrophyDetailController);

    TrophyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trophy', 'League'];

    function TrophyDetailController($scope, $rootScope, $stateParams, previousState, entity, Trophy, League) {
        var vm = this;

        vm.trophy = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('piratesflApp:trophyUpdate', function(event, result) {
            vm.trophy = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
