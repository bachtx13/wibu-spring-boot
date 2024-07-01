package com.bachtx.authservice.services.impl;

import com.bachtx.authservice.asyncs.events.OnRegistrationCompleteEvent;
import com.bachtx.authservice.dtos.payloads.LoginPayload;
import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.requests.LoginResponse;
import com.bachtx.authservice.dtos.requests.RegisterResponse;
import com.bachtx.authservice.entities.Role;
import com.bachtx.authservice.entities.TokenHolder;
import com.bachtx.authservice.entities.User;
import com.bachtx.authservice.enums.EUserRole;
import com.bachtx.authservice.exceptions.*;
import com.bachtx.authservice.mappers.IUserMapper;
import com.bachtx.authservice.repositories.IRoleRepository;
import com.bachtx.authservice.repositories.ITokenHolderRepository;
import com.bachtx.authservice.repositories.IUserRepository;
import com.bachtx.authservice.services.IUserService;
import com.bachtx.wibucommon.exceptions.ServiceErrorException;
import com.bachtx.wibucommon.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ITokenHolderRepository tokenHolderRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserMapper userMapper = IUserMapper.INSTANCE;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Value("${security-config.token.hash-secret}")
    private String hashSecret;
    @Value("${security-config.token.validity}")
    private Long validity;

    @Override
    public LoginResponse login(LoginPayload loginPayload) {
        User user = this.userRepository.findByEmail(loginPayload.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User email not found");
        }
        if (!user.isVerified()) {
            throw new UnverifiedUserException("User is not verified");
        }
        boolean isPasswordMatched = passwordEncoder.matches(loginPayload.getPassword(), user.getPassword());
        if (!isPasswordMatched) {
            throw new PasswordNotMatchException("Password not match");
        }
        JwtUtil jwtUtil = new JwtUtil(hashSecret, validity);
        String token = jwtUtil.generateToken(user.getEmail());
        return LoginResponse.builder()
                .token(token)
                .build();
    }


    @Override
    public RegisterResponse register(RegisterPayload registerPayload) {
        log.debug("user register");
        User foundUser = userRepository.findByEmail(registerPayload.getEmail());
        if (foundUser != null && foundUser.isVerified()) {
            throw new EmailAlreadyExistsException("Email already registered", new Throwable(registerPayload.getEmail()));
        }
        try {
            User user;
            if (foundUser == null) {
                User newUser = userMapper.registerRequestToUser(registerPayload);
                user = userRepository.save(newUser);
            } else {
                user = foundUser;
            }
            user.setPassword(passwordEncoder.encode(registerPayload.getPassword()));
            TokenHolder userVerifyToken = tokenHolderRepository.findByUser(user);
            if (userVerifyToken == null) {
                userVerifyToken = TokenHolder.builder()
                        .user(user)
                        .build();
            }
            userVerifyToken.setToken(UUID.randomUUID());
            userVerifyToken.setExpiryDate(Instant.now().plus(1, ChronoUnit.DAYS));
            TokenHolder createdToken = tokenHolderRepository.save(userVerifyToken);
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
        log.debug("user verify email");
        try {
            TokenHolder userVerifyToken = tokenHolderRepository.findByToken(token);
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
            Role role = roleRepository.findByName(EUserRole.ROLE_USER);
            if (role == null) {
                throw new RoleNotFoundException("Role " + EUserRole.ROLE_USER + " not found");
            }
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            User updatedUser = userRepository.save(user);
            return userMapper.userToRegisterResponse(updatedUser);
        } catch (IllegalArgumentException | OptimisticLockingFailureException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new ServiceErrorException("Entity null", ex);
        }
    }

    @Async
    protected void deleteToken(TokenHolder tokenHolder) {
        tokenHolderRepository.delete(tokenHolder);
    }
}
