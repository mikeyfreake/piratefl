(function() {
    'use strict';
    angular
        .module('piratesflApp')
        .factory('TeamStats', TeamStats);

    TeamStats.$inject = ['$resource'];

    function TeamStats ($resource) {
        var resourceUrl =  'api/team-stats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
