package com.bachtx.mangaservice.services.impl;

import com.bachtx.mangaservice.contexts.AuthenticationContextHolder;
import com.bachtx.mangaservice.contexts.models.AuthenticationContext;
import com.bachtx.mangaservice.dtos.payloads.CreateChapterPayload;
import com.bachtx.mangaservice.dtos.payloads.UpdateChapterPayload;
import com.bachtx.mangaservice.dtos.response.ChapterResponse;
import com.bachtx.mangaservice.entities.Chapter;
import com.bachtx.mangaservice.entities.Manga;
import com.bachtx.mangaservice.entities.StoryPage;
import com.bachtx.mangaservice.mappers.IChapterMapper;
import com.bachtx.mangaservice.mappers.IStoryPageMapper;
import com.bachtx.mangaservice.repositories.IChapterRepository;
import com.bachtx.mangaservice.repositories.IMangaRepository;
import com.bachtx.mangaservice.repositories.IStoryPageRepository;
import com.bachtx.mangaservice.services.IChapterService;
import com.bachtx.mangaservice.specifications.BaseFilterSpecification;
import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.EUserRole;
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChapterService implements IChapterService {
    private final IMangaRepository mangaRepository;
    private final IChapterRepository chapterRepository;
    private final IStoryPageRepository storyPageRepository;
    private final IChapterMapper chapterMapper = IChapterMapper.INSTANCE;
    private final IStoryPageMapper storyPageMapper = IStoryPageMapper.INSTANCE;

    @Override
    public ChapterResponse create(CreateChapterPayload payload) {
        Manga foundManga = mangaRepository.findById(payload.getMangaId())
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        Chapter newChapter = Chapter.builder()
                .manga(foundManga)
                .build();
        chapterMapper.transferCreateChapterPayloadDataToChapter(newChapter, payload);
        List<StoryPage> pages = storyPageMapper.listCreateStoryPagePayloadToListStoryPage(payload.getPages());
        newChapter.setPages(pages);
        newChapter.setViews(0L);
        newChapter = chapterRepository.save(newChapter);
        return chapterMapper.chapterToChapterResponse(newChapter);
    }

    @Override
    public ChapterResponse update(UUID id, UpdateChapterPayload payload) {
        Chapter foundChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Chapter not found"));
        chapterMapper.transferUpdateChapterPayloadDataToChapter(foundChapter, payload);
        List<StoryPage> pages = storyPageMapper.listCreateStoryPagePayloadToListStoryPage(payload.getPages());
        foundChapter.setPages(pages);
        Chapter updatedChapter = chapterRepository.save(foundChapter);
        return chapterMapper.chapterToChapterResponse(updatedChapter);
    }

    @Override
    public ChapterResponse updateStatus(UUID id, boolean isDisable) {
        Chapter chapterToUpdate = chapterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Chapter not found"));
        chapterToUpdate.setDisabled(isDisable);
        Chapter savedChapter = chapterRepository.save(chapterToUpdate);
        return chapterMapper.chapterToChapterResponse(savedChapter);
    }

    @Override
    public ChapterResponse getById(UUID id) {
        Chapter foundChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Chapter not found"));
        return chapterMapper.chapterToChapterResponse(foundChapter);
    }

    @Override
    public List<ChapterResponse> getAll(Pageable pageable, Specification<Chapter> specification) {
        return List.of();
    }

    @Override
    public BaseFilterSpecification<Chapter> createFilterSpecification() {
        return null;
    }

    @Override
    public Long getNumberOfRecords(ERecordStatus status) {
        return 0L;
    }

    @Override
    public ERecordStatus calculateRecordStatusByRole(ERecordStatus status) {
        AuthenticationContext authenticationContext = AuthenticationContextHolder.getContext();
        if (status != ERecordStatus.ENABLED && authenticationContext.hasRole(EUserRole.ROLE_ADMIN)) {
            status = ERecordStatus.ENABLED;
        }
        return status;
    }
}
