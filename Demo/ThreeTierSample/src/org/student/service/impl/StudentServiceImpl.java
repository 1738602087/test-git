package org.student.service.impl;

import java.util.List;

import org.student.dao.IStudentDao;
import org.student.dao.impl.StudentDaoImpl;
import org.student.entity.Student;
import org.student.service.IStudentService;

//业务逻辑层：逻辑性的增删改查（ 增：查+增）  ，对dao层进行的组装
public class StudentServiceImpl implements IStudentService{
	IStudentDao studentDao = new StudentDaoImpl();
	//根据学号查询学生
	@Override
	public Student queryStudentBySno(int sno) {
		return studentDao.queryStudentBySno(sno);
	}
	//查询全部学生
	@Override
	public List<Student> queryAllStudents(){
		return studentDao.queryAllStudents() ;
	}
	
	@Override
	public boolean updateStudentBySno(int sno, Student student) {
		if(studentDao.isExist(sno)) {
			return studentDao.updateStudentBySno(sno, student) ;
		}
		return false ;
	}	
	
	@Override
	public boolean deleteStudentBySno(int sno) {
		if(studentDao.isExist(sno)) {
			return studentDao.deleteStudentBySno(sno) ;
		}
			return false ;
	}
	
	@Override
	public boolean addStudent(Student student) {
		if(!studentDao.isExist(student.getSno())) {//不存在
			
			return studentDao.addStudent(student) ; 
		}else {
			System.out.println("此人已存在！");
			return false ;
		}
	}
	@Override
	public List<Student> queryStudentsByPage(int currentPage, int pageSize) {
		
		return studentDao.queryStudentsByPage(currentPage, pageSize);
	}
	@Override
	public int getTotalCount() {
		return studentDao.getTotalCount();
	}
}
