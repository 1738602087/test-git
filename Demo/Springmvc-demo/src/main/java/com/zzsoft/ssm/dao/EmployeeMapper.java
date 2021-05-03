package com.zzsoft.ssm.dao;

import com.zzsoft.ssm.pojo.Employee;

public interface EmployeeMapper {

    Employee login(Employee employee);

    void regist(Employee employee);

    Employee selectEmployee(Employee employee);
}
