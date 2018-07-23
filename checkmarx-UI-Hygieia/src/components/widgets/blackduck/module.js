(function () {
    'use strict';
    let widget_state,
        config = {
            view: {
                defaults: {
                    title: 'BlackDuck' // widget title
                },
                controller: 'BlackDuckViewController',
                controllerAs: 'blackDuckWidget',
                templateUrl: 'components/widgets/blackduck/view.html',
            },
            config: {
                controller: 'BlackDuckConfigController',
                controllerAs: 'blackDuckWidget',
                templateUrl: 'components/widgets/blackduck/config.html'
            },
            getState: getState,
            collectors: ['blackduck']
        };

    angular
        .module(HygieiaConfig.module)
        .config(register);

    register.$inject = ['widgetManagerProvider', 'WidgetState'];
    function register(widgetManagerProvider, WidgetState) {
        widget_state = WidgetState;
        widgetManagerProvider.register('blackduck', config);
    }

    function getState(widgetConfig) {
        // make sure config values are set
        return HygieiaConfig.local || (widgetConfig.id) ? widget_state.READY : widget_state.CONFIGURE;
    }
}) ();

