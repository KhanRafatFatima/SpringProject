package com.ebos.Service;

import java.util.Map;

import com.ebos.Request.CreateStudentRequest;
import com.ebos.Request.CreateSubjectRequest;
import com.ebos.Request.LoginRequest;

public interface AdminService {
	
	public Map<String,Object> createStudent(CreateStudentRequest req);
	
	public Map<String,Object> getAllStudentList();
	
	public Map<String, Object> createSubject(CreateSubjectRequest req);
	
	public Map<String,Object> adminLogin(LoginRequest req);

}
