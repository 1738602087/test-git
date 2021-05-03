 //控制层 
app.controller('brandController' ,function($scope,$controller   ,brandService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		brandService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		brandService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		brandService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=brandService.update( $scope.entity ); //修改  
		}else{
			serviceObject=brandService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				alert(response);//返回值为[object,object]
                alert(JSON.stringify(response));//返回值为{"message":"增加成功","success":true}
				//是一个json对象的格式，json常见的数据格式第三种，多个无序的数据对象，
				/*
				* 	3：多个无序的数据：对象
                  	   用{}包含起来，其元素必须由key-value组成，key是一个字符串，value是任意类型数据，
                  	   Key与value之间用:号隔开，两个key-value之间使用，号隔开，通过object.key的形式访问数据。
                      (对象表示为键值对，数据由逗号分隔，花括号保存对象，方括号保存数组)
                 	对象
                 	例子：{“name”:”sunwukong”, ”age”:18}*/

				/*所以这里response.success也就是通过object.key的形式访问数据，也就是说response.success的值为true，
				*这个时候就代表我们增加品牌的操作成功完成，这个时候我们就需要重新刷新页面。否则也就是我们增加或者修改的操作
				*失败那么就会弹出这个错误信息。
				* */
				if(response.success){
					//重新查询,这个方法是在baseController.js文件里面
		        	$scope.reloadList();//重新查询
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框
		alert($scope.selectIds);
		brandService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		brandService.search(page,rows,$scope.searchEntity).success(
			function(response){
				alert(page);
				alert(rows);
				alert(JSON.stringify($scope.searchEntity));
				alert(JSON.stringify(response));
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	
