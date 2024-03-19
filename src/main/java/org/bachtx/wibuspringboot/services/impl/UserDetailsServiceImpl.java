package org.bachtx.wibuspringboot.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bachtx.wibuspringboot.entities.User;
import org.bachtx.wibuspringboot.entities.UserPrincipal;
import org.bachtx.wibuspringboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Load user " + username);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    String message = "User " + username + " not found";
                    log.warn(message);
                    return new UsernameNotFoundException(message);
                });
        if (user.getRoles().isEmpty()) {
            String message = "User " + username + " doesn't have any role";
            log.warn(message);
            throw new UsernameNotFoundException(message);
        }
        log.info("Load user " + username + " successfully");
        return new UserPrincipal(user);
    }
}
