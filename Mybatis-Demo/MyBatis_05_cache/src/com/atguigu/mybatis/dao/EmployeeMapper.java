package com.atguigu.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	//多条记录封装一个map：Map<Integer,Employee>:键是这条记录的
	// 主键，值是记录封装后的javaBean
	//@MapKey:告诉mybatis封装这个map的时候使用哪个属性作为map的key
	@MapKey("lastName")
	public Map<String, Employee> getEmpByLastNameLikeReturnMap(String lastName);
	
	//返回一条记录的map；key就是列名，值就是对应的值
	public Map<String, Object> getEmpByIdReturnMap(Integer id);
	//我们在执行这个查询操作的时候有时会返回一个list集合，集合中存放的是我们的这个学生对象或者员工对象
	// 也就是泛型，所以我们这里比如说从数据库中根据这个名字进行模糊查询，那么也就是我们查询的结果可能有多
	// 条数据，每一条数据都是一个对象，所以我们就是使用这个list集合作为这个方法的返回值，集合中的泛型是
	// student或者这个Employee对象
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	public Employee getEmpByMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	
	public Employee getEmpById(Integer id);

	public Long addEmp(Employee employee);

	public boolean updateEmp(Employee employee);

	public void deleteEmpById(Integer id);
	
}
