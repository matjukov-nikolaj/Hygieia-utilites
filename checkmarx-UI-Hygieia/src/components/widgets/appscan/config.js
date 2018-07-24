(function() {
    'use strict';

    angular
        .module(HygieiaConfig.module)
        .controller('AppScanConfigController', AppScanConfigController);

    AppScanConfigController.$inject = ['modalData', '$scope', 'collectorData', '$uibModalInstance'];

    function AppScanConfigController(modalData, $scope, collectorData, $uibModalInstance) {
        let ctrl = this;
        let widgetConfig = modalData.widgetConfig;
        let component = modalData.dashboard.application.components[0];
        console.log(modalData);

        ctrl.bdToolsDropdownPlaceholder = 'Loading AppScan Jobs...';

        ctrl.bdLoading = true;
        ctrl.submit = submitForm;

        $scope.getAppScanCollectors = function(filter){
            return collectorData.itemsByType('AppScan', {"search": filter, "size": 20}).then(function (response){
                return response;
            });
        };

        loadSavedAppScanJob();
        collectorData.itemsByType('AppScan').then(processAppScanResponse);
        function loadSavedAppScanJob(){
            let appScanCollectorItems = component.collectorItems.AppScan;
            const savedAppScanJob = appScanCollectorItems ? appScanCollectorItems[0].description : null;
            if(savedAppScanJob){
                $scope.getAppScanCollectors(savedAppScanJob).then(getAppScanCollectorsCallback) ;
            }
        }

        function getAppScanCollectorsCallback(data) {
            ctrl.appScanCollectorItem = data[0];
        }

        function processAppScanResponse(data) {
            let appScanCollectorItems = component.collectorItems.AppScan;
            let appScanCollectorItemId = _.isEmpty(appScanCollectorItems) ? null : appScanCollectorItems[0].id;
            ctrl.apJobs = data;
            ctrl.apCollectorItem = appScanCollectorItemId ? _.find(ctrl.bdJobs, {id: appScanCollectorItemId}) : null;
            ctrl.apToolsDropdownPlaceholder = data.length ? 'Select a AppScan Job' : 'No AppScan Job Found';
        }

        function submitForm(appScanCollectorItem) {
            let collectorItems = [];
            if (appScanCollectorItem) collectorItems.push(appScanCollectorItem.id);
            let form = document.configForm;
            let postObj = {
                name: 'appscan',
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