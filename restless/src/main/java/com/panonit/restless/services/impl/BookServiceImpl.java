package com.panonit.restless.services.impl;

import com.panonit.restless.domain.dto.BookDto;
import com.panonit.restless.domain.entities.BookEntity;
import com.panonit.restless.mappers.Mapper;
import com.panonit.restless.repositories.BookRepository;
import com.panonit.restless.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final Mapper<BookEntity, BookDto> mapper;

    public BookServiceImpl(BookRepository repository, Mapper<BookEntity, BookDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BookDto createBook(String isbn, BookDto book) {
        book.setIsbn(isbn);

        return saveBook(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(mapper::mapToDto).toList();
    }

    @Override
    public Page<BookDto> getAllBooks(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::mapToDto);
    }

    @Override
    public Optional<BookDto> getBookById(String isbn) {
        return repository.findById(isbn).map(mapper::mapToDto);
    }

    @Override
    public Optional<BookDto> updateBook(String isbn, BookDto book) {
        Optional<BookEntity> optional = repository.findById(isbn);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        BookDto local = mapper.mapToDto(optional.get());
        Optional.ofNullable(book.getTitle()).ifPresent(local::setTitle);
        Optional.ofNullable(book.getAuthor()).ifPresent(local::setAuthor);

        return replaceBook(isbn, local);
    }

    @Override
    public Optional<BookDto> replaceBook(String isbn, BookDto book) {
        if (isBookMissing(isbn)) {
            return Optional.empty();
        }

        book.setIsbn(isbn);

        return Optional.of(saveBook(book));
    }

    @Override
    public void deleteBook(String isbn) {
        if (isBookMissing(isbn)) {
            return;
        }

        repository.deleteById(isbn);
    }

    private BookDto saveBook(BookDto book) {
        BookEntity bookEntity = mapper.mapToEntity(book);
        BookEntity savedBookEntity = repository.save(bookEntity);

        return mapper.mapToDto(savedBookEntity);
    }

    private boolean isBookMissing(String isbn) {
        return !repository.existsById(isbn);
    }
}
