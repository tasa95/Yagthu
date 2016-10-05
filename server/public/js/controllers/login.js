/**
 * Created by thierryallardsaintalbin on 23/06/15.
 */
'use strict';



var adress = "http://localhost:3000/login";





/* Controllers */

var loginapp = angular.module('myApp', []);

loginapp.controller('loginPage', ['$scope',
    function($scope) {
        $scope.login ;
        $scope.password ;
    }]);



loginapp.controller('loginPage', ['$scope', '$http', '$location' , '$log','$window',function($scope, $http ,$location,$log,$window) {
    $scope.login = {};
    $scope.password = [];

    $scope.loginApplication = function () {
        /* the $http service allows you to make arbitrary ajax requests.
         * in this case you might also consider using angular-resource and setting up a
         * User $resource. */
        $http.post(adress, {login: $scope.login, password: $scope.password}).success(function(data, status, headers, config) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.results = data;
            $log.log(data.data);
            $log.log(status);
            $window.sessionStorage.token = data.data.token;

            var url = "/home.html"

            var changeLocation = function(url, force) {
                //this will mark the URL change
                //$location.path(adress); //use $location.path(url).replace() if you want to replace the location instead

                $scope = $scope || angular.element(document).scope();
                if (force || $scope.$$phase) {
                    window.location = url;
                }
                else {
                    //only use this if you want to replace the history stack
                    //$location.path(url).replace();

                    //this this if you want to change the URL and add it to the history stack
                    $location.path(url);
                    $scope.$apply();
                }
            };

            changeLocation(url,true);
        }).error(function(data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $log.log(data);
            $log.log(status);
        });
    };
}]);

angular.module('myApp').controller('CollapseDemoCtrl', function ($scope) {
    $scope.isCollapsed = false;
});


