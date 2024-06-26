package com.example.config;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

import com.example.model.entity.Authority;
import com.example.model.entity.Role;
import com.example.model.entity.User;
import com.example.repository.AuthorityRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

import java.util.Arrays;

@Component
public class RoleAuthorityInitializer {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String SUPER_ADMIN_EMAIL = "olojedechristopher24@gmail.com";
	
	@EventListener
	public void initializeRolesAndAuthority(ApplicationReadyEvent event) {
		
		System.out.println("Hello, Christopher");
		
		// Course AUTHORITY
		Authority createCourse = createAuthority("CREATE_COURSE_AUTHORITY");
		Authority getCourse = createAuthority("GET_COURSE_AUTHORITY");
		Authority updateCourse = createAuthority("UPDATE_COURSE_AUTHORITY");
		Authority deleteCourse = createAuthority("DELETE_COURSE_AUTHORITY");
		Authority viewCourse = createAuthority("VIEW_COURSE_AUTHORITY");
		
		//STUDENT AUTHORITY
		Authority createStudent = createAuthority("Create_STUDENT_AUTHORITY");
		Authority getStudent = createAuthority("GET_STUDENT_AUTHORITY");
		Authority updateStudent = createAuthority("UPDATE_STUDENT_AUTHORITY");
		Authority deleteStudent = createAuthority("DELETE_STUDENT_AUTHORITY");
		Authority viewStudent = createAuthority("VIEW_STUDENT_AUTHORITY");
		
		// ADMIN AUTHORITY
		Authority createAdmin = createAuthority("CREATE_ADMIN_AUTHORITY");
		Authority getAdmin = createAuthority("GET_ADMIN_AUTHORITY");
		Authority updateAdmin = createAuthority("UPDATE_ADMIN_AUTHORITY");
		Authority deleteAdmin = createAuthority("DELETE_ADMIN_AUTHORITY");
		Authority viewAdmin = createAuthority("VIEW_ADMIN_AUTHORITY");
		
		
		
		Role superAdminRole = createRole("ROLE_SUPER_ADMIN", Arrays.asList(
				createAdmin, 
				getAdmin, 
				deleteAdmin,
				viewAdmin,
				getStudent,
				deleteStudent,
				viewStudent,
				getCourse,
				deleteCourse,
				viewCourse
				));
		
		Optional<User> optional = userRepository.findByEmail(SUPER_ADMIN_EMAIL);
		
		if (optional.isEmpty()) {
			
			User superAdmin = new User();
			superAdmin.setEmail("olojedechristopher24@gmail.com");
			superAdmin.setEmailVerificationStatus(true);
			superAdmin.setEmailVerificationToken(null);
			superAdmin.setPassword("christopher");
			superAdmin.setPasswordResetToken(null);
			
			String salt = KeyGenerators.string().generateKey();
			String secret = "christopher";
			TextEncryptor encryptor = Encryptors.text(secret, salt);
			String encryptedPin = encryptor.encrypt("123456");
			
			superAdmin.setPin(encryptedPin);
			superAdmin.setRoles(Arrays.asList(superAdminRole));
			
			userRepository.save(superAdmin);
		}
	}

	private Authority createAuthority(String name) {
		
		Optional<Authority> optional = authorityRepository.findByName(name);
		
		if (optional.isEmpty()) {
			Authority authority = new Authority(name);
			
			return authorityRepository.save(authority);
		}
		else {
			return null;
		}
	}

	private Role createRole(String name, Collection<Authority> authorities) {
		
		Optional<Role> optional = roleRepository.findByName(name);
		
		if (optional.isEmpty()) {
			Role role = new Role(name);
			role.setAuthorities(authorities);
			
			return roleRepository.save(role);
		}
		else {
			
			return null;
		}
	}
}
