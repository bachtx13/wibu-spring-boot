package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.mangaservice.entities.Chapter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        IStoryPageMapper.class
})
public interface IChapterMapper {
    IChapterMapper INSTANCE = Mappers.getMapper(IChapterMapper.class);

    @Mappings({})
    ChapterResponse chapterToChapterResponse(Chapter chapter);

    @Mappings({
            @Mapping(target = "pages", ignore = true)
    })
    @Named("chapterToChapterResponseWithoutPages")
    ChapterResponse chapterToChapterResponseWithoutPages(Chapter chapter);

    @Mappings({})
    List<ChapterResponse> listChapterToListChapterResponse(List<Chapter> chapters);

    @Mappings({})
    Chapter chapterResponseToChapter(ChapterResponse chapterResponse);

    @Mappings({})
    Chapter createChapterPayloadToChapter(CreateChapterPayload createChapterPayload);

    @Mappings({})
    void transferCreateChapterPayloadDataToChapter(@MappingTarget Chapter chapter, CreateChapterPayload createChapterPayload);


    @Mappings({})
    Chapter updateChapterPayloadToChapter(UpdateChapterPayload updateChapterResponse);

    @Mappings({})
    void transferUpdateChapterPayloadDataToChapter(@MappingTarget Chapter chapter, UpdateChapterPayload updateChapterPayload);
}
