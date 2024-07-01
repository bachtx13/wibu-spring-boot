package com.bachtx.authservice.mappers;

import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.requests.RegisterResponse;
import com.bachtx.authservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mappings({})
    User registerRequestToUser(RegisterPayload registerRequest);

    @Mappings({})
    RegisterResponse userToRegisterResponse(User user);

//    @Mappings({})
//    UserInfoResponse userToUserInfoResponse(User user);
}
