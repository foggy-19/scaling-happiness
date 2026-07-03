package com.panonit.postgres.dao;

import com.panonit.postgres.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void create(Author author);

    void update(long id, Author author);

    Optional<Author> get(long id);

    List<Author> getAll();

    void delete(long id);
}
