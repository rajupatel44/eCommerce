package com.eCommerce.security;

import com.eCommerce.controller.UserController;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // Inject dependencies as needed for your authentication process
    private final UserController userService;


    public CustomAuthenticationProvider(UserController userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Custom authentication logic using injected services
        boolean authenticationResult = userService.authenticate(username, password);

        if (authenticationResult) {
            // Authentication successful, create an authenticated token
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        } else {
            // Failed authentication
            throw new BadCredentialsException("Authentication failed for " + username);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
