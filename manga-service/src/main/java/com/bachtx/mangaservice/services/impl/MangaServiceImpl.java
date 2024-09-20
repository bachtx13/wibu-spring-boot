package com.bachtx.mangaservice.services.impl;

import com.bachtx.mangaservice.contexts.AuthenticationContextHolder;
import com.bachtx.mangaservice.contexts.models.AuthenticationContext;
import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.entities.Author;
import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.mangaservice.entities.Manga;
import com.bachtx.mangaservice.entities.User;
import com.bachtx.mangaservice.mappers.IMangaMapper;
import com.bachtx.mangaservice.repositories.IAuthorRepository;
import com.bachtx.mangaservice.repositories.IGenreRepository;
import com.bachtx.mangaservice.repositories.IMangaRepository;
import com.bachtx.mangaservice.services.IMangaService;
import com.bachtx.wibucommon.enums.ERecordStatus;
import com.bachtx.wibucommon.enums.EUserRole;
import com.bachtx.wibucommon.exceptions.AccessDeniedException;
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MangaServiceImpl implements IMangaService {
    private final IMangaRepository mangaRepository;
    private final IAuthorRepository authorRepository;
    private final IGenreRepository genreRepository;
    private final IMangaMapper mangaMapper = IMangaMapper.INSTANCE;

    @Override
    public MangaResponse getById(UUID id) {
        Manga foundManga = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        return mangaMapper.mangaToMangaResponse(foundManga);
    }

    @Override
    public List<MangaResponse> getAll(Pageable pageable, Specification<Manga> specification) {
        List<Manga> mangas = mangaRepository.findAll(specification, pageable).toList();
        return mangaMapper.listMangaToListMangaPreviewResponse(mangas);
    }

    @Override
    public MangaResponse create(UpdateMangaPayload payload) {
        AuthenticationContext authenticationContext = AuthenticationContextHolder.getContext();
        if (!authenticationContext.isAuthenticated()) {
            throw new AccessDeniedException(authenticationContext.getCause());
        }
        if (!authenticationContext.hasRole(EUserRole.ROLE_ADMIN)) {
            throw new AccessDeniedException("User hasn\\'t permission");
        }
        User foundUser = AuthenticationContextHolder.getContext().getPrincipal();
        Manga newManga = mangaMapper.updateMangaPayloadToManga(payload);
        _updateMangaRelationData(newManga, payload);
        newManga.setPublisher(foundUser);
        Manga savedManga = mangaRepository.save(newManga);
        return mangaMapper.mangaToMangaResponse(savedManga);
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
    public MangaResponse updateStatus(UUID id, boolean isDeActive) {
        Manga mangaToUpdate = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        mangaToUpdate.setDisabled(isDeActive);
        Manga savedManga = mangaRepository.save(mangaToUpdate);
        return mangaMapper.mangaToMangaResponse(savedManga);
    }

    @Override
    public Long getNumberOfRecords(ERecordStatus status) {
        status = calculateRecordStatusByRole(status);
        if(status != ERecordStatus.IGNORE_STATUS){
            return mangaRepository.countByDisabled(status == ERecordStatus.DISABLED);
        }
        return mangaRepository.count();
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
    public ERecordStatus calculateRecordStatusByRole(ERecordStatus status){
        AuthenticationContext authenticationContext = AuthenticationContextHolder.getContext();
        if(status != ERecordStatus.ENABLED && authenticationContext.hasRole(EUserRole.ROLE_ADMIN)){
            status = ERecordStatus.ENABLED;
        }
        return status;
    }
}
