//package com.example.demo;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Arrays;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import com.example.model.entity.Authority;
//import com.example.model.entity.Role;
//import com.example.repository.RoleRepository;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace=Replace.NONE)
//@Rollback(true)
//
//public class RoleRepositoryTests {
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Test
//	final void testCreateRole() {
//		Role role = new Role();
//		role.setName("ROLE_ADMIN");
//
//		Authority a1 = new Authority("READ_AUTHORITY");
//		Authority a2 = new Authority("READ_AUTHORITY");
//		Authority a3 = new Authority("READ_AUTHORITY");
//
//		role.setAuthorities(Arrays.asList(a1, a2, a3));
//
//		Role saveRole = roleRepository.save(role);
//
//		assertTrue(saveRole.getAuthorities().size() == 3);
//		assertEquals(saveRole.getName(), role.getName());
//
//	}
//
//}
