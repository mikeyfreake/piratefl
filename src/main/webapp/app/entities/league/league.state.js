(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('league', {
            parent: 'entity',
            url: '/league?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Leagues'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/league/leagues.html',
                    controller: 'LeagueController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('league-detail', {
            parent: 'entity',
            url: '/league/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'League'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/league/league-detail.html',
                    controller: 'LeagueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'League', function($stateParams, League) {
                    return League.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'league',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('league-detail.edit', {
            parent: 'league-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/league/league-dialog.html',
                    controller: 'LeagueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['League', function(League) {
                            return League.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('league.new', {
            parent: 'league',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/league/league-dialog.html',
                    controller: 'LeagueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                leagueName: null,
                                constitution: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('league', null, { reload: 'league' });
                }, function() {
                    $state.go('league');
                });
            }]
        })
        .state('league.edit', {
            parent: 'league',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/league/league-dialog.html',
                    controller: 'LeagueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['League', function(League) {
                            return League.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('league', null, { reload: 'league' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('league.delete', {
            parent: 'league',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/league/league-delete-dialog.html',
                    controller: 'LeagueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['League', function(League) {
                            return League.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('league', null, { reload: 'league' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
