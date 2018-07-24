(function () {
    'use strict';
    let widget_state,
        config = {
            view: {
                defaults: {
                    title: 'AppScan' // widget title
                },
                controller: 'AppScanViewController',
                controllerAs: 'appScanWidget',
                templateUrl: 'components/widgets/appscan/view.html',
            },
            config: {
                controller: 'AppScanConfigController',
                controllerAs: 'appScanWidget',
                templateUrl: 'components/widgets/appscan/config.html'
            },
            getState: getState,
            collectors: ['appscan']
        };

    angular
        .module(HygieiaConfig.module)
        .config(register);

    register.$inject = ['widgetManagerProvider', 'WidgetState'];
    function register(widgetManagerProvider, WidgetState) {
        widget_state = WidgetState;
        widgetManagerProvider.register('appscan', config);
    }

    function getState(widgetConfig) {
        // make sure config values are set
        return HygieiaConfig.local || (widgetConfig.id) ? widget_state.READY : widget_state.CONFIGURE;
    }
}) ();

