package org.bachtx.wibuspringboot.services.impl;

import lombok.AllArgsConstructor;
import org.bachtx.wibuspringboot.dtos.response.UserInfoResponse;
import org.bachtx.wibuspringboot.entities.User;
import org.bachtx.wibuspringboot.entities.UserPrincipal;
import org.bachtx.wibuspringboot.exceptions.UserNotLoggedYetException;
import org.bachtx.wibuspringboot.mappers.UserMapper;
import org.bachtx.wibuspringboot.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public UserInfoResponse getUserInfo() {
        User user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (user == null) {
            throw new UserNotLoggedYetException("User not logged yet");
        }
        return userMapper.userToUserInfoResponse(user);
    }
}
