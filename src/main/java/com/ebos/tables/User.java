package com.ebos.tables;


import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private String name;
	
	private String address;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "student_subject", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<Subject> subjects = new HashSet<>();
	
	private String username; //for login credentials

	@NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	private String email; //for login credentials

	@NotBlank
	@Size(max = 100)
	private String password;  //for login credentials
	
	public  User(String name, String username, String email, String password) {
		this.name=name;
		this.username=username;
		this.email=email;
		this.password=password;
	}

}
