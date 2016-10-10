(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('power-ranking', {
            parent: 'entity',
            url: '/power-ranking?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PowerRankings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/power-ranking/power-rankings.html',
                    controller: 'PowerRankingController',
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
        .state('power-ranking-detail', {
            parent: 'entity',
            url: '/power-ranking/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PowerRanking'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/power-ranking/power-ranking-detail.html',
                    controller: 'PowerRankingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PowerRanking', function($stateParams, PowerRanking) {
                    return PowerRanking.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'power-ranking',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('power-ranking-detail.edit', {
            parent: 'power-ranking-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/power-ranking/power-ranking-dialog.html',
                    controller: 'PowerRankingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PowerRanking', function(PowerRanking) {
                            return PowerRanking.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('power-ranking.new', {
            parent: 'power-ranking',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/power-ranking/power-ranking-dialog.html',
                    controller: 'PowerRankingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                week: null,
                                rank: null,
                                comments: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('power-ranking', null, { reload: 'power-ranking' });
                }, function() {
                    $state.go('power-ranking');
                });
            }]
        })
        .state('power-ranking.edit', {
            parent: 'power-ranking',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/power-ranking/power-ranking-dialog.html',
                    controller: 'PowerRankingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PowerRanking', function(PowerRanking) {
                            return PowerRanking.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('power-ranking', null, { reload: 'power-ranking' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('power-ranking.delete', {
            parent: 'power-ranking',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/power-ranking/power-ranking-delete-dialog.html',
                    controller: 'PowerRankingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PowerRanking', function(PowerRanking) {
                            return PowerRanking.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('power-ranking', null, { reload: 'power-ranking' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
