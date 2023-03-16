package com.app.petsvets.service;

import org.springframework.security.core.Authentication;

import com.app.petsvets.model.LoginResponseModel;

public interface JwtService {

	LoginResponseModel generateToken(String userName, Authentication auth);

}
