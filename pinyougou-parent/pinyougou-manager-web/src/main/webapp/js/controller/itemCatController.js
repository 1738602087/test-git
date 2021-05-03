 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}

	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;
				alert(response);
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			console.log(1111);
			serviceObject=itemCatService.update( $scope.entity ); //修改
			console.log(11111);
		}else{
            $scope.entity.parentId=$scope.parentId;//赋予上级ID,即将这个上一级的id给到我们新建分类的id
			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}


	//批量删除 
	$scope.dele=function(){
		//当我们点击这个删除的时候我们首先进行判断我们当前所处的分类是顶级分类还是
		//一级分类，如果是顶级分类或者一级分类的时候我们就不执行这个删除逻辑，因为
		//该商品分类列表下面还有商品分类，不能够进行删除，如果当前处于第三级分类的时候
		//我们就可以直接进行删除
		/*alert($scope.selectIds);
		$scope.findByParentId($scope.selectIds);
		if($scope.list==[]){*/
		if($scope.grade!=1&$scope.grade!=2){
            //获取选中的复选框
            itemCatService.dele( $scope.selectIds ).success(
                function(response){
                    if(response.success){
                        //alert($scope.selectIds);//查看我们选中的这个商品列表id
                        $scope.reloadList();//刷新列表
                        $scope.selectIds=[];
                    }
                }
            );
        }
        else{
		    alert("该商品下面还有分类或商品的分类id选择错误，不能够删除");
		}
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	
	$scope.grade=1;//当前级别
	//设置级别 ,这个函数主要是用于我们点击这个查询下级的按钮的时候设置面包屑导航中的当前级别值+1.
	//比如说我们当前在1级，之后我们点击这个列表的查询下级按钮这个时候它就会调用该函数，将这个级别设置为2，
	//并且执行下面的这个selectList方法，
	$scope.setGrade=function(value){
		$scope.grade=value;
	}
	
	//操作面包屑的方法，这里我们首先要对面包屑的级别做一个判断因为级别不同，这个操作不同
	$scope.selectList=function(p_entity){


		alert(JSON.stringify(p_entity));
		alert($scope.grade);
		/*
		* 顶级分类的时候也就是我们面包屑的级别为1，这个时候我们面包屑分类的二级分类和三级分类都为null*/
		if($scope.grade==1){
			$scope.entity_1=null;
			$scope.entity_2=null;
		}
        /*
        * 二级分类的时候也就是我们面包屑的级别为2，这个时候我们面包屑分类的二级分类为点击查询下级的entity，三级分类都为null*/
		if($scope.grade==2){
			
			$scope.entity_1=p_entity;
			$scope.entity_2=null;
		}
        /*
        * 三级分类的时候也就是我们面包屑的级别为3，这个时候我们面包屑分类的二级分类不变，三级分类为点击查询下级的entity*/
		if($scope.grade==3){
			$scope.entity_2=p_entity;
		}
		
		$scope.findByParentId(p_entity.id);
		
	}

	/*新增商品分类*/
	/*
	 我们需要一个变量去记住上级ID，在保存的时候再根据这个ID来新增分类
     修改itemCatController.js,  定义变量，我们的这个变量也就是我们
     的上一级分类的id，这里我们首先声明一个全局的分类id代指我们页面初始
     化加载时候的父级ID，当我们在页面中点击这个查询下一级的时候我们需要把
     这个id的值进行更新，这个id的值的更新是应该通过我们selectList方法中的
     p_entity.id进行得到
    */
	$scope.parentId=0; /*上一级ID*/
	$scope.findByParentId=function(parentId) {
		alert(parentId);
        $scope.parentId = parentId;
        itemCatService.findByParentId(parentId).success(
        	function(response) {
        		alert(JSON.stringify(response));
                $scope.list=response;
               }
        );

    }

    /*参照这个商品品牌列表的下拉功能第三天下午利用select组件进行实现*/
    //$scope.type_template={data:[{id:35,text:'35'},{id:36,text:'36'},{id:37,text:'37'}]};//模板列表

    //$scope.type_template={data:[]};//模板列表
    //读取模板列表，从后端数据库中进行读取
    $scope.findTemplateList=function(){
        itemCatService.selectOptionList().success(
            function(response){
            	alert(response);
                //$scope.type_template={data:response};
                var option;
                option="<option>"+"选择商品类型模板"+"</option>" ;
				for(i=0;i<response.length;i++){//循环，i为下标从0开始，response[i]为集合中对应的第i个元素
                    option += "<option value='"+response[i]+"'>"+response[i]+"</option>";
                 alert(response[i]);//35,37,33
                }
                $("#typeTemplate").html(option);//将循环拼接的字符串插入第二个下拉列表
            }
        );
    }
});
