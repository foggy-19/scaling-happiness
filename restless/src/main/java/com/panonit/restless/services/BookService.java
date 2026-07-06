package com.panonit.restless.services;

import com.panonit.restless.domain.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto createBook(String isbn, BookDto book);

    List<BookDto> getAllBooks();

    Optional<BookDto> getBookById(String isbn);

    Optional<BookDto> updateBook(String isbn, BookDto book);

    Optional<BookDto> replaceBook(String isbn, BookDto book);

    void deleteBook(String isbn);
}
