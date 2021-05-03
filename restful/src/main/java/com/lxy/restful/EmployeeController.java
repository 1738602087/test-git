/**
 * @ClassName : EmployeeController  //类名
 * @Description : 员工类的控制器层  //描述
 * @Author : lxy //作者
 * @Date: 2021-02-13 10:47  //时间
 */
package com.lxy.restful;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class EmployeeController {

    /**
     * 方法描述 获取所有员工对象
     * @param
     * @return: List<Employee>
     * @Author: lxy
     * @date 2021/2/13 10:55
     */
    //设计Restful接口的步骤,Restful接口也就是我们访问controller，controller给我们相应的数据
    /*  1.确定需求，获取所有的员工对象，
    *   2.确定资源(我们需要首先确定资源是数据库的表对象还是抽象的对象)，员工对象，员工资源也就是对应于数据库中的某一张员工表
    *   tb_employee,所以资源即员工对象employee,我们一般都是使用名词的复数形式，所以我们就是employees.
    *            因为我们查询全部，所以我们就不需要有这个/Id路径。
    *   3.确定请求方式，获取也就是查询，所以请求方式为Get，新增/增加请求方式为Post,
    *                 修改请求方式为Put,删除请求方式为Delete.
    *   4.确定返回结果(类型，头信息，状态码,这三个选项中我们开发过程中一般都是确定这个返回结果的类型就可以了
    *   ，头信息吧和状态码按照之前的惯例就可以)
    *           返回结果类型为员工集合，
    *           返回结果头信息为json格式，所以Contente-Type="application/json",
    *           所以这里我们使用了@Responsebody注解
    *           状态码:200
    *    5.*/
    @RequestMapping(value = "/employees",method = RequestMethod.GET)
    // @GetMapping("employees")
    @ResponseBody
    public List<Employee> findAll(){
       List<Employee> list = new ArrayList<>();
        Employee employee1 = new Employee(1L, "张三");
        Employee employee2 = new Employee(2L, "李四");
        Employee employee3 = new Employee(3L, "王五");
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        return list;
    }

    /**
     * 方法描述 获取某个员工的信息
     * @param  Long Id
     * @return: Employee
     * @Author: lxy
     * @date 2021/2/13 12:20
     */
    /*  1.确定需求，获取某个员工的信息，
     *   2.确定资源(我们需要首先确定资源是数据库的表对象还是抽象的对象)，员工对象，员工资源也就是对应于数据库中的某一张员工表
     *   tb_employee,所以资源即员工对象employee,我们一般都是使用名词的复数形式，所以我们就是employees.
     *            因为我们查询某一个，所以我们就需要有这个/Id路径。所以/employees/{Id}，这里我们{Id}即我们使用
     *            路径占位符，
     *   3.确定请求方式，获取也就是查询，所以请求方式为Get，新增/增加请求方式为Post,
     *                 修改请求方式为Put,删除请求方式为Delete.
     *   4.确定返回结果(类型，头信息，状态码,这三个选项中我们开发过程中一般都是确定这个返回结果的类型就可以了
     *   ，头信息吧和状态码按照之前的惯例就可以)
     *           返回结果类型为员工对象，
     *           返回结果头信息为json格式，所以Contente-Type="application/json",
     *           所以这里我们使用了@Responsebody注解
     *           状态码:200
     *    5. 如果@PathVariable注解并没有设置value，那么默认就是去路径上面
     *    找相同名称的参数*/
    @GetMapping("employees/{Id}")
    @ResponseBody
    public Employee getEmployeeById(@PathVariable("Id") Long Id){
        return new Employee(Id,"李向阳");
    }

    /**
     * 方法描述 获取某个员工的信息
     * @param  Long Id
     * @return: void
     * @Author: lxy
     * @date 2021/2/13 12:20
     */
    /*  1.确定需求，获取某个员工的信息，
     *   2.确定资源(我们需要首先确定资源是数据库的表对象还是抽象的对象)，员工对象，员工资源也就是对应于数据库中的某一张员工表
     *   tb_employee,所以资源即员工对象employee,我们一般都是使用名词的复数形式，所以我们就是employees.
     *            因为我们查询某一个，所以我们就需要有这个/Id路径。所以/employees/{Id}，这里我们{Id}即我们使用
     *            路径占位符，
     *   3.确定请求方式，获取也就是查询，所以请求方式为Get，新增/增加请求方式为Post,
     *                 修改请求方式为Put,删除请求方式为Delete.
     *   4.确定返回结果(类型，头信息，状态码,这三个选项中我们开发过程中一般都是确定这个返回结果的类型就可以了
     *   ，头信息吧和状态码按照之前的惯例就可以)
     *           返回结果类型为员工对象，
     *           返回结果头信息为json格式，所以Contente-Type="application/json",
     *           所以这里我们使用了@Responsebody注解
     *           状态码:200
     *    5. 如果@PathVariable注解并没有设置value，那么默认就是去路径上面
     *    找相同名称的参数*/
    @DeleteMapping("employees/{Id}")
    @ResponseBody
    public void deleteEmployeeById(@PathVariable("Id") Long Id, HttpServletResponse response){
    System.out.println("删除id为"+Id+"的员工信息");
      response.setStatus(203);
    }

    /*   1.确定需求，获取某个员工某个月的薪资记录
     *   2.确定资源(我们需要首先确定资源是数据库的表对象还是抽象的对象)，这里我们资源有两个薪资对象和员工对象，
     *   员工资源也就是对应于数据库中的某一张员工表tb_employee,所以资源即员工对象employee,我们一般
     *   都是使用名词的复数形式，所以我们就是employees.这里我们的薪资对象资源也就是对应于数据库中的
     *   这个tb_salary,我们的资源路径的写法就是某个员工对应于/employees/{Id},某个月的薪资记录也就
     *   是对应于我们/salaries/{month}，所以我们最后的路径也就是/employees/{Id}/salaries/{month}
     *   3.确定请求方式，获取也就是查询，所以请求方式为Get，新增/增加请求方式为Post,
     *                 修改请求方式为Put,删除请求方式为Delete.
     *   4.确定返回结果(类型，头信息，状态码,这三个选项中我们开发过程中一般都是确定这个返回结果的类型就可以了
     *   ，头信息吧和状态码按照之前的惯例就可以)
     *           返回结果类型为薪资对象，
     *           返回结果头信息为json格式，所以Contente-Type="application/json",
     *           所以这里我们使用了@Responsebody注解
     *           状态码:200
     *    5. 如果@PathVariable注解并没有设置value，那么默认就是去路径上面
     *    找相同名称的参数
     *    6.@DateTimeFormat注解 前台传递日期参数到后台接收时使用的注解
     *      @JsonFormat注解 后台返回json数据给前台时使用的注解*/
    @GetMapping("/employees/{Id}/salaries/{month}")
    @ResponseBody
    public Salary getSalaryByEmployee(@PathVariable("Id") Long Id, @PathVariable("month")
                          @DateTimeFormat(pattern = "yyyy-MM") Date month){
        return new Salary(1L,Id, BigDecimal.TEN,month);
    }

}

