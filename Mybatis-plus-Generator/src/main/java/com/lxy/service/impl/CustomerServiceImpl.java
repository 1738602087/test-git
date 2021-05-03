package com.lxy.service.impl;

import com.lxy.model.Customer;
import com.lxy.dao.CustomerMapper;
import com.lxy.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxy
 * @since 2020-10-05
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
