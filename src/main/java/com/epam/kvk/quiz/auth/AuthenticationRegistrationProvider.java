package com.epam.kvk.quiz.auth;

import com.epam.kvk.quiz.entity.User;
import com.epam.kvk.quiz.entity.UserRole;
import com.epam.kvk.quiz.entity.enums.UserRoleEnum;
import com.epam.kvk.quiz.repository.UserRepository;
import com.epam.kvk.quiz.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationRegistrationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<UserRole> grants;

        if (userRepository.existsByName(name)) {
            if (userRepository.existsByNameAndPassword(name, password)) {
                //TODO check ws active session
                grants = userRoleRepository.findAllByUser_NameAndUser_Password(name, password);
            } else {
                return null;
            }
        } else {
            User user = userRepository.createUser(name, password);
            grants = user.getRoles();
        }
        return new UsernamePasswordAuthenticationToken(name, password, grants);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
