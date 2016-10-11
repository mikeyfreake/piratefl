(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('trophies', {
            parent: 'app',
            url: '/trophies',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/public/trophies/trophies.html',
                    controller: 'TrophiesController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
