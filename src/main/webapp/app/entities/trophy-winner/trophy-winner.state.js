(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trophy-winner', {
            parent: 'entity',
            url: '/trophy-winner?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TrophyWinners'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trophy-winner/trophy-winners.html',
                    controller: 'TrophyWinnerController',
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
        .state('trophy-winner-detail', {
            parent: 'entity',
            url: '/trophy-winner/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TrophyWinner'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trophy-winner/trophy-winner-detail.html',
                    controller: 'TrophyWinnerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TrophyWinner', function($stateParams, TrophyWinner) {
                    return TrophyWinner.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trophy-winner',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trophy-winner-detail.edit', {
            parent: 'trophy-winner-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy-winner/trophy-winner-dialog.html',
                    controller: 'TrophyWinnerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrophyWinner', function(TrophyWinner) {
                            return TrophyWinner.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trophy-winner.new', {
            parent: 'trophy-winner',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy-winner/trophy-winner-dialog.html',
                    controller: 'TrophyWinnerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trophy-winner', null, { reload: 'trophy-winner' });
                }, function() {
                    $state.go('trophy-winner');
                });
            }]
        })
        .state('trophy-winner.edit', {
            parent: 'trophy-winner',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy-winner/trophy-winner-dialog.html',
                    controller: 'TrophyWinnerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrophyWinner', function(TrophyWinner) {
                            return TrophyWinner.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trophy-winner', null, { reload: 'trophy-winner' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trophy-winner.delete', {
            parent: 'trophy-winner',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy-winner/trophy-winner-delete-dialog.html',
                    controller: 'TrophyWinnerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TrophyWinner', function(TrophyWinner) {
                            return TrophyWinner.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trophy-winner', null, { reload: 'trophy-winner' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
