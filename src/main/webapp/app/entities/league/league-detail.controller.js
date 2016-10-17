(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('LeagueDetailController', LeagueDetailController);

    LeagueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'League', 'Season', 'Team', 'Trophy'];

    function LeagueDetailController($scope, $rootScope, $stateParams, previousState, entity, League, Season, Team, Trophy) {
        var vm = this;

        vm.league = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('piratesflApp:leagueUpdate', function(event, result) {
            vm.league = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
