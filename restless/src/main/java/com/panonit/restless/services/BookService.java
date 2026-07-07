package com.panonit.restless.services;

import com.panonit.restless.domain.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookDto createBook(String isbn, BookDto book);

    List<BookDto> getAllBooks();

    Page<BookDto> getAllBooks(Pageable pageable);

    Optional<BookDto> getBookById(String isbn);

    Optional<BookDto> updateBook(String isbn, BookDto book);

    Optional<BookDto> replaceBook(String isbn, BookDto book);

    void deleteBook(String isbn);
}
