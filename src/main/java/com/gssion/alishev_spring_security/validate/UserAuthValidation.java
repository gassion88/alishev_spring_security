package com.gssion.alishev_spring_security.validate;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Validator;

public interface UserAuthValidation extends Validator {

    UserDetails getUserByName(String userName) throws AuthenticationException;

    void validateUserCredentials(Authentication authentication) throws AuthenticationException;
}