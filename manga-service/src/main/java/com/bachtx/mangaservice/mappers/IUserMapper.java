package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.response.UserInfoResponse;
import com.bachtx.mangaservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "mangas", ignore = true),
    })
    UserInfoResponse userToUserInfoResponse(User user);
}
