// define module (main app)
angular.module('launcherApplication', ['ngResource'])
    // define controller for launcher
    .controller('launcherWebController', function($scope, $resource){

        // ng-resource for backend-communication (submit commands)
        var resource = $resource("http://localhost:9090/nina/launcher",{}, {'submit':   {method:'POST'}});

        // handler for click-events
        $scope.executeCommand = function(command) {

            console.info('executing command: ' + command);

            // build command object
            var commandObject = { 'command': command };
            // submit command
            resource.submit(commandObject);
        }
    });