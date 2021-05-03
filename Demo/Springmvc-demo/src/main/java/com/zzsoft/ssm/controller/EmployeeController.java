package com.zzsoft.ssm.controller;

import com.zzsoft.ssm.pojo.Employee;
import com.zzsoft.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController{
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/tologin")
    public String tologin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(Employee employee, Model model) {
        String msg = employeeService.login(employee);
        model.addAttribute("msg",msg);
        if (msg.equals("用户名或密码错误")) {
            return "login";
        } else {
            return "success";
        }
    }

    @RequestMapping("/toregist")
    public String toregist(Employee employee) {
        return "regist";
    }
    @RequestMapping("/regist")
    public String register(Employee employee,Model model) {
        System.out.println(111);
        String msg = employeeService.regist(employee);
        model.addAttribute("msg", msg);
        return "login";
    }
}
