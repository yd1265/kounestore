package com.bookstore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserService;
import com.bookstore.utility.SecurityUtility;
@EnableAutoConfiguration
@SpringBootApplication
public class BookstoreApplication  implements CommandLineRunner{
   @Autowired
   private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
       User user1=new User();
       user1.setFirstName("youssouf");
       user1.setLastName("Diallo");
       user1.setUsername("ydiallo");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("diallo"));
		user1.setEmail("youssoufkounet@gmail.com");
		
		Set<UserRole> userRoles=new HashSet<UserRole>();
		Role role1=new Role();
		  role1.setName("ROLE_USER");
		  userRoles.add(new UserRole(user1,role1));
		  userService.createUser(user1, userRoles);
	}
	
	
}
