'use strict';

describe('Controller Tests', function() {

    describe('PowerRanking Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPowerRanking, MockTeam, MockSeason;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPowerRanking = jasmine.createSpy('MockPowerRanking');
            MockTeam = jasmine.createSpy('MockTeam');
            MockSeason = jasmine.createSpy('MockSeason');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PowerRanking': MockPowerRanking,
                'Team': MockTeam,
                'Season': MockSeason
            };
            createController = function() {
                $injector.get('$controller')("PowerRankingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'piratesflApp:powerRankingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
