(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('RankingsController', RankingsController);

    RankingsController.$inject = ['$scope', 'PowerRanking', '$state'];

    function RankingsController ($scope, PowerRanking, $state) {
        var vm = this;
        
        load();
        
        function load() {
        	//vm.league = League.get({id: 1});
        }
    }
})();
