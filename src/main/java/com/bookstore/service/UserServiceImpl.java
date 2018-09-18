package com.bookstore.service;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.PasswordResetTokenRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.repository.UserRoleRepository;
@Service
public class UserServiceImpl implements UserService{
	
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private static final Logger LOG= LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public PasswordResetToken getPasswordResetToken(String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		
		PasswordResetToken passwordResetToken=new PasswordResetToken(user, token);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public User findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		
		User localUser=userRepository.findByUsername(user.getUsername());
		
		if(localUser!=null){
			LOG.info("User already Exist");
		}else {
			for(UserRole ur: userRoles){
          roleRepository.save(ur.getRole());
			
			}
			
			user.getUserRoles().addAll(userRoles);
			localUser=userRepository.save(user);
		}
		
		
		return localUser;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
		
	}
}


