package com.panonit.postgres.dao;

import com.panonit.postgres.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void create(Book book);

    void update(String isbn, Book book);

    Optional<Book> get(String isbn);

    List<Book> getAll();

    void delete(String isbn);
}
