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
            ctrl.projectName = blackDuckData.name;
            ctrl.metrics = blackDuckData.metrics;
            ctrl.percentages = blackDuckData.percentages;
            console.log(response.result);
            ctrl.first = ctrl.metrics[config.blackDuck.NUMBER_OF_FILES];
            ctrl.second = ctrl.metrics[config.blackDuck.FILES_PENDING_IDENTIFICATION];
            ctrl.percentagesSecond = "(" + ctrl.percentages[config.blackDuck.FILES_PENDING_IDENTIFICATION] + "%)"
            ctrl.third = ctrl.metrics[config.blackDuck.FILES_WITH_VIOLATIONS];
            ctrl.percentagesThird = "(" + ctrl.percentages[config.blackDuck.FILES_WITH_VIOLATIONS] + "%)";
            ctrl.reportUrl = _.isEmpty(response.reportUrl) ? config.EMPTY_URL : response.reportUrl;
            ctrl.date = getDateFromTimestamp(blackDuckData.timestamp);
            processBlockWithDifferenceValues(response);
            deferred.resolve(response.lastUpdated);
            return deferred.promise;
        }

        function processBlockWithDifferenceValues(response) {
            let differenceBlockStyle = document.getElementById("blackDuckDifferenceBlock").style;
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
            const percents = response.result[1].percentages;
            ctrl.differenceFilesWithViolations = metricsValuesDifferences[config.blackDuck.FILES_WITH_VIOLATIONS];
            ctrl.percentages = percents[config.blackDuck.FILES_WITH_VIOLATIONS];
            showDifferenceOfValues(ctrl.differenceFilesWithViolations, "FilesWithViolations");
        }

        function showDifferenceOfValues(value, element) {
            let downArrowStyle = document.getElementById(config.blackDuck.COLLECTOR + element + config.VALUES_DOWN).style;
            hideBlock(downArrowStyle);
            let upArrowStyle = document.getElementById(config.blackDuck.COLLECTOR + element + config.VALUES_UP).style;
            hideBlock(upArrowStyle);
            let blockElement = document.getElementById(config.DIFFERENCE + element + "Block");
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
            if (element === "FilesWithViolations") {
                ctrl.differenceFilesWithViolations = Math.abs(ctrl.differenceFilesWithViolations);
            }
        }
    }
}) ();