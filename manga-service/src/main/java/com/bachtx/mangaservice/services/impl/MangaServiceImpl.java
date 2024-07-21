package com.bachtx.mangaservice.services.impl;

import com.bachtx.mangaservice.dtos.payloads.UpdateMangaPayload;
import com.bachtx.mangaservice.dtos.response.MangaResponse;
import com.bachtx.mangaservice.entities.Author;
import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.mangaservice.entities.Manga;
import com.bachtx.mangaservice.mappers.IAuthorMapper;
import com.bachtx.mangaservice.mappers.IGenreMapper;
import com.bachtx.mangaservice.mappers.IMangaMapper;
import com.bachtx.mangaservice.mappers.IUserMapper;
import com.bachtx.mangaservice.repositories.IAuthorRepository;
import com.bachtx.mangaservice.repositories.IGenreRepository;
import com.bachtx.mangaservice.repositories.IMangaRepository;
import com.bachtx.mangaservice.services.IMangaService;
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
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
    private final IAuthorMapper authorMapper = IAuthorMapper.INSTANCE;
    private final IGenreMapper genreMapper = IGenreMapper.INSTANCE;
    private final IUserMapper userMapper = IUserMapper.INSTANCE;

    @Override
    public MangaResponse getById(UUID id) {
        Manga foundManga = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        MangaResponse mangaResponse = mangaMapper.mangaToMangaResponse(foundManga);
        mangaResponse.setAuthors(authorMapper.listAuthorToListAuthorResponse(foundManga.getAuthors()));
        mangaResponse.setGenres(genreMapper.listGenreToListGenreResponse(foundManga.getGenres()));
        mangaResponse.setPublisher(userMapper.userToUserInfoResponse(foundManga.getPublisher()));
        return mangaResponse;
    }

    @Override
    public List<MangaResponse> getAll() {
        List<Manga> foundMangas = mangaRepository.findAll();
        return mangaMapper.listMangaToListMangaResponse(foundMangas);
    }

    @Override
    public MangaResponse create(UpdateMangaPayload payload) {
        Manga newManga = mangaMapper.updateMangaPayloadToManga(payload);
        _updateMangaRelationData(newManga, payload);
        Manga savedManga = mangaRepository.save(newManga);
        MangaResponse mangaResponse = mangaMapper.mangaToMangaResponse(savedManga);
        mangaResponse.setAuthors(authorMapper.listAuthorToListAuthorResponse(newManga.getAuthors()));
        mangaResponse.setGenres(genreMapper.listGenreToListGenreResponse(newManga.getGenres()));
        return mangaResponse;
    }

    @Override
    public MangaResponse update(UUID id, UpdateMangaPayload payload) {
        Manga mangaToUpdate = mangaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Manga not found"));
        mangaMapper.transferUpdateMangaPayloadDataToManga(mangaToUpdate, payload);
        _updateMangaRelationData(mangaToUpdate, payload);
        Manga savedManga = mangaRepository.save(mangaToUpdate);
        MangaResponse mangaResponse = mangaMapper.mangaToMangaResponse(savedManga);
        mangaResponse.setAuthors(authorMapper.listAuthorToListAuthorResponse(mangaToUpdate.getAuthors()));
        mangaResponse.setGenres(genreMapper.listGenreToListGenreResponse(mangaToUpdate.getGenres()));
        return mangaResponse;
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
}
