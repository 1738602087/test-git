app.controller('indexController',function($scope,loginService){


    //显示当前用户名
    $scope.showLoginName=function(){
        loginService.loginName().success(
            function(response){
                alert(11111);
                alert(response.loginName);
                $scope.loginName=response.loginName;
            }
        );
    }


});