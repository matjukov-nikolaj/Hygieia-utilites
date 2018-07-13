(function() {
    'use strict';

    angular
        .module(HygieiaConfig.module)
        .controller('CheckMarxConfigController', CheckMarxConfigController);

    CheckMarxConfigController.$inject = ['modalData', '$scope', 'collectorData', '$uibModalInstance'];

    function CheckMarxConfigController(modalData, $scope, collectorData, $uibModalInstance) {
        let ctrl = this;
        let widgetConfig = modalData.widgetConfig;
        let component = modalData.dashboard.application.components[0];
        console.log(modalData);

        ctrl.csToolsDropdownPlaceholder = 'Loading Security Analysis Jobs...';

        ctrl.csLoading = true;
        ctrl.submit = submitForm;

        $scope.getCheckMarxCollectors = function(filter){
            return collectorData.itemsByType('CheckMarx', {"search": filter, "size": 20}).then(function (response){
                return response;
            });
        };

        loadSavedCheckMarxJob();
        collectorData.itemsByType('CheckMarx').then(processCheckMarxResponse);
        function loadSavedCheckMarxJob(){
            let checkMarxCollectorItems = component.collectorItems.CheckMarx;
            const savedCheckMarxJob = checkMarxCollectorItems ? checkMarxCollectorItems[0].description : null;
            if(savedCheckMarxJob){
                $scope.getCheckMarxCollectors(savedCheckMarxJob).then(getCheckMarxCollectorsCallback) ;
            }
        }

        function getCheckMarxCollectorsCallback(data) {
            ctrl.checkMarxCollectorItem = data[0];
        }

        function processCheckMarxResponse(data) {
            let checkMarxCollectorItems = component.collectorItems.CheckMarx;
            let checkMarxCollectorItemId = _.isEmpty(checkMarxCollectorItems) ? null : checkMarxCollectorItems[0].id;
            ctrl.csJobs = data;
            ctrl.csCollectorItem = checkMarxCollectorItemId ? _.find(ctrl.csJobs, {id: checkMarxCollectorItemId}) : null;
            ctrl.csToolsDropdownPlaceholder = data.length ? 'Select a Code Security Job' : 'No Code Security Job Found';
        }

        function submitForm(checkMarxCollectorItem) {
            let collectorItems = [];
            if (checkMarxCollectorItem) collectorItems.push(checkMarxCollectorItem.id);
            let form = document.configForm;
            let postObj = {
                name: 'checkmarx',
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