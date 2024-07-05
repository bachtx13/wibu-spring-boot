package com.bachtx.authservice.services.impl;

import com.bachtx.authservice.asyncs.events.OnRegistrationCompleteEvent;
import com.bachtx.authservice.dtos.payloads.LoginPayload;
import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.responses.LoginResponse;
import com.bachtx.authservice.dtos.responses.RegisterResponse;
import com.bachtx.authservice.dtos.responses.UserInfoResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final JwtUtil jwtUtil;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Value("${admin-account.email: admin@gmail.com}")
    private String adminEmail;
    @Value("${admin-account.username: admin}")
    private String adminUsername;
    @Value("${admin-account.password: 123}")
    private String adminPassword;

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
        String token = jwtUtil.generateToken(user.getEmail());
        return LoginResponse.builder()
                .token(token)
                .build();
    }


    @Override
    public RegisterResponse register(RegisterPayload registerPayload) {
        log.debug("user register");
        User foundUser = userRepository.findByEmail(registerPayload.getEmail());
        if (registerPayload.getEmail().equals(adminEmail) || (foundUser != null && foundUser.isVerified())) {
            throw new EmailAlreadyExistsException("Email already registered", new Throwable(registerPayload.getEmail()));
        }
        try {
            User user;
            if (foundUser == null) {
                user = userMapper.registerRequestToUser(registerPayload);
                user.setPassword(passwordEncoder.encode(registerPayload.getPassword()));
                user.setTokenHolder(TokenHolder.builder()
                        .user(user)
                        .build());
            } else {
                user = foundUser;
                user.setPassword(passwordEncoder.encode(registerPayload.getPassword()));
            }
            user.getTokenHolder().setToken(UUID.randomUUID());
            user.getTokenHolder().setExpiryDate(Instant.now().plus(1, ChronoUnit.DAYS));

            userRepository.save(user);
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                    user.getTokenHolder().getToken(),
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
                _deleteToken(userVerifyToken);
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
            _deleteToken(userVerifyToken);
            return userMapper.userToRegisterResponse(updatedUser);
        } catch (IllegalArgumentException | OptimisticLockingFailureException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new ServiceErrorException("Entity null", ex);
        }
    }

    @Override
    public void initAdminUser() {
        User foundAdminUser = userRepository.findByEmail(adminEmail);
        if (foundAdminUser != null) {
            log.info("Admin user already exists");
            return;
        }
        Role roleAdmin = roleRepository.findByName(EUserRole.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(roleAdmin);
        User newAdminUser = User.builder()
                .email(adminEmail)
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles(roles)
                .verified(true)
                .build();
        userRepository.save(newAdminUser);
    }

    @Override
    public UserInfoResponse getUserInfo(String token) {
        String email = jwtUtil.getSubjectFromToken(token);
        User user = userRepository.findByEmail(email);
        if(user == null){
            return UserInfoResponse.builder().build();
        }
        UserInfoResponse userInfoResponse = userMapper.userToUserInfoResponse(user);
        userInfoResponse.setRoles(user.getRoles().stream()
                .map(Role::getName).toList());
        return userInfoResponse;
    }

    private void _deleteToken(TokenHolder tokenHolder) {
        executor.submit(() -> {
            tokenHolderRepository.delete(tokenHolder);
        });
    }
}
