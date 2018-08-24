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
            ctrl.reportUrl = _.isEmpty(response.reportUrl) ? codeSecurityConfig.EMPTY_URL : response.reportUrl;
            ctrl.date = getDateFromTimestamp(checkMarxData.timestamp);
            processBlockWithDifferenceValues(response);
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }

        function processBlockWithDifferenceValues(response) {
            let differenceBlockStyle = document.getElementById("checkMarxDifferenceBlock").style;
            if (response.result.length === 2) {
                differenceBlockStyle.display = codeSecurityConfig.BLOCK;
                showDifferenceBlock(differenceBlockStyle, response);
            } else {
                hideBlock(differenceBlockStyle);
            }
        }

        function showDifferenceBlock(differenceBlockStyle, response) {
            differenceBlockStyle.display = codeSecurityConfig.BLOCK;
            const metricsValuesDifferences = response.result[1].metrics;
            ctrl.differenceLowValues = metricsValuesDifferences[codeSecurityConfig.LOW];
            ctrl.differenceMediumValues = metricsValuesDifferences[codeSecurityConfig.MEDIUM];
            ctrl.differenceHighValues = metricsValuesDifferences[codeSecurityConfig.HIGH];
            showDifferenceOfValues(ctrl.differenceLowValues, codeSecurityConfig.LOW);
            showDifferenceOfValues(ctrl.differenceMediumValues, codeSecurityConfig.MEDIUM);
            showDifferenceOfValues(ctrl.differenceHighValues, codeSecurityConfig.HIGH);
        }

        function showDifferenceOfValues(value, element) {
            let downArrowStyle = document.getElementById(codeSecurityConfig.CHECK_MARX + element + codeSecurityConfig.VALUES_DOWN).style;
            hideBlock(downArrowStyle);
            let upArrowStyle = document.getElementById(codeSecurityConfig.CHECK_MARX + element + codeSecurityConfig.VALUES_UP).style;
            hideBlock(upArrowStyle);
            let blockElement = document.getElementById(codeSecurityConfig.DIFFERENCE + element + codeSecurityConfig.VALUES_BLOCK);
            if (value > 0) {
                blockElement.style.color = codeSecurityConfig.GREEN_COLOR;
                downArrowStyle.display = codeSecurityConfig.INLINE_BLOCK;
            } else if (value < 0) {
                blockElement.style.color = codeSecurityConfig.RED_COLOR;
                upArrowStyle.display = codeSecurityConfig.INLINE_BLOCK;
                replaceNegativeValueOfElement(element);
            } else {
                blockElement.style.color = codeSecurityConfig.YELLOW_COLOR;
            }
        }

        function hideBlock(element) {
            if (element.display !== codeSecurityConfig.NONE) {
                element.display = codeSecurityConfig.NONE;
            }
        }

        function replaceNegativeValueOfElement(element) {
            if (element === codeSecurityConfig.LOW) {
                ctrl.differenceLowValues = Math.abs(ctrl.differenceLowValues);
            } else if (element === codeSecurityConfig.MEDIUM) {
                ctrl.differenceMediumValues = Math.abs(ctrl.differenceMediumValues);
            } else {
                ctrl.differenceHighValues = Math.abs(ctrl.differenceHighValues);
            }
        }

        function getDateFromTimestamp(timestamp) {
            let date = new Date(timestamp);
            return [date.getFullYear(),
                date.getMonth() + 1,
                date.getDate()].join('-');
        }
    }
})();