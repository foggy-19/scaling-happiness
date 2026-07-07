package com.panonit.restless.repositories;

import com.panonit.restless.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String>, PagingAndSortingRepository<BookEntity, String> {

    @Override
    Iterable<BookEntity> findAll(Sort sort);

    @Override
    Page<BookEntity> findAll(Pageable pageable);
}
