package com.panonit.postgres.dao.impl;

import com.panonit.postgres.dao.AuthorDao;
import com.panonit.postgres.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        String sql = "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, author.getId(), author.getName(), author.getAge());
    }

    @Override
    public void update(long id, Author author) {
        String sql = "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getId(), author.getName(), author.getAge(), id);
    }

    @Override
    public Optional<Author> get(long id) {
        String sql = "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1";
        List<Author> authors = jdbcTemplate.query(sql, new AuthorMapper(), id);

        return authors.stream().findFirst();
    }

    @Override
    public List<Author> getAll() {
        String sql = "SELECT id, name, age FROM authors";

        return jdbcTemplate.query(sql, new AuthorMapper());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
