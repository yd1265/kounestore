package com.bookstore.service;

import com.bookstore.domain.UserShipping;

public interface UserShippingService {
	
public	UserShipping findById(Long id);
    void removeById(Long id);
}
