(function() {
    'use strict';
    angular
        .module('piratesflApp')
        .factory('TrophyWinner', TrophyWinner);

    TrophyWinner.$inject = ['$resource'];

    function TrophyWinner ($resource) {
        var resourceUrl =  'api/trophy-winners/:id';

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
