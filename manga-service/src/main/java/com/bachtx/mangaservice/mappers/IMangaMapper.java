package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.entities.Manga;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        uses = {
                IAuthorMapper.class,
                IChapterMapper.class,
                IGenreMapper.class,
                IUserMapper.class
        }
)
public interface IMangaMapper {
    IMangaMapper INSTANCE = Mappers.getMapper(IMangaMapper.class);

    @Mappings({
            @Mapping(target = "chapters", qualifiedByName = "chapterToChapterResponseWithoutPages")
    })
    MangaResponse mangaToMangaResponse(Manga manga);

    @Mappings({
            @Mapping(target = "chapters", ignore = true)
    })
    @Named("mangaToMangaPreviewResponse")
    MangaResponse mangaToMangaPreviewResponse(Manga manga);

    @Mappings({})
    @IterableMapping(qualifiedByName = "mangaToMangaPreviewResponse")
    List<MangaResponse> listMangaToListMangaPreviewResponse(List<Manga> mangas);

    @Mappings({})
    Manga mangaResponseToManga(MangaResponse mangaResponse);

    @Mappings({})
    Manga updateMangaPayloadToManga(UpdateMangaPayload updateMangaResponse);

    @Mappings({
            @Mapping(target = "publisher", ignore = true),
            @Mapping(target = "authors", ignore = true),
            @Mapping(target = "chapters", ignore = true),
            @Mapping(target = "genres", ignore = true),
    })
    void transferUpdateMangaPayloadDataToManga(@MappingTarget Manga manga, UpdateMangaPayload updateMangaPayload);
}
