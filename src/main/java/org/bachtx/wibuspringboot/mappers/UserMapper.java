package org.bachtx.wibuspringboot.mappers;

import org.bachtx.wibuspringboot.dtos.request.RegisterRequest;
import org.bachtx.wibuspringboot.dtos.response.RegisterResponse;
import org.bachtx.wibuspringboot.dtos.response.UserInfoResponse;
import org.bachtx.wibuspringboot.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({})
    User registerRequestToUser(RegisterRequest registerRequest);

    @Mappings({})
    RegisterResponse userToRegisterResponse(User user);

    @Mappings({})
    UserInfoResponse userToUserInfoResponse(User user);
}
