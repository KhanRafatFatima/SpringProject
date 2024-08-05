package com.ebos.ServiceImplimentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebos.Service.SubjectService;
import com.ebos.repository.SubjectRepository;
import com.ebos.tables.Subject;
import com.ebos.tables.User;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public Map<String, Object> getAllSubjects() {
		  Map<String, Object> map = new HashMap<>();
		    try {
		        List<Subject> allSubject = subjectRepository.findAll();
		       
		        map.put("users", allSubject);
		    } catch (Exception e) {
		    	map.put("status", false);
				map.put("message", "error occured");
		    }
		    return map;
	}

}
