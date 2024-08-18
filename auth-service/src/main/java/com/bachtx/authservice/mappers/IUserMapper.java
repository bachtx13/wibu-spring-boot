package com.bachtx.authservice.mappers;

import com.bachtx.authservice.dtos.payloads.RegisterPayload;
import com.bachtx.authservice.dtos.responses.RegisterResponse;
import com.bachtx.authservice.dtos.responses.UserInfoResponse;
import com.bachtx.authservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mappings({})
    User registerRequestToUser(RegisterPayload registerRequest);

    @Mappings({})
    RegisterResponse userToRegisterResponse(User user);

    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    UserInfoResponse userToUserInfoResponse(User user);
    @Mappings({
            @Mapping(target = "roles", ignore = true)
    })
    List<UserInfoResponse> userListToUserInfoResponseList(List<User> user);
}
