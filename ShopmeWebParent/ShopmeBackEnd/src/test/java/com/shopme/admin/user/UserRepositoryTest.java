package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateUserOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userMalli = new User("malli@gmail.com", "Malli@1234", "Mallikarjuna", "Gali");
		userMalli.addRole(roleAdmin);
		User savedUser = repo.save(userMalli);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	@Disabled
	public void testCreateUserWithTwoRoles() {
		User userArjun = new User("arjun@gmail.com", "Arjun@1234", "Arjun", "Ambati");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userArjun.addRole(roleEditor);
		userArjun.addRole(roleAssistant);
		User savedUser = repo.save(userArjun);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}

	@Test
	public void testGetUserById() {
		User userArjun = repo.findById(2).get();
		System.out.println(userArjun);
		assertThat(userArjun).isNotNull();
	}

	@Test
	public void testUpdateUsrDetails() {
		User userArjun = repo.findById(2).get();
		userArjun.setEnabled(true);
		userArjun.setEmail("ar@gmail.com");
		repo.save(userArjun);
	}

}
