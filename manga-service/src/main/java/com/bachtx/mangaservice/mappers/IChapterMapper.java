package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.mangaservice.entities.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IChapterMapper {
    IChapterMapper INSTANCE = Mappers.getMapper(IChapterMapper.class);

    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    ChapterResponse chapterToChapterResponse(Chapter chapter);

    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    List<ChapterResponse> listChapterToListChapterResponse(List<Chapter> chapters);

    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    Chapter chapterResponseToChapter(ChapterResponse chapterResponse);

    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    Chapter createChapterPayloadToChapter(CreateChapterPayload createChapterPayload);

    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    void transferCreateChapterPayloadDataToChapter(@MappingTarget Chapter chapter, CreateChapterPayload createChapterPayload);


    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    Chapter updateChapterPayloadToChapter(UpdateChapterPayload updateChapterResponse);

    @Mappings({
            @Mapping(target = "manga", ignore = true),
            @Mapping(target = "pages", ignore = true),
    })
    void transferUpdateChapterPayloadDataToChapter(@MappingTarget Chapter chapter, UpdateChapterPayload updateChapterPayload);
}
