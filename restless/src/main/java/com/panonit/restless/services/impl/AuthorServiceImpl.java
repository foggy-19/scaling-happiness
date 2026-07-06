package com.panonit.restless.services.impl;

import com.panonit.restless.domain.dto.AuthorDto;
import com.panonit.restless.domain.entities.AuthorEntity;
import com.panonit.restless.mappers.Mapper;
import com.panonit.restless.repositories.AuthorRepository;
import com.panonit.restless.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final Mapper<AuthorEntity, AuthorDto> mapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, Mapper<AuthorEntity, AuthorDto> mapper) {
        this.repository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto author) {
        return saveAuthor(author);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(mapper::mapToDto).toList();
    }

    @Override
    public Optional<AuthorDto> getAuthorById(Long id) {
        return repository.findById(id).map(mapper::mapToDto);
    }

    @Override
    public Optional<AuthorDto> updateAuthor(Long id, AuthorDto author) {
        Optional<AuthorEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        AuthorDto local = mapper.mapToDto(optional.get());
        Optional.ofNullable(author.getName()).ifPresent(local::setName);
        Optional.ofNullable(author.getAge()).ifPresent(local::setAge);

        return replaceAuthor(id, local);
    }

    @Override
    public Optional<AuthorDto> replaceAuthor(Long id, AuthorDto author) {
        if (isAuthorMissing(id)) {
            return Optional.empty();
        }

        author.setId(id);

        return Optional.of(saveAuthor(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        if (isAuthorMissing(id)) {
            return;
        }

        repository.deleteById(id);
    }

    private AuthorDto saveAuthor(AuthorDto author) {
        AuthorEntity authorEntity = mapper.mapToEntity(author);
        AuthorEntity savedAuthorEntity = repository.save(authorEntity);

        return mapper.mapToDto(savedAuthorEntity);
    }

    private boolean isAuthorMissing(Long id) {
        return !repository.existsById(id);
    }
}
