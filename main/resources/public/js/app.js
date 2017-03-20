var app = angular.module('app', ['ngRoute','ngResource']);

app.config(function($routeProvider){

	$routeProvider
        .when('/home',{
            templateUrl: '/page/home.html',
            controller: 'HomeCtrl'
        })
        .otherwise(
            { redirectTo: '/home'}
        );

});