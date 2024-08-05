package com.ebos.ServiceImplimentation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ebos.Request.CreateStudentRequest;
import com.ebos.Request.CreateSubjectRequest;
import com.ebos.Request.LoginRequest;
import com.ebos.Response.JwtAuthenticationResponse;
import com.ebos.Service.AdminService;
import com.ebos.exception.AppException;
import com.ebos.repository.RoleRepository;
import com.ebos.repository.SubjectRepository;
import com.ebos.repository.UserRepository;
import com.ebos.security.JwtTokenProvider;
import com.ebos.tables.Role;
import com.ebos.tables.RoleName;
import com.ebos.tables.Subject;
import com.ebos.tables.User;

@Service
public class AdminServiceImpl implements AdminService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

  	@Autowired
	private AuthenticationManager authenticationManager;

  	@Autowired
	private JwtTokenProvider tokenProvider;
  	
  	@Autowired
  	private RoleRepository roleRepository;
	
	

		@Override
		public Map<String, Object> createStudent(CreateStudentRequest req) {
			Map<String,Object> map=new HashMap<>();
			try {
				User user=new User();
				
				user.setName(req.getName());
				user.setAddress(req.getAddress());
				user.setPassword(req.getPassword());
				user.setUsername(req.getUsername());
				user.setEmail(req.getEmail());
				
				 Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
			                .orElseThrow(() -> new AppException("User Role not set."));
	
			        user.setRoles(Collections.singleton(userRole));
				userRepository.save(user);
				
				map.put("status", true);
				map.put("message", "Created Student");
				
				
			}catch(Exception e) {
				map.put("status", false);
				map.put("message", "error occured");
			}
			return map;
		}

	@Override
	public Map<String, Object> getAllStudentList() {
	    Map<String, Object> map = new HashMap<>();
	    try {
	        List<User> allUsers = userRepository.findAll();
	       
	        map.put("users", allUsers);
	    } catch (Exception e) {
	    	map.put("status", false);
			map.put("message", "error occured");
	    }
	    return map;
	}
	
	
	@Override
	public Map<String, Object> createSubject(CreateSubjectRequest req) {
		Map<String,Object> map=new HashMap<>();
		try {
			Subject subject=new Subject();
			
			subject.setSubjectName(req.getSubjectName());
		
			
			subjectRepository.save(subject);
			
			map.put("status", true);
			map.put("message", "Created Subject");
			
			
		}catch(Exception e) {
			map.put("status", false);
			map.put("message", "error occured");
		}
		return map;
	}

	@Override
	public Map<String, Object> adminLogin(LoginRequest req) {
		Map<String, Object> map = new HashMap<>();
		try {
			if (!userRepository.existsByEmail(req.getEmail())) {
				map.put("status", false);
				map.put("message", "Incorrect email");
			} else {
				Optional<User> newuser = userRepository.findByEmail(req.getEmail());
				User user=newuser.get();
				String role = null;
				for (Role r : user.getRoles()) {
					if (r.getId() == 1) {
						role = "ADMIN";
					}
				}
				if (role == null || !role.equalsIgnoreCase("ADMIN")) {
					map.put("status", false);
					map.put("message", "Admin Role Missing");
				}
				else if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
					Authentication authentication = authenticationManager
							.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					String jwtToken = tokenProvider.generateToken(authentication);

					map.put("jwtToken", new JwtAuthenticationResponse(jwtToken));
					map.put("status", true);
					map.put("message", "Logged in Successfully");
					
				} else {
					map.put("status", false);
					map.put("message", "Incorrect password");
				}
			}
		} catch (Exception e) {
			map.put("status", false);
			map.put("message", "Error occured" +e.getMessage());
			
		}
		return map;
	}
	


	
	

}
