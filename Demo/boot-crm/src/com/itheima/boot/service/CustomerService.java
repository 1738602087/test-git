package com.itheima.boot.service;

import com.itheima.boot.pojo.Customer;
import com.itheima.boot.pojo.QueryVo;
import com.itheima.boot.util.Page;

public interface CustomerService {

	Page<Customer> getCustomerList(QueryVo queryVo);
	Customer getCustomerById(Long id);
	void updateCustomer(Customer customer);
	void deleteCustomer(Long id);
}
