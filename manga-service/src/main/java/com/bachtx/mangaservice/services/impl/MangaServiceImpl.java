package com.bachtx.mangaservice.services.impl;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.raws.MangaPreviewRaw;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.entities.Author;
import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.mangaservice.entities.Manga;
import com.bachtx.mangaservice.entities.User;
import com.bachtx.mangaservice.mappers.IAuthorMapper;
import com.bachtx.mangaservice.mappers.IGenreMapper;
import com.bachtx.mangaservice.mappers.IMangaMapper;
import com.bachtx.mangaservice.mappers.IUserMapper;
import com.bachtx.mangaservice.repositories.*;
import com.bachtx.mangaservice.services.IMangaService;
import com.bachtx.wibucommon.exceptions.AccessDeniedException;
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import com.bachtx.wibucommon.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MangaServiceImpl implements IMangaService {
    private final IMangaRepository mangaRepository;
    private final IAuthorRepository authorRepository;
    private final IGenreRepository genreRepository;
    private final IUserRepository userRepository;
    private final IChapterRepository chapterRepository;
    private final JwtUtil jwtUtil;
    private final IMangaMapper mangaMapper = IMangaMapper.INSTANCE;
    private final IAuthorMapper authorMapper = IAuthorMapper.INSTANCE;
    private final IGenreMapper genreMapper = IGenreMapper.INSTANCE;
    private final IUserMapper userMapper = IUserMapper.INSTANCE;

    @Override
    public MangaResponse getById(UUID id) {
        Manga foundManga = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        return mangaMapper.mangaToMangaResponse(foundManga);
    }

    @Override
    public List<MangaResponse> getAll(Pageable pageable) {
        List<MangaPreviewRaw> mangaPreviewRaws = mangaRepository.findAllPreviewManga(pageable);
        return mangaPreviewRaws.stream().map(mangaPreviewRaw -> {
            MangaResponse mangaResponse = mangaMapper.mangaToMangaResponse(mangaPreviewRaw.getManga());
            mangaResponse.setViews(mangaPreviewRaw.getTotalViews());
            mangaResponse.setChapters(null);
            return mangaResponse;
        }).toList();
    }

    @Override
    public MangaResponse create(UpdateMangaPayload payload) {
        return null;
    }

    @Override
    public MangaResponse update(UUID id, UpdateMangaPayload payload) {
        Manga mangaToUpdate = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        mangaMapper.transferUpdateMangaPayloadDataToManga(mangaToUpdate, payload);
        _updateMangaRelationData(mangaToUpdate, payload);
        Manga savedManga = mangaRepository.save(mangaToUpdate);
        return mangaMapper.mangaToMangaResponse(savedManga);
    }

    @Override
    public MangaResponse updateStatus(UUID id, boolean status) {
        Manga mangaToUpdate = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        mangaToUpdate.setDisabled(status);
        Manga savedManga = mangaRepository.save(mangaToUpdate);
        return mangaMapper.mangaToMangaResponse(savedManga);
    }

    private void _updateMangaRelationData(Manga manga, UpdateMangaPayload payload) {
        List<Author> authors = authorRepository.findAllByIdIn(payload.getAuthorIds());
        List<Genre> genres = genreRepository.findAllByIdIn(payload.getGenreIds());
        if (genres.isEmpty()) {
            throw new RecordNotFoundException("Please choose at least one available genre");
        }
        if (authors.isEmpty()) {
            throw new RecordNotFoundException("Please choose at least one available author");
        }
        manga.setAuthors(authors);
        manga.setGenres(genres);
    }

    @Override
    public MangaResponse create(UpdateMangaPayload payload, String token) {
        String publisherEmail = jwtUtil.getSubjectFromToken(token);
        User foundUser = userRepository.findByEmail(publisherEmail);
        if (foundUser == null) {
            throw new AccessDeniedException("User not found");
        }
        Manga newManga = mangaMapper.updateMangaPayloadToManga(payload);
        _updateMangaRelationData(newManga, payload);
        newManga.setPublisher(foundUser);
        Manga savedManga = mangaRepository.save(newManga);
        return mangaMapper.mangaToMangaResponse(savedManga);
    }
}
