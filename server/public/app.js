'use strict';

// Declare app level module which depends on views, and components
var ctrl  = angular.module('myApp', [
  'ngRoute',
  'myApp.view1',
  'myApp.view2',
  'myApp.version'
]).
config(['$routeProvider', function($routeProvider) {

      $routeProvider.when('/', {
        templateUrl : 'login.html',
        controller : 'navigation'
      }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
      })// Home

          .when("/", {templateUrl: "partial/home.html", controller: "PageCtrl"})
// Pages
            .when("/about", {templateUrl: "partial/about.html", controller: "PageCtrl"})
            .when("/attendances", {templateUrl: "partial/Attendances.html", controller: "PageCtrl"})
            .when("/documents", {templateUrl: "partial/Documents.html", controller: "PageCtrl"})
            .when("/rooms", {templateUrl: "partial/Rooms.html", controller: "PageCtrl"})
            .when("/users",  {templateUrl: "partial/Users.html", controller: "PageCtrl"})

// else 404
            .otherwise("/404", {templateUrl: "partial/404.html", controller: "PageCtrl"});



     // $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
}]);


ctrl.controller('PageCtrl', function ( $scope, $location, $http ) {
    console.log("Page Controller reporting for duty.");
});

ctrl.controller('userController',['$scope', '$http', '$location' , '$log','$window',function ($scope,$http,$location,$log,$window){
    console.log("userController reporting for duty.")
    $scope.users
    $scope.init = function () {
        // check if there is query in url
        // and fire search in case its value is not empty
        var adressUser = "http://localhost:3000/user";

        $http.get(adressUser,{
            headers: {'Authorization': 'Bearer '+$window.sessionStorage.token}}).success(function(data, status, headers, config) {
            $log.log(data);
            $log.log(status);
            $scope.users = data.data;
            $log.log($scope.users);

        }).error(function(data,status,headers,config)
        {

        });

    };
}]);

