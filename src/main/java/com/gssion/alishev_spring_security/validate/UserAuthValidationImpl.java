package com.gssion.alishev_spring_security.validate;

import com.gssion.alishev_spring_security.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserAuthValidationImpl implements UserAuthValidation{

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserAuthValidationImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails getUserByName(String userName) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(userName);
    }

    @Override
    public void validateUserCredentials(Authentication authentication) throws AuthenticationException {
        String passedUserName = authentication.getName();
        String passedUserPassword = authentication.getCredentials().toString();

        UserDetails user = getUserByName(passedUserName);

        if (!user.getPassword().equals(passedUserPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }


    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        String passedUserName = person.getName();
        String passedUserPassword = person.getPassword();

        UserDetails user = userDetailsService.loadUserByUsername(passedUserName);

        if (!user.getPassword().equals(passedUserPassword)) {
            errors.rejectValue("password", "", "Incorrect password");
        }
    }
}
