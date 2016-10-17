(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('season', {
            parent: 'entity',
            url: '/season?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Seasons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/season/seasons.html',
                    controller: 'SeasonController',
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
        .state('season-detail', {
            parent: 'entity',
            url: '/season/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Season'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/season/season-detail.html',
                    controller: 'SeasonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Season', function($stateParams, Season) {
                    return Season.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'season',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('season-detail.edit', {
            parent: 'season-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/season/season-dialog.html',
                    controller: 'SeasonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Season', function(Season) {
                            return Season.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('season.new', {
            parent: 'season',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/season/season-dialog.html',
                    controller: 'SeasonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                year: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('season', null, { reload: 'season' });
                }, function() {
                    $state.go('season');
                });
            }]
        })
        .state('season.edit', {
            parent: 'season',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/season/season-dialog.html',
                    controller: 'SeasonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Season', function(Season) {
                            return Season.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('season', null, { reload: 'season' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('season.delete', {
            parent: 'season',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/season/season-delete-dialog.html',
                    controller: 'SeasonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Season', function(Season) {
                            return Season.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('season', null, { reload: 'season' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
