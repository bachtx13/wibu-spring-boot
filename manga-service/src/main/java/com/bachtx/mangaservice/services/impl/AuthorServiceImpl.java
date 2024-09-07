package com.bachtx.mangaservice.services.impl;

import com.bachtx.mangaservice.contexts.AuthenticationContextHolder;
import com.bachtx.mangaservice.contexts.models.AuthenticationContext;
import com.bachtx.mangaservice.dtos.payloads.UpdateAuthorPayload;
import com.bachtx.mangaservice.dtos.response.AuthorResponse;
import com.bachtx.mangaservice.entities.Author;
import com.bachtx.mangaservice.mappers.IAuthorMapper;
import com.bachtx.mangaservice.repositories.IAuthorRepository;
import com.bachtx.mangaservice.services.IAuthorService;
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
public class AuthorServiceImpl implements IAuthorService {
    private final IAuthorRepository authorRepository;
    private final IAuthorMapper authorMapper = IAuthorMapper.INSTANCE;

    @Override
    public AuthorResponse getById(UUID id) {
        Author foundAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Author not found"));
        return authorMapper.authorToAuthorResponse(foundAuthor);
    }

    @Override
    public List<AuthorResponse> getAll(Pageable pageable, Specification<Author> specification) {
        List<Author> foundAuthors = authorRepository.findAll(specification, pageable).toList();
        return authorMapper.listAuthorToListAuthorResponse(foundAuthors);
    }

    @Override
    public Long getNumberOfRecords(ERecordStatus status) {
        return 0L;
    }

    @Override
    public AuthorResponse create(UpdateAuthorPayload payload) {
            Author newAuthor = authorMapper.updateAuthorPayloadToAuthor(payload);
            Author savedAuthor = authorRepository.save(newAuthor);
            return authorMapper.authorToAuthorResponse(savedAuthor);
    }

    @Override
    public AuthorResponse update(UUID id, UpdateAuthorPayload payload) {
        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Author not found"));
        authorMapper.transferUpdateAuthorPayloadDataToAuthor(authorToUpdate, payload);
        Author savedAuthor = authorRepository.save(authorToUpdate);
        return authorMapper.authorToAuthorResponse(savedAuthor);
    }

    @Override
    public AuthorResponse updateStatus(UUID id, boolean isDeActive) {
        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Author not found"));
        authorToUpdate.setDisabled(isDeActive);
        Author savedAuthor = authorRepository.save(authorToUpdate);
        return authorMapper.authorToAuthorResponse(savedAuthor);
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
