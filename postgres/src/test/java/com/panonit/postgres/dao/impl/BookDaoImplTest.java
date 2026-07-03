package com.panonit.postgres.dao.impl;

import com.panonit.postgres.TestDataUtil;
import com.panonit.postgres.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateGeneratesCorrectSql() {
        Book book = TestDataUtil.getTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq(book.getIsbn()),
                eq(book.getTitle()),
                eq(book.getAuthorId())
        );
    }

    @Test
    public void testThatGetGeneratesCorrectSql() {
        underTest.get("isbn");

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                any(BookDaoImpl.BookMapper.class),
                eq("isbn")
        );
    }

    @Test
    public void testThatGetAllGeneratesCorrectSql() {
        underTest.getAll();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                any(BookDaoImpl.BookMapper.class)
        );

    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        String isbn = "isbn";
        Book book = TestDataUtil.getTestBookA();
        underTest.update(isbn, book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq(book.getIsbn()),
                eq(book.getTitle()),
                eq(book.getAuthorId()),
                eq(isbn)
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        String isbn = "isbn";
        underTest.delete(isbn);

        verify(jdbcTemplate).update(
                eq("DELETE FROM books WHERE isbn = ?"),
                eq(isbn)
        );
    }
}