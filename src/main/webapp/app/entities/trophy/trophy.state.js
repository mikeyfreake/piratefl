(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('trophy', {
            parent: 'entity',
            url: '/trophy?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Trophies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trophy/trophies.html',
                    controller: 'TrophyController',
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
        .state('trophy-detail', {
            parent: 'entity',
            url: '/trophy/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Trophy'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/trophy/trophy-detail.html',
                    controller: 'TrophyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Trophy', function($stateParams, Trophy) {
                    return Trophy.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'trophy',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('trophy-detail.edit', {
            parent: 'trophy-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy/trophy-dialog.html',
                    controller: 'TrophyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trophy', function(Trophy) {
                            return Trophy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trophy.new', {
            parent: 'trophy',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy/trophy-dialog.html',
                    controller: 'TrophyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                trophyName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('trophy', null, { reload: 'trophy' });
                }, function() {
                    $state.go('trophy');
                });
            }]
        })
        .state('trophy.edit', {
            parent: 'trophy',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy/trophy-dialog.html',
                    controller: 'TrophyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Trophy', function(Trophy) {
                            return Trophy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trophy', null, { reload: 'trophy' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('trophy.delete', {
            parent: 'trophy',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/trophy/trophy-delete-dialog.html',
                    controller: 'TrophyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Trophy', function(Trophy) {
                            return Trophy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('trophy', null, { reload: 'trophy' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
