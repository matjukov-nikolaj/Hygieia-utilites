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
            console.log(response);
            ctrl.projectName = checkMarxData.name;
            ctrl.metrics = checkMarxData.metrics;
            ctrl.reportUrl = _.isEmpty(response.reportUrl) ? config.EMPTY_URL : response.reportUrl;
            ctrl.date = getDateFromTimestamp(checkMarxData.timestamp);
            processBlockWithDifferenceValues(response);
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }

        function processBlockWithDifferenceValues(response) {
            let differenceBlockStyle = document.getElementById("checkMarxDifferenceBlock").style;
            if (response.result.length === 2) {
                differenceBlockStyle.display = config.BLOCK;
                showDifferenceBlock(differenceBlockStyle, response);
            } else {
                hideBlock(differenceBlockStyle);
            }
        }

        function showDifferenceBlock(differenceBlockStyle, response) {
            differenceBlockStyle.display = config.BLOCK;
            const metricsValuesDifferences = response.result[1].metrics;
            ctrl.differenceLowValues = metricsValuesDifferences[config.checkMarx.LOW];
            ctrl.differenceMediumValues = metricsValuesDifferences[config.checkMarx.MEDIUM];
            ctrl.differenceHighValues = metricsValuesDifferences[config.checkMarx.HIGH];
            showDifferenceOfValues(ctrl.differenceLowValues, config.checkMarx.LOW);
            showDifferenceOfValues(ctrl.differenceMediumValues, config.checkMarx.MEDIUM);
            showDifferenceOfValues(ctrl.differenceHighValues, config.checkMarx.HIGH);
        }

        function showDifferenceOfValues(value, element) {
            let downArrowStyle = document.getElementById(config.checkMarx.COLLECTOR + element + config.VALUES_DOWN).style;
            hideBlock(downArrowStyle);
            let upArrowStyle = document.getElementById(config.checkMarx.COLLECTOR + element + config.VALUES_UP).style;
            hideBlock(upArrowStyle);
            let blockElement = document.getElementById(config.DIFFERENCE + element + config.VALUES_BLOCK);
            if (value > 0) {
                blockElement.style.color = config.GREEN_COLOR;
                downArrowStyle.display = config.INLINE_BLOCK;
            } else if (value < 0) {
                blockElement.style.color = config.RED_COLOR;
                upArrowStyle.display = config.INLINE_BLOCK;
                replaceNegativeValueOfElement(element);
            } else {
                blockElement.style.color = config.YELLOW_COLOR;
            }
        }

        function hideBlock(element) {
            if (element.display !== config.NONE) {
                element.display = config.NONE;
            }
        }

        function replaceNegativeValueOfElement(element) {
            if (element === config.checkMarx.LOW) {
                ctrl.differenceLowValues = Math.abs(ctrl.differenceLowValues);
            } else if (element === config.checkMarx.MEDIUM) {
                ctrl.differenceMediumValues = Math.abs(ctrl.differenceMediumValues);
            } else {
                ctrl.differenceHighValues = Math.abs(ctrl.differenceHighValues);
            }
        }
    }
})();