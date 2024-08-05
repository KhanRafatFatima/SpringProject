package com.ebos.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebos.Request.CreateStudentRequest;
import com.ebos.Request.CreateSubjectRequest;
import com.ebos.Service.AdminService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/createStudent")
	public ResponseEntity<Map<String, Object>> createStudent(@Valid @RequestBody CreateStudentRequest req) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = adminService.createStudent(req);

			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllStudent")
	public ResponseEntity<Map<String, Object>> getStudentList() {
		Map<String, Object> map = new HashMap<>();
		try {
			map = adminService.getAllStudentList();

			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/createSubject")
	public ResponseEntity<Map<String, Object>> createSubject(@Valid @RequestBody CreateSubjectRequest req) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = adminService.createSubject(req);

			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
