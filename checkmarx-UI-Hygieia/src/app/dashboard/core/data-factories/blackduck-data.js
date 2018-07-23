/**
 * Gets code security related data
 */
(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module + '.core')
        .factory('blackDuckData', blackDuckData);

    function blackDuckData($http) {
        const blackDuckDetailsRoute = '/api/blackduck';

        return {
            blackDuckDetails: blackDuckDetails
        };

        // get the latest code security data for the component
        function blackDuckDetails(params) {
            return $http.get(blackDuckDetailsRoute, { params: params })
                .then(function (response) {
                    return response.data;
                });
        }
    }
})();
