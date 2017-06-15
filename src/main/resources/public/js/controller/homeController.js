app.controller('HomeCtrl', function (
	$scope, homeService
) {
	
	$scope.traductions = [];

	$scope.manezes = false;
	$scope.formData = {
		originalPhrase : "",
		translatedPhrase : ""
	};
	
	var isUpperCase = function(value){
		return value === value.toUpperCase();
	};
	
	var treatPhrase = function(value){
		
		if(isUpperCase($scope.formData.originalPhrase)){
			return value.toUpperCase();
		}
		
		if(isUpperCase($scope.formData.originalPhrase.charAt(0))){
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
		
		homeService.translate($scope.formData.originalPhrase, $scope.manezes, (result) => {
			
			if(!result){
				$scope.formData.translatedPhrase = angular.copy($scope.formData.originalPhrase);
			} else {
				var text = result.traductions[0].text;	
				$scope.formData.translatedPhrase = angular.copy(treatPhrase(text));
				$scope.traductions = angular.copy(result.traductions);
				$scope.isEditing = false;
			}
			
		});
		
	};
	
	$scope.invert = function(){
		$scope.manezes = !$scope.manezes;
		$scope.translate();
	};
	
	$scope.sendTraduction = function(){
		homeService.sendTraduction({ 
			originalPhrase: $scope.formData.originalPhrase, 
			translatedPhrase: $scope.formData.translatedPhrase,
			manezes: $scope.manezes
		}, ()=>{
			$scope.isEditing = false;
			$scope.showSendTraductionsFeedback = true;
		});
	};
	
	$scope.findPhrases = function(phrase){
		return homeService.findPhrases(phrase, $scope.manezes);
	};
	
	$scope.selectedItemChange = function(item){
		$scope.translate();
	};
	
});
