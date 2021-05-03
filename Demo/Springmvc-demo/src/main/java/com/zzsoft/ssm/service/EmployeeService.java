package com.zzsoft.ssm.service;

import com.zzsoft.ssm.pojo.Employee;


public interface EmployeeService {

    String login(Employee employee);
    String regist(Employee employee);
}
