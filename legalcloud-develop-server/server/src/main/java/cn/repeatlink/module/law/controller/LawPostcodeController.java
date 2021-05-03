package cn.repeatlink.module.law.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.repeatlink.framework.common.AjaxResult;
import cn.repeatlink.module.law.common.Define;
import cn.repeatlink.module.manage.dto.PostcodeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(Define.APP_URL_PREFIX + "/postcode")
@Api(value = "邮编相关", produces = "application/json", tags = "邮编相关接口")
@ApiSort(30)
public class LawPostcodeController extends BaseLawController {

	@ApiOperationSupport(order = 1)
	@GetMapping("/{postcode}")
	@ApiOperation(value = "邮编檢索", notes = "邮编檢索", produces = "application/json")
	@ApiImplicitParam(required = true, dataType = "String", paramType = "path", name = "postcode", value = "完整邮编")
	public AjaxResult<PostcodeInfo> getPostcodeInfo(@PathVariable("postcode") String postcode) {
		Record record = Db.findFirst("select * from postcode where postcode = ?", postcode);
		PostcodeInfo info = null;
		if(record != null) {
			info = new PostcodeInfo();
			info.setPostcode(postcode);
			info.setAddr1(record.getStr("addr1"));
			info.setAddr2(record.getStr("addr2"));
			info.setAddr3(record.getStr("addr3"));
		}
		return AjaxResult.success(info);
	}
	
	@ApiOperationSupport(order = 2)
	@GetMapping("/addr1list")
	@ApiOperation(value = "addr1列表", notes = "addr1列表", produces = "application/json")
	public AjaxResult<List<String>> getAllAddr1List() {
		List<String> list = Db.query("select addr1 from postcode GROUP BY addr1 ORDER BY postcode");
		return AjaxResult.success(list);
	}
	
	@ApiOperationSupport(order = 3)
	@GetMapping("/addr2list")
	@ApiOperation(value = "addr2列表", notes = "addr2列表", produces = "application/json")
	public AjaxResult<List<String>> getAllAddr2List(@RequestParam("addr1") String addr1) {
		List<String> list = Db.query("select addr2 from postcode where addr1=? GROUP BY addr2 ORDER BY postcode", addr1);
		return AjaxResult.success(list);
	}
	
	@ApiOperationSupport(order = 3)
	@GetMapping("/addrlist")
	@ApiOperation(value = "addr列表", notes = "addr列表", produces = "application/json")
	public AjaxResult<Kv> getAllAddrList() {
		List<Record> list = Db.find("select addr1,addr2,addr3 from postcode GROUP BY addr1,addr2,addr3 ORDER BY postcode");
		Kv kv = Kv.create();
		if(list != null) {
			for(Record record : list) {
				String addr1 = record.getStr("addr1");
				if(StringUtils.isNotBlank(addr1)) {
					Kv data = kv.getAs(addr1);
					if(data == null) {
						data = Kv.create();
						kv.set(addr1, data);
					}
					String addr2 = record.getStr("addr2");
					if(StringUtils.isNotBlank(addr2)) {
						List<String> data2 = data.getAs(addr2);
						if(data2 == null) {
							data2 = new ArrayList<>();
							data.set(addr2, data2);
						}
						String addr3 = record.getStr("addr3");
						if(StringUtils.isNotBlank(addr3)) {
							data2.add(addr3);
						}
					}
				}
			}
		}
		return AjaxResult.success(kv);
	}
	
	
}
