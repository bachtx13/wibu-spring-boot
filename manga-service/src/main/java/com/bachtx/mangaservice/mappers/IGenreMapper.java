package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;
import com.bachtx.mangaservice.entities.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IGenreMapper {
    IGenreMapper INSTANCE = Mappers.getMapper(IGenreMapper.class);
    @Mappings({})
    GenreResponse genreToGenreResponse(Genre genre);
    @Mappings({})
    List<GenreResponse> listGenreToListGenreResponse(List<Genre> genres);
    @Mappings({})
    Genre genreResponseToGenre(GenreResponse genreResponse);
    @Mappings({})
    Genre updateGenrePayloadToGenre(UpdateGenrePayload updateGenreResponse);
    @Mappings({})
    void transferUpdateGenrePayloadDataToGenre(@MappingTarget Genre genre, UpdateGenrePayload updateGenrePayload);
}
