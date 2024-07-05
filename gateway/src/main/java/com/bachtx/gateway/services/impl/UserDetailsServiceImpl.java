package com.bachtx.gateway.services.impl;

import com.bachtx.gateway.entities.User;
import com.bachtx.gateway.entities.UserPrincipal;
import com.bachtx.gateway.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Load user {}", username);
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (user.getRoles().isEmpty()) {
            String message = "User " + username + " doesn't have any role";
            log.warn(message);
            throw new UsernameNotFoundException(message);
        }
        log.info("Load user {} successfully", username);
        return new UserPrincipal(user);
    }
}
