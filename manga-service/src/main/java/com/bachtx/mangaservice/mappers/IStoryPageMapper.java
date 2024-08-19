package com.bachtx.mangaservice.mappers;

import com.bachtx.mangaservice.dtos.payloads.CreateStoryPagePayload;
import com.bachtx.mangaservice.dtos.response.StoryPageResponse;
import com.bachtx.mangaservice.entities.StoryPage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IStoryPageMapper {
    IStoryPageMapper INSTANCE = Mappers.getMapper(IStoryPageMapper.class);

    @Mappings({})
    StoryPageResponse storyPageToStoryPageResponse(StoryPage storyPage);

    @Mappings({})
    List<StoryPageResponse> listStoryPageToListStoryPageResponse(List<StoryPage> storyPages);

    @Mappings({})
    StoryPage storyPageResponseToStoryPage(StoryPageResponse storyPageResponse);

    @Mappings({})
    StoryPage createStoryPagePayloadToStoryPage(CreateStoryPagePayload createStoryPagePayload);

    @Mappings({})
    List<StoryPage> listCreateStoryPagePayloadToListStoryPage(List<CreateStoryPagePayload> createStoryPagePayload);

    @Mappings({})
    void transferCreateStoryPagePayloadDataToStoryPage(@MappingTarget StoryPage storyPage, CreateStoryPagePayload createStoryPagePayload);
}
