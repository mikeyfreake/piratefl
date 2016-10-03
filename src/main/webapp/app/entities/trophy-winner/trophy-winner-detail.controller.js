(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('TrophyWinnerDetailController', TrophyWinnerDetailController);

    TrophyWinnerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TrophyWinner', 'Season', 'Team', 'Trophy'];

    function TrophyWinnerDetailController($scope, $rootScope, $stateParams, previousState, entity, TrophyWinner, Season, Team, Trophy) {
        var vm = this;

        vm.trophyWinner = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('piratesflApp:trophyWinnerUpdate', function(event, result) {
            vm.trophyWinner = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
