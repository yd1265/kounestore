package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.security.UserRole;

public interface UserRoleRepository  extends CrudRepository<UserRole, Long>{


}
