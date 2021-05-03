package com.itheima.boot.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheima.boot.pojo.BaseDict;
import com.itheima.boot.pojo.Customer;
import com.itheima.boot.pojo.QueryVo;
import com.itheima.boot.service.BaseDictService;
import com.itheima.boot.service.CustomerService;
import com.itheima.boot.util.Page;

/**
 * 客户管理Controller
 * <p>Title: CustomerController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class CustomerController {
	@Autowired
	private BaseDictService baseDictService;
	@Autowired
	private CustomerService customerService;
	@Value("${customer.source.code}")
	private String custSorceCode;
	@Value("${customer.industory.code}")
	private String custIndustoryCode;
	@Value("${customer.level.code}")
	private String custLevelCode;
    /*
    * 这里就是我们页面初始化的时候我们进行访问的方法，在这个方法内部
    * 我们需要取这个字典类表，所以我们首先需要注入我们的customerService对象，
    * 注入进来之后我们就可以去初始化我们的客户来源，通过这个
    * baseDictService.getDictListByTypeCode(custSorceCode)方法，这个
    * 参数我们应该从哪来，这里我们是直接在浏览器中访问这个url，哪来的页面，而且我们也不能够
    * 让用户在这个url的后面去添加参数，所以说我们就需要将这个客户行业，客户来源以及这个
    * 所属行业固定死写到我们的这个属性配置文件中，写到属性配置文件中简单，如何从这个属性配置
    * 文件中将我们的这个数据取出来，*/
	@RequestMapping("/customer/list")
	public String showCustomerList(QueryVo queryVo,Model model) throws Exception {
		//乱码处理
		if (StringUtils.isNotBlank(queryVo.getCustName())) {
			queryVo.setCustName(new String(queryVo.getCustName().getBytes("iso8859-1"), "utf-8"));
		}
		//初始化客户来源
		List<BaseDict> sourceList = baseDictService.getDictListByTypeCode(custSorceCode);
		//所属行业
		List<BaseDict> industoryList = baseDictService.getDictListByTypeCode(custIndustoryCode);
		//客户级别
		List<BaseDict> levelList = baseDictService.getDictListByTypeCode(custLevelCode);
		//把字典信息传递给页面
		model.addAttribute("fromType", sourceList);
		model.addAttribute("industryType", industoryList);
		model.addAttribute("levelType", levelList);
		//根据查询条件查询客户列表
		Page<Customer> page = customerService.getCustomerList(queryVo);
		//把客户列表传递给页面
		model.addAttribute("page", page);
		//参数回显
		model.addAttribute("custName", queryVo.getCustName());
		model.addAttribute("custSource", queryVo.getCustSource());
		model.addAttribute("custIndustry", queryVo.getCustIndustory());
		model.addAttribute("custLevel", queryVo.getCustLevel());
		
		return "customer";
	}
	
	@RequestMapping("/customer/edit")
	@ResponseBody
	public Customer getCustomerById(Long id) {
		Customer customer = customerService.getCustomerById(id);
		return customer;
	}
	
	@RequestMapping(value="/customer/update", method=RequestMethod.POST)
	@ResponseBody
	public String updateCustomer(Customer customer) {
		customerService.updateCustomer(customer);
		return "OK";
	}
	
	@RequestMapping("/customer/delete")
	@ResponseBody
	public String deleteCustomer(Long id) {
		customerService.deleteCustomer(id);
		return "OK";
	}
}
