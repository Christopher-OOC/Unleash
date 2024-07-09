package com.example.initializer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.model.entity.Admin;
import com.example.model.entity.Authority;
import com.example.model.entity.Role;
import com.example.model.entity.User;
import com.example.repository.AdminRepository;
import com.example.repository.AuthorityRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.utilities.PinEncoderUtils;
import com.example.utilities.PublicIdGeneratorUtils;

import java.util.Arrays;
import java.util.Date;

@Component
public class RoleAuthorityInitializer {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final String SUPER_ADMIN_EMAIL = "olojedechristopher24@gmail.com";
	
	@EventListener
	public void initializeRolesAndAuthority(ApplicationReadyEvent event) {
		
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
		
		
		// CREATE SUPER ADMIN ROLE
		Role superAdminRole = createRole("ROLE_SUPER_ADMIN", Arrays.asList());
		
		
		//CREATE STUDENT ROLE
		createRole("ROLE_STUDENT", Arrays.asList(createStudent, getStudent,
				updateStudent, viewStudent, getCourse, viewCourse));
		
		createRole("ROLE_INSTRUCTOR", Arrays.asList(createCourse, getCourse,
				updateCourse, viewCourse, viewStudent, getStudent));
		
		
		Optional<User> optional = userRepository.findByEmail(SUPER_ADMIN_EMAIL);
		
		if (optional.isEmpty()) {
			
			Admin admin = new Admin();
			admin.setAdminId(PublicIdGeneratorUtils.generateId(30));
			admin.setEmail(SUPER_ADMIN_EMAIL);
			admin.setFirstName("Olamide");
			admin.setLastName("Olojede");
			admin.setMiddleName("Christopher");
			admin.setDateAdded(new Date());
			
			User user = new User();
			user.setEmail(SUPER_ADMIN_EMAIL);
			user.setPublicUserId(admin.getAdminId());
			user.setEmailVerificationStatus(true);
			user.setEmailVerificationToken(null);
			user.setPassword(passwordEncoder.encode("christopher"));
			user.setPasswordResetToken(null);	
			user.setPin(PinEncoderUtils.encodePin("12345"));
			
			user.getRoles().add(superAdminRole);
			
			admin.setUser(user);
			
			adminRepository.save(admin);
		}
	}

	private Authority createAuthority(String name) {
		
		Optional<Authority> optional = authorityRepository.findByName(name);
		
		if (optional.isEmpty()) {
			Authority authority = new Authority(name);
			
			return authorityRepository.save(authority);
		}
		else {
			return optional.get();
		}
	}

	private Role createRole(String name, List<Authority> authorities) {
		
		Optional<Role> optional = roleRepository.findByName(name);
		
		if (optional.isEmpty()) {
			Role role = new Role(name);
			
			for (Authority authority : authorities) {
				authority.getRoles().add(role);
				role.getAuthorities().add(authority);
			}
			
			return roleRepository.save(role);
		}
		else {
			
			return optional.get();
		}
	}
}
