(function() {
    'use strict';
    angular
        .module('piratesflApp')
        .factory('Trophy', Trophy);

    Trophy.$inject = ['$resource'];

    function Trophy ($resource) {
        var resourceUrl =  'api/trophies/:id';

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
