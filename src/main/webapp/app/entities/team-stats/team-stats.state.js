(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('team-stats', {
            parent: 'entity',
            url: '/team-stats?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TeamStats'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/team-stats/team-stats.html',
                    controller: 'TeamStatsController',
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
        .state('team-stats-detail', {
            parent: 'entity',
            url: '/team-stats/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TeamStats'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/team-stats/team-stats-detail.html',
                    controller: 'TeamStatsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TeamStats', function($stateParams, TeamStats) {
                    return TeamStats.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'team-stats',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('team-stats-detail.edit', {
            parent: 'team-stats-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/team-stats/team-stats-dialog.html',
                    controller: 'TeamStatsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TeamStats', function(TeamStats) {
                            return TeamStats.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('team-stats.new', {
            parent: 'team-stats',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/team-stats/team-stats-dialog.html',
                    controller: 'TeamStatsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                wins: null,
                                losses: null,
                                ties: null,
                                pointsFor: null,
                                pointsAgainst: null,
                                draftPosition: null,
                                finished: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('team-stats', null, { reload: 'team-stats' });
                }, function() {
                    $state.go('team-stats');
                });
            }]
        })
        .state('team-stats.edit', {
            parent: 'team-stats',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/team-stats/team-stats-dialog.html',
                    controller: 'TeamStatsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TeamStats', function(TeamStats) {
                            return TeamStats.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('team-stats', null, { reload: 'team-stats' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('team-stats.delete', {
            parent: 'team-stats',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/team-stats/team-stats-delete-dialog.html',
                    controller: 'TeamStatsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TeamStats', function(TeamStats) {
                            return TeamStats.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('team-stats', null, { reload: 'team-stats' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
