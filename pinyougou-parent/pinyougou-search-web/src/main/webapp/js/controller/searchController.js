app.controller('searchController',function($scope,searchService){
	
	//搜索
	$scope.search=function(){
		searchService.search($scope.searchMap).success(
			function(response){
				alert(JSON.stringify(response));
				$scope.resultMap=response;				
			}
		);		
	}
	
	
});