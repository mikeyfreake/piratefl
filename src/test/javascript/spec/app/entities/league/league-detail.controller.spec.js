'use strict';

describe('Controller Tests', function() {

    describe('League Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLeague, MockSeason, MockTeam, MockTrophy;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLeague = jasmine.createSpy('MockLeague');
            MockSeason = jasmine.createSpy('MockSeason');
            MockTeam = jasmine.createSpy('MockTeam');
            MockTrophy = jasmine.createSpy('MockTrophy');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'League': MockLeague,
                'Season': MockSeason,
                'Team': MockTeam,
                'Trophy': MockTrophy
            };
            createController = function() {
                $injector.get('$controller')("LeagueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'piratesflApp:leagueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
