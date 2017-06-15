app.factory('homeService', function ($http, $q) {
	
	var showGenericError = function(){
		alert("O estepô, déssi um erro no sixtema! aviza la ox cara agora :(");
	};
	
	_translate = function(originalPhrase, manezes, callback){
		$http.get('home/translate',{
			params : {
				originalPhrase : originalPhrase,
				manezes : manezes
			}
		}).then(function(response){
			callback && callback(response.data);
		},showGenericError);
	};
	
	_sendTraduction = function( params , callback){
		$http.post('home/sendTraduction',{
			originalPhrase : params.originalPhrase,
			translatedPhrase : params.translatedPhrase,
			manezes : params.manezes
		}).then(function(){
			callback();
		},showGenericError);
	};
	
	_findPhrases = function(phrase, manezes){
		
		var deferred = $q.defer();
		
		if(phrase)
			$http.get('home/find',{
				params : {
					phrase : phrase,
					manezes : manezes
				}
			}).then(function(response){
				
				deferred.resolve(response.data.map( function (value) {
			        return {
			            value: value && value.text.toLowerCase(),
			            display: value && value.text
			        };
			    }));
				
			},showGenericError);
	
		return deferred.promise;

	};
	
	return {
		translate : _translate,
		sendTraduction : _sendTraduction,
		findPhrases : _findPhrases
	};
	
});
