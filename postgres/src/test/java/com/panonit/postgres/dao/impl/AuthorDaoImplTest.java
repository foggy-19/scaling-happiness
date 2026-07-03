package com.panonit.postgres.dao.impl;

import com.panonit.postgres.TestDataUtil;
import com.panonit.postgres.domain.Author;
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
class AuthorDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateGeneratesCorrectSql() {
        Author author = TestDataUtil.getTestAuthorA();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(author.getId()),
                eq(author.getName()),
                eq(author.getAge())
        );
    }

    @Test
    public void testThatGetGeneratesCorrectSql() {
        underTest.get(1L);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                any(AuthorDaoImpl.AuthorMapper.class),
                eq(1L));
    }

    @Test
    public void testThatGetAllGeneratesCorrectSql() {
        underTest.getAll();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                any(AuthorDaoImpl.AuthorMapper.class)
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        long id = 3L;
        Author author = TestDataUtil.getTestAuthorA();
        underTest.update(id, author);

        verify(jdbcTemplate).update(
                eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?"),
                eq(author.getId()),
                eq(author.getName()),
                eq(author.getAge()),
                eq(id)
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        long id = 3L;
        underTest.delete(id);

        verify(jdbcTemplate).update(
                eq("DELETE FROM authors WHERE id = ?"),
                eq(id)
        );
    }
}