/**
 * Gets code security related data
 */
(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module + '.core')
        .factory('checkMarxData', checkMarxData);

    function checkMarxData($http) {
        const checkMarxDetailsRoute = '/api/checkmarx';

        return {
            checkMarxDetails: checkMarxDetails
        };

        // get the latest code security data for the component
        function checkMarxDetails(params) {
            return $http.get(checkMarxDetailsRoute, { params: params })
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();
