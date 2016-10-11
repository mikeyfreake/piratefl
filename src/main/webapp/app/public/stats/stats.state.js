(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('stats', {
            parent: 'app',
            url: '/stats',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/public/stats/stats.html',
                    controller: 'StatsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
