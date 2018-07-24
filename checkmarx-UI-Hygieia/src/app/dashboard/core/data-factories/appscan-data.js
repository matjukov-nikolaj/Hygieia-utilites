(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module + '.core')
        .factory('appScanData', appScanData);

    function appScanData($http) {
        const appScanDetailsRoute = '/api/appscan';

        return {
            appScanDetails: appScanDetails
        };

        // get the latest code security data for the component
        function appScanDetails(params) {
            return $http.get(appScanDetailsRoute, { params: params })
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();
