(function () {
    'use strict';
    angular
        .module(HygieiaConfig.module)
        .controller('BlackDuckViewController', BlackDuckViewController);

    BlackDuckViewController.$inject = ['$scope', 'blackDuckData', '$q'];

    function BlackDuckViewController($scope, blackDuckData, $q) {
        let ctrl = this;
        ctrl.load = function () {
            let bdRequest = {
                id: $scope.widgetConfig.componentId,
            };
            return $q.all([
                blackDuckData.blackDuckDetails(bdRequest).then(processBlackDuckResponse)
            ])
        };

        function processBlackDuckResponse(response) {
            let deferred = $q.defer();
            const blackDuckData = _.isEmpty(response.result) ? {} : response.result[0];
            console.log(blackDuckData);
            ctrl.projectName = blackDuckData.name;
            ctrl.metrics = blackDuckData.metrics;
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }
    }
}) ();