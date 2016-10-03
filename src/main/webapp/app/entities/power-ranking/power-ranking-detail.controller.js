(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('PowerRankingDetailController', PowerRankingDetailController);

    PowerRankingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PowerRanking', 'Team', 'Season'];

    function PowerRankingDetailController($scope, $rootScope, $stateParams, previousState, entity, PowerRanking, Team, Season) {
        var vm = this;

        vm.powerRanking = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('piratesflApp:powerRankingUpdate', function(event, result) {
            vm.powerRanking = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
