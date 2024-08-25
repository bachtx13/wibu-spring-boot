package com.bachtx.mangaservice.services.impl;

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
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        newChapter.setViews(1L);
        newChapter = chapterRepository.save(newChapter);
        return chapterMapper.chapterToChapterResponse(newChapter);
    }

    @Override
    public ChapterResponse update(UUID id, UpdateChapterPayload payload) {
        return null;
    }

    @Override
    public ChapterResponse updateStatus(UUID id, boolean status) {
        return null;
    }

    @Override
    public ChapterResponse getById(UUID id) {
        return null;
    }

    @Override
    public List<ChapterResponse> getAll(Pageable pageable) {
        return List.of();
    }
}
