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
            ctrl.reportUrl = _.isEmpty(response.reportUrl) ? config.EMPTY_URL : response.reportUrl;
            ctrl.date = getDateFromTimestamp(appScanData.timestamp);
            processBlockWithDifferenceValues(response);
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }

        function processBlockWithDifferenceValues(response) {
            let differenceBlockStyle = document.getElementById("appScanDifferenceBlock").style;
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
            ctrl.differenceLowValues = metricsValuesDifferences[config.appScan.LOW];
            ctrl.differenceMediumValues = metricsValuesDifferences[config.appScan.MEDIUM];
            ctrl.differenceHighValues = metricsValuesDifferences[config.appScan.HIGH];
            showDifferenceOfValues(ctrl.differenceLowValues, config.appScan.LOW);
            showDifferenceOfValues(ctrl.differenceMediumValues, config.appScan.MEDIUM);
            showDifferenceOfValues(ctrl.differenceHighValues, config.appScan.HIGH);
        }

        function showDifferenceOfValues(value, element) {
            let downArrowStyle = document.getElementById(config.appScan.COLLECTOR + element + config.VALUES_DOWN).style;
            hideBlock(downArrowStyle);
            let upArrowStyle = document.getElementById(config.appScan.COLLECTOR + element + config.VALUES_UP).style;
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
            if (element === config.appScan.LOW) {
                ctrl.differenceLowValues = Math.abs(ctrl.differenceLowValues);
            } else if (element === config.appScan.MEDIUM) {
                ctrl.differenceMediumValues = Math.abs(ctrl.differenceMediumValues);
            } else {
                ctrl.differenceHighValues = Math.abs(ctrl.differenceHighValues);
            }
        }

    }
}) ();
