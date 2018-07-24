(function () {
    'use strict';
    angular
        .module(HygieiaConfig.module)
        .controller('CheckMarxViewController', CheckMarxViewController);

    CheckMarxViewController.$inject = ['$scope', 'checkMarxData', '$q'];

    function CheckMarxViewController($scope, checkMarxData, $q) {
        let ctrl = this;
        ctrl.load = function () {
            let cmRequest = {
                id: $scope.widgetConfig.componentId,
            };
            return $q.all([
                checkMarxData.checkMarxDetails(cmRequest).then(processCheckMarxResponse)
            ])
        };

        function processCheckMarxResponse(response) {
            let deferred = $q.defer();
            const checkMarxData = _.isEmpty(response.result) ? {} : response.result[0];
            ctrl.projectName = checkMarxData.name;
            ctrl.metrics = checkMarxData.metrics;
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }
    }
}) ();