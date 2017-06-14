app.controller('HomeCtrl', function ($scope, $http) {
	
	
	$scope.traductions = [];

	$scope.manezes = false;
	
	var isUpperCase = function(value){
		return value === value.toUpperCase();
	};
	
	var treatPhrase = function(value){
		
		if(isUpperCase($scope.originalPhrase)){
			return value.toUpperCase();
		}
		
		if(isUpperCase($scope.originalPhrase.charAt(0))){
			return value.charAt(0).toUpperCase() + value.substr(1);
		}
		
		return value;
		
	};
	
	$scope.cleanSendTraductionsFeedback = function(){
		$scope.showSendTraductionsFeedback = false;
	};
	
	$scope.edit = function(){
		$scope.isEditing = true;
		$scope.cleanSendTraductionsFeedback();
	};
	
	$scope.translate = function(){
		
		$scope.cleanSendTraductionsFeedback();
		
		$http.get('home/translate',{
			params : {
				originalPhrase : $scope.originalPhrase,
				manezes : $scope.manezes
			}
		}).then(function(result){
			
			if(!result){
				$scope.translatedPhrase = $scope.originalPhrase;
			} else {
				var text = result.traductions[0].text;	
				$scope.translatedPhrase = treatPhrase(text);
				$scope.traductions = result.traductions;
				$scope.isEditing = false;
			}
			
		},function(){
			alert("O estepô, déssi um erro no sixte! aviza la ox cara agora :(");
		});
	};
	
	$scope.invert = function(){
		$scope.manezes = !$scope.manezes;
		$scope.translate();
	};
	
	$scope.sendTraduction = function(){
		$http.post('home/sendTraduction',{
			originalPhrase : $scope.originalPhrase,
			translatedPhrase : $scope.translatedPhrase,
			manezes : $scope.manezes
		}).then(function(){
			$scope.isEditing = false;
			$scope.showSendTraductionsFeedback = true;
		},function(){
			alert("O estepô, déssi um erro no sixte! aviza la ox cara agora :(");
		});
	};
	
});
