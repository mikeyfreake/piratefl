(function() {
    'use strict';
    angular
        .module('piratesflApp')
        .factory('Season', Season);

    Season.$inject = ['$resource'];

    function Season ($resource) {
        var resourceUrl =  'api/seasons/:id';

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
