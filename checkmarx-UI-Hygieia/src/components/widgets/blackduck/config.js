(function() {
    'use strict';

    angular
        .module(HygieiaConfig.module)
        .controller('BlackDuckConfigController', BlackDuckConfigController);

    BlackDuckConfigController.$inject = ['modalData', '$scope', 'collectorData', '$uibModalInstance'];

    function BlackDuckConfigController(modalData, $scope, collectorData, $uibModalInstance) {
        let ctrl = this;
        let widgetConfig = modalData.widgetConfig;
        let component = modalData.dashboard.application.components[0];
        console.log(modalData);

        ctrl.bdToolsDropdownPlaceholder = 'Loading BlackDuck Jobs...';

        ctrl.bdLoading = true;
        ctrl.submit = submitForm;

        $scope.getBlackDuckCollectors = function(filter){
            return collectorData.itemsByType('BlackDuck', {"search": filter, "size": 20}).then(function (response){
                return response;
            });
        };

        loadSavedBlackDuckJob();
        collectorData.itemsByType('BlackDuck').then(processBlackDuckResponse);
        function loadSavedBlackDuckJob(){
            let blackDuckCollectorItems = component.collectorItems.BlackDuck;
            const savedBlackDuckJob = blackDuckCollectorItems ? blackDuckCollectorItems[0].description : null;
            if(savedBlackDuckJob){
                $scope.getBlackDuckCollectors(savedBlackDuckJob).then(getBlackDuckCollectorsCallback) ;
            }
        }

        function getBlackDuckCollectorsCallback(data) {
            ctrl.blackDuckCollectorItem = data[0];
        }

        function processBlackDuckResponse(data) {
            let blackDuckCollectorItems = component.collectorItems.BlackDuck;
            let blackDuckCollectorItemId = _.isEmpty(blackDuckCollectorItems) ? null : blackDuckCollectorItems[0].id;
            ctrl.bdJobs = data;
            ctrl.bdCollectorItem = blackDuckCollectorItemId ? _.find(ctrl.bdJobs, {id: blackDuckCollectorItemId}) : null;
            ctrl.bdToolsDropdownPlaceholder = data.length ? 'Select a BlackDuck Job' : 'No BlackDuck Job Found';
        }

        function submitForm(blackDuckCollectorItem) {
            let collectorItems = [];
            if (blackDuckCollectorItem) collectorItems.push(blackDuckCollectorItem.id);
            let form = document.configForm;
            let postObj = {
                name: 'blackduck',
                options: {
                    id: widgetConfig.options.id,
                },
                componentId: component.id,
                collectorItemIds: collectorItems
            };
            // pass this new config to the modal closing so it's saved
            $uibModalInstance.close(postObj);
        }
    }
})();