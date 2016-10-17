'use strict';

describe('Controller Tests', function() {

    describe('TrophyWinner Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTrophyWinner, MockSeason, MockTeam, MockTrophy;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTrophyWinner = jasmine.createSpy('MockTrophyWinner');
            MockSeason = jasmine.createSpy('MockSeason');
            MockTeam = jasmine.createSpy('MockTeam');
            MockTrophy = jasmine.createSpy('MockTrophy');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TrophyWinner': MockTrophyWinner,
                'Season': MockSeason,
                'Team': MockTeam,
                'Trophy': MockTrophy
            };
            createController = function() {
                $injector.get('$controller')("TrophyWinnerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'piratesflApp:trophyWinnerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
