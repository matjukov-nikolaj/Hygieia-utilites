(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module)
        .factory('dataComparison', dataComparison);

    function dataComparison() {

        let data = {
            result: null,
            name: null,
            config: null,
        };

        let addResult = function(object) {
            data.result = object;
        };

        let addCollectorName = function (name) {
            data.name = name;
        };

        let addConfig = function (cfg) {
            data.config = cfg
        };

        let getData = function(){
            return data;
        };

        return {
            addResult: addResult,
            addCollectorName: addCollectorName,
            addConfig: addConfig,
            getData: getData
        };
    }
})();
