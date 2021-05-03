package cn.repeatlink.module.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.repeatlink.framework.cache.CacheKit;
import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.demo.vo.DemoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/demo")
@Api(value="DEMO管理")
public class DemoController {
	

	@ApiOperation(value="hello",notes = "hello notes")
	@GetMapping
	public AjaxResult<Object> hello(){
		
		try {
			List<Entity> list = Db.use().query("select * from sys_job");
//			for(int i = 0 ; i < 2000; i++) {
//				list = DbUtil.use().query("select * from sys_config");
//			}
			// System.out.println(list.size());
			
			CacheKit.put("test", "key", list);
			list = CacheKit.use("test").get("key");
			return AjaxResult.success(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return AjaxResult.success("hello");
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "根据id删除商品", notes = "商品删除")
    @ApiImplicitParam(name = "id", value = "商品ID",  paramType = "path", required = true, dataType =  "Long",example = "1")
	public AjaxResult<String> hello(@PathVariable   Long id){
		return AjaxResult.success("hello");
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation(value = "根据id查找用户", notes = "根据id查找用户")
    @ApiImplicitParam(name = "id", value = "用户ID",  paramType = "path", required = true, dataType =  "Long",example = "1")
	public AjaxResult<DemoData> get(@PathVariable   Long id){
		return AjaxResult.success(new DemoData());
	}
	
}
