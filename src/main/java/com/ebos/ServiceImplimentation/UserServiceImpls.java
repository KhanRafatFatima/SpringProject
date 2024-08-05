package com.ebos.ServiceImplimentation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ebos.Request.LoginRequest;
import com.ebos.Response.JwtAuthenticationResponse;
import com.ebos.Service.UserService;
import com.ebos.repository.UserRepository;
import com.ebos.security.JwtTokenProvider;
import com.ebos.tables.User;

@Service
public class UserServiceImpls implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

  	@Autowired
	private AuthenticationManager authenticationManager;

  	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Override
	public Map<String, Object> employeeLogin(LoginRequest req) {
	    Map<String, Object> map = new HashMap<>();
	    try {
	        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());

	        if (optionalUser.isEmpty()) {
	            map.put("status", "Incorrect");
	            return map;
	        }

	        User user = optionalUser.get();
	        
	        // Check if user has EMPLOYEE role (assuming EMPLOYEE role ID is 2)
	        boolean isEmployee = user.getRoles().stream().anyMatch(role -> role.getId() == 2);
	        
	        if (!isEmployee) {
	            map.put("status", "User Role Missing");
	            return map;
	        }

	        // Check if password matches
	        if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
	            Authentication authentication = authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(req.getStudentName(), req.getPassword()));

	            SecurityContextHolder.getContext().setAuthentication(authentication);

	            String jwtToken = tokenProvider.generateToken(authentication);

	            map.put("jwtToken", new JwtAuthenticationResponse(jwtToken));
	            map.put("status", true);
				map.put("message", "Logged in Successfully");
	        } else {
	        	map.put("status", false);
				map.put("message", "Incorrect password");
	        }

	    } catch (Exception e) {
	    	map.put("message", "Error occured" +e.getMessage());
			map.put("status", false);
	    }
	    return map;
	}


	
}
	