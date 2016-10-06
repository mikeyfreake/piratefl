(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('rankings', {
            parent: 'app',
            url: '/rankings',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/public/rankings/rankings.html',
                    controller: 'RankingsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
