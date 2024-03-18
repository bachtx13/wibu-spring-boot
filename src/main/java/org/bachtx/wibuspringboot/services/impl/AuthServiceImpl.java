package org.bachtx.wibuspringboot.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.entities.TokenEntity;
import org.bachtx.wibuspringboot.entities.User;
import org.bachtx.wibuspringboot.exceptions.EmailAlreadyExistsException;
import org.bachtx.wibuspringboot.exceptions.ServiceErrorException;
import org.bachtx.wibuspringboot.mappers.UserMapper;
import org.bachtx.wibuspringboot.repositories.TokenRepository;
import org.bachtx.wibuspringboot.repositories.UserRepository;
import org.bachtx.wibuspringboot.services.AuthService;
import org.bachtx.wibuspringboot.utils.TokenUtil;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final TokenUtil tokenUtil;

    @Override
    public void register(RegisterRequest registerRequest) {
        log.trace("user register");
        User foundUser = userRepository.findByEmail(registerRequest.getEmail()).orElse(null);
        if (foundUser != null && !foundUser.isDisable()) {
            throw new EmailAlreadyExistsException(new Throwable(registerRequest.getEmail()));
        }
        try {
            User user;
            if (foundUser == null) {
                User newUser = userMapper.registerRequestToUser(registerRequest);
                user = userRepository.save(newUser);
            } else {
                user = foundUser;
            }
            String userVerifyTokenStr = tokenUtil.generateToken(user.getEmail());
            TokenEntity userVerifyToken = TokenEntity.builder()
                    .user(user)
                    .token(userVerifyTokenStr)
                    .build();
            tokenRepository.save(userVerifyToken);
        } catch (IllegalArgumentException | OptimisticLockingFailureException ex) {
            log.error(ex.getMessage(), ex.getCause());
            throw new ServiceErrorException("Entity null", new Throwable("register"));
        }
    }
}
