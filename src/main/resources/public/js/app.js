var app = angular.module('app', ['ngRoute','ngResource','ngMaterial','ui.bootstrap']);

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

app.run(function($rootScope, $http) {
	$http.get("message/appMessage.json").then((response) => {
		$rootScope.appMessage = response.data;
	},() => {
		console.error("Erro ao carregar as mensagens do sistema");
	});
});