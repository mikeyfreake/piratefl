(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('constitution', {
            parent: 'app',
            url: '/constitution',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/public/constitution/constitution.html',
                    controller: 'ConstitutionController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
