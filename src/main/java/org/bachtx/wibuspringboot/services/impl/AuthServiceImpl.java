package org.bachtx.wibuspringboot.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bachtx.wibuspringboot.core.events.OnRegistrationCompleteEvent;
import org.bachtx.wibuspringboot.dtos.request.LoginRequest;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.LoginResponse;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
import org.bachtx.wibuspringboot.entities.TokenEntity;
import org.bachtx.wibuspringboot.entities.User;
import org.bachtx.wibuspringboot.exceptions.EmailAlreadyExistsException;
import org.bachtx.wibuspringboot.exceptions.RegistrationVerifyErrorException;
import org.bachtx.wibuspringboot.exceptions.ServiceErrorException;
import org.bachtx.wibuspringboot.mappers.UserMapper;
import org.bachtx.wibuspringboot.repositories.TokenRepository;
import org.bachtx.wibuspringboot.repositories.UserRepository;
import org.bachtx.wibuspringboot.services.AuthService;
import org.bachtx.wibuspringboot.utils.TokenUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final TokenUtil tokenUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        log.trace("user register");
        User foundUser = userRepository.findByEmail(registerRequest.getEmail()).orElse(null);
        if (foundUser != null && foundUser.isVerified()) {
            throw new EmailAlreadyExistsException("Email already registered", new Throwable(registerRequest.getEmail()));
        }
        try {
            User user;
            if (foundUser == null) {
                User newUser = userMapper.registerRequestToUser(registerRequest);
                user = userRepository.save(newUser);
            } else {
                user = foundUser;
            }
            TokenEntity userVerifyToken = tokenRepository.findByUser(user);
            if (userVerifyToken == null) {
                userVerifyToken = TokenEntity.builder()
                        .user(user)
                        .build();
            }
            userVerifyToken.setToken(UUID.randomUUID());
            userVerifyToken.setExpiryDate(Instant.now().plus(1, ChronoUnit.DAYS));
            TokenEntity createdToken = tokenRepository.save(userVerifyToken);
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                    createdToken.getToken(),
                    user.getEmail()
            ));
            return userMapper.userToRegisterResponse(user);
        } catch (IllegalArgumentException | OptimisticLockingFailureException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new ServiceErrorException("Entity null", ex);
        }
    }

    @Override
    public RegisterResponse registrationVerify(UUID token) {
        log.trace("user verify email");
        try {
            TokenEntity userVerifyToken = tokenRepository.findByToken(token);
            if (userVerifyToken == null || userVerifyToken.getExpiryDate().isBefore(Instant.now())) {
                throw new RegistrationVerifyErrorException("Token invalid");
            }
            User user = userVerifyToken.getUser();
            if (user == null) {
                throw new RegistrationVerifyErrorException("User not found");
            }
            if (user.isVerified()) {
                throw new RegistrationVerifyErrorException("User already verified");
            }
            user.setVerified(true);
            User updatedUser = userRepository.save(user);
            return userMapper.userToRegisterResponse(updatedUser);
        } catch (IllegalArgumentException | OptimisticLockingFailureException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new ServiceErrorException("Entity null", ex);
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.trace("user login");
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found", new Throwable(loginRequest.getEmail())));
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new ServiceErrorException("Entity null", ex);
        }
        return null;
    }
}
