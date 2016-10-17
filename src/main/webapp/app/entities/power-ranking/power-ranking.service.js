(function() {
    'use strict';
    angular
        .module('piratesflApp')
        .factory('PowerRanking', PowerRanking);

    PowerRanking.$inject = ['$resource'];

    function PowerRanking ($resource) {
        var resourceUrl =  'api/power-rankings/:id';

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
