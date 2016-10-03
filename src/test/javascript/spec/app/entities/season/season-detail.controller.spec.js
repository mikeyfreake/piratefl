'use strict';

describe('Controller Tests', function() {

    describe('Season Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSeason, MockLeague, MockTeamStats, MockPowerRanking, MockTrophyWinner;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSeason = jasmine.createSpy('MockSeason');
            MockLeague = jasmine.createSpy('MockLeague');
            MockTeamStats = jasmine.createSpy('MockTeamStats');
            MockPowerRanking = jasmine.createSpy('MockPowerRanking');
            MockTrophyWinner = jasmine.createSpy('MockTrophyWinner');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Season': MockSeason,
                'League': MockLeague,
                'TeamStats': MockTeamStats,
                'PowerRanking': MockPowerRanking,
                'TrophyWinner': MockTrophyWinner
            };
            createController = function() {
                $injector.get('$controller')("SeasonDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'piratesflApp:seasonUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
