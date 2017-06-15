app.controller('HomeCtrl', function (
	$scope, homeService
) {
	
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
		
		homeService.translate($scope.originalPhrase, $scope.manezes, (result) => {
			
			if(!result){
				$scope.translatedPhrase = $scope.originalPhrase;
			} else {
				var text = result.traductions[0].text;	
				$scope.translatedPhrase = treatPhrase(text);
				$scope.traductions = result.traductions;
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
			originalPhrase: $scope.originalPhrase, 
			translatedPhrase: $scope.translatedPhrase,
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
