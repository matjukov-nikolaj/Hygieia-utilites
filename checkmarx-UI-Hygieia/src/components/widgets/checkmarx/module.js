(function () {
    'use strict';
    let widget_state,
        config = {
            view: {
                defaults: {
                    title: 'CheckMarx' // widget title
                },
                controller: 'CheckMarxViewController',
                controllerAs: 'checkMarxWidget',
                templateUrl: 'components/widgets/checkmarx/view.html',
            },
            config: {
                controller: 'CheckMarxConfigController',
                controllerAs: 'checkMarxWidget',
                templateUrl: 'components/widgets/checkmarx/config.html'
            },
            getState: getState,
            collectors: ['checkmarx']
        };

    angular
        .module(HygieiaConfig.module)
        .config(register);

    register.$inject = ['widgetManagerProvider', 'WidgetState'];
    function register(widgetManagerProvider, WidgetState) {
        widget_state = WidgetState;
        widgetManagerProvider.register('checkmarx', config);
    }

    function getState(widgetConfig) {
        // make sure config values are set
        return HygieiaConfig.local || (widgetConfig.id) ? widget_state.READY : widget_state.CONFIGURE;
    }
}) ();

