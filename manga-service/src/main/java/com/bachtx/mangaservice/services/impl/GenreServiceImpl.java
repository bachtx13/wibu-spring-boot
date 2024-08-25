package com.bachtx.mangaservice.services.impl;

import com.bachtx.mangaservice.dtos.payloads.UpdateGenrePayload;
import com.bachtx.mangaservice.dtos.response.GenreResponse;
import com.bachtx.mangaservice.entities.Genre;
import com.bachtx.mangaservice.mappers.IGenreMapper;
import com.bachtx.mangaservice.repositories.IGenreRepository;
import com.bachtx.mangaservice.services.IGenreService;
import com.bachtx.wibucommon.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements IGenreService {
    private final IGenreRepository genreRepository;
    private final IGenreMapper genreMapper = IGenreMapper.INSTANCE;

    @Override
    public GenreResponse getById(UUID id) {
        Genre foundGenre = genreRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Genre not found"));
        return genreMapper.genreToGenreResponse(foundGenre);

    }

    @Override
    public List<GenreResponse> getAll(Pageable pageable) {
        List<Genre> foundGenres = genreRepository.findAll(pageable).toList();
        return genreMapper.listGenreToListGenreResponse(foundGenres);
    }

    @Override
    public GenreResponse create(UpdateGenrePayload payload) {
            Genre newGenre = genreMapper.updateGenrePayloadToGenre(payload);
            Genre savedGenre = genreRepository.save(newGenre);
            return genreMapper.genreToGenreResponse(savedGenre);
    }

    @Override
    public GenreResponse update(UUID id, UpdateGenrePayload payload) {
        Genre genreToUpdate = genreRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Genre not found"));
        genreMapper.transferUpdateGenrePayloadDataToGenre(genreToUpdate, payload);
        Genre savedGenre = genreRepository.save(genreToUpdate);
        return genreMapper.genreToGenreResponse(savedGenre);
    }

    @Override
    public GenreResponse updateStatus(UUID id, boolean status) {
        Genre genreToUpdate = genreRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Genre not found"));
        genreToUpdate.setDisabled(status);
        Genre savedGenre = genreRepository.save(genreToUpdate);
        return genreMapper.genreToGenreResponse(savedGenre);
    }
}
