package com.panonit.restless.services;

import com.panonit.restless.domain.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorDto createAuthor(AuthorDto author);

    List<AuthorDto> getAllAuthors();

    Optional<AuthorDto> getAuthorById(Long id);

    Optional<AuthorDto> updateAuthor(Long id, AuthorDto author);

    Optional<AuthorDto> replaceAuthor(Long id, AuthorDto author);

    void deleteAuthor(Long id);
}
