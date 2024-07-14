package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;
import com.bachtx.mangaservice.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IAuthorMapper {
    IAuthorMapper INSTANCE = Mappers.getMapper(IAuthorMapper.class);
    @Mappings({})
    AuthorResponse authorToAuthorResponse(Author author);
    @Mappings({})
    List<AuthorResponse> listAuthorToListAuthorResponse(List<Author> authors);
    @Mappings({})
    Author authorResponseToAuthor(AuthorResponse authorResponse);
    @Mappings({})
    Author updateAuthorPayloadToAuthor(UpdateAuthorPayload updateAuthorResponse);
    @Mappings({})
    void transferUpdateAuthorPayloadDataToAuthor(@MappingTarget Author author, UpdateAuthorPayload updateAuthorPayload);
}
