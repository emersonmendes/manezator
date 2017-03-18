app.controller('HomeCtrl', function ($scope, $http) {
	
	$scope.edit = function(){
		$scope.isEditing = true;
	};
	
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
	
	$scope.translate = function(){
		$http.get('home/translate',{
			params : {
				originalPhrase : $scope.originalPhrase,
				manezes : $scope.manezes
			}
		}).success(function(result){
			
			if(!result){
				$scope.translatedPhrase = $scope.originalPhrase;
			} else {
				
				var text = result.traductions[0].text;	
				
				$scope.translatedPhrase = treatPhrase(text);
				
				$scope.traductions = result.traductions;
				
			}
			
		});
	};
	
	$scope.invert = function(){
		$scope.manezes = !$scope.manezes;
	};
	
	$scope.sendTraduction = function(){
		$http.post('home/sendTraduction',{
			originalPhrase : $scope.originalPhrase,
			translatedPhrase : $scope.translatedPhrase,
			manezes : $scope.manezes
		}).success(function(){
			$scope.isEditing = false;
		});
	};
	
});
