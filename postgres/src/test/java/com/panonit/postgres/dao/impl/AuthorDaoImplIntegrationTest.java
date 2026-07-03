package com.panonit.postgres.dao.impl;

import com.panonit.postgres.TestDataUtil;
import com.panonit.postgres.dao.AuthorDao;
import com.panonit.postgres.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTest {

    private final AuthorDao underTest;

    @Autowired
    public AuthorDaoImplIntegrationTest(AuthorDao underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        underTest.create(author);

        Optional<Author> result = underTest.get(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatManyAuthorsCanBeCreatedAndRetrieved() {
        Author author1 = TestDataUtil.getTestAuthorA();
        underTest.create(author1);

        Author author2 = TestDataUtil.getTestAuthorB();
        underTest.create(author2);

        Author author3 = TestDataUtil.getTestAuthorC();
        underTest.create(author3);

        List<Author> result = underTest.getAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(author1, author2, author3);

    }

    @Test
    public void testThatAuthorCanBeUpdatedAndRetrieved() {
        Author author1 = TestDataUtil.getTestAuthorA();
        underTest.create(author1);

        Author author2 = TestDataUtil.getTestAuthorB();
        underTest.update(author1.getId(), author2);

        Optional<Author> result = underTest.get(author2.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author2);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.getTestAuthorA();
        underTest.create(author);

        underTest.delete(author.getId());

        Optional<Author> result = underTest.get(author.getId());
        assertThat(result).isEmpty();
    }
}
