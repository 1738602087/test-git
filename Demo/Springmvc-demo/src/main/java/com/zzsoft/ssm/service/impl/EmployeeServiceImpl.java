package com.zzsoft.ssm.service.impl;

import com.zzsoft.ssm.dao.EmployeeMapper;
import com.zzsoft.ssm.pojo.Employee;
import com.zzsoft.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    public EmployeeMapper employeeMapper;

    @Override
    public String login(Employee employee) {
        Employee loginEmployee = employeeMapper.login(employee);
        if (loginEmployee==null){
            return "用户名密码错误";
        }else{
            return "你好："+employee.getUsername();
        }
    }

    @Override
    public String regist(Employee employee) {
        Employee selectedEmployee = employeeMapper.selectEmployee(employee);
        if(selectedEmployee==null){
            employeeMapper.regist(employee);
            return "注册成功可以登录";
        }else {
            return "用户名已存在";
        }
    }
}
