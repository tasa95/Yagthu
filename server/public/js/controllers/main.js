/**
 * Created by thierryallardsaintalbin on 24/06/15.
 */
var app = angular.module('tutorialWebApp', [
    'ngRoute'
]);


/**
 * Configure the Routes
 */
app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
// Home
        .when("/", {templateUrl: "partial/home.html", controller: "PageCtrl"})
// Pages
.when("/about", {templateUrl: "partial/about.html", controller: "PageCtrl"})
.when("/attendances", {templateUrl: "partial/Attendances.html", controller: "PageCtrl"})
.when("/documents", {templateUrl: "partial/Documents.html", controller: "PageCtrl"})
        .when("/rooms", {templateUrl: "partial/Rooms.html", controller: "PageCtrl"})
        .when("/users",  {templateUrl: "partial/Users.html", controller: "PageCtrl"})

// else 404
.otherwise("/404", {templateUrl: "partial/404.html", controller: "PageCtrl"});
}]);

app.controller('PageCtrl', function ( $scope, $location, $http ) {
    console.log("Page Controller reporting for duty.");
});


app.controller('userController',['$scope', '$http', '$location' , '$log',function ($scope,$http,$location,$log){
    console.log("userController reporting for duty.")
    $scope.init = function () {
        // check if there is query in url
        // and fire search in case its value is not empty
        var adressUser = "http://localhost:3000/user";

            $http.get(adressUser).success(function(data, status, headers, config) {
                $log.$log(data);
                $log.$log(status);

            }).error(function(data,status,headers,config)
            {

            });

    };
}]);

