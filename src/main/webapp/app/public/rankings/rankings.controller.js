(function() {
    'use strict';

    angular
        .module('piratesflApp')
        .controller('RankingsController', RankingsController);

    RankingsController.$inject = ['$scope', 'PowerRanking', 'AlertService', '$state'];

    function RankingsController ($scope, PowerRanking, $state) {
        var vm = this;
        
        load();
        
        vm.search = {week: 0};
        
        function load() {
        	PowerRanking.query({sort: "rank,asc", size: 300}, onSuccess, onError);
        	
            function onSuccess(data, headers) {
                vm.powerRankings = data;
                
                vm.search.week = Math.max.apply(Math,vm.powerRankings.map(function(o){return o.week;}))
            }
            
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
