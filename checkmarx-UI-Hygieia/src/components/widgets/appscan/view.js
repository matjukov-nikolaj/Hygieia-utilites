(function () {
    'use strict';
    angular
        .module(HygieiaConfig.module)
        .controller('AppScanViewController', AppScanViewController);

    AppScanViewController.$inject = ['$scope', 'appScanData', '$q'];

    function AppScanViewController($scope, appScanData, $q) {
        let ctrl = this;
        ctrl.load = function () {
            let apRequest = {
                id: $scope.widgetConfig.componentId,
            };
            return $q.all([
                appScanData.appScanDetails(apRequest).then(processAppScanResponse)
            ])
        };

        function processAppScanResponse(response) {
            let deferred = $q.defer();
            const appScanData = _.isEmpty(response.result) ? {} : response.result[0];
            ctrl.projectName = appScanData.name;
            ctrl.metrics = appScanData.metrics;
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }
    }
}) ();