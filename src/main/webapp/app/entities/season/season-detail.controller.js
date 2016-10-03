(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('SeasonDetailController', SeasonDetailController);

    SeasonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Season', 'League', 'TeamStats', 'PowerRanking', 'TrophyWinner'];

    function SeasonDetailController($scope, $rootScope, $stateParams, previousState, entity, Season, League, TeamStats, PowerRanking, TrophyWinner) {
        var vm = this;

        vm.season = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('piratesflApp:seasonUpdate', function(event, result) {
            vm.season = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
