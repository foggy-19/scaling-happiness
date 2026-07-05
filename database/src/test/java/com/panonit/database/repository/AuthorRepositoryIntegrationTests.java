package com.panonit.database.repository;


import com.panonit.database.TestDataUtil;
import com.panonit.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository repository;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository repository) {
        this.repository = repository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();

        author = repository.save(author);
        Optional<Author> result = repository.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatManyAuthorsCanBeCreatedAndRetrieved() {
        Author author1 = TestDataUtil.getTestAuthorA();
        Author author2 = TestDataUtil.getTestAuthorB();
        Author author3 = TestDataUtil.getTestAuthorC();

        author1 = repository.save(author1);
        author2 = repository.save(author2);
        author3 = repository.save(author3);
        Iterable<Author> result = repository.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(author1, author2, author3);
    }

    @Test
    public void testThatAuthorCanBeUpdatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();

        author = repository.save(author);
        author.setName("updated name");
        author = repository.save(author);
        Optional<Author> result = repository.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtil.getTestAuthorA();

        author = repository.save(author);
        repository.deleteById(author.getId());
        Optional<Author> result = repository.findById(author.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        Author author1 = TestDataUtil.getTestAuthorA();
        Author author2 = TestDataUtil.getTestAuthorB();
        Author author3 = TestDataUtil.getTestAuthorC();

        author1 = repository.save(author1);
        author2 = repository.save(author2);
        author3 = repository.save(author3);
        Iterable<Author> result = repository.ageLessThan(33);

        assertThat(result)
                .hasSize(2)
                .doesNotContain(author3)
                .containsExactly(author1, author2);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        Author author1 = TestDataUtil.getTestAuthorA();
        Author author2 = TestDataUtil.getTestAuthorB();
        Author author3 = TestDataUtil.getTestAuthorC();

        author1 = repository.save(author1);
        author2 = repository.save(author2);
        author3 = repository.save(author3);
        Iterable<Author> result = repository.ageGreaterThan(32);

        assertThat(result)
                .hasSize(1)
                .doesNotContain(author1, author2)
                .containsExactly(author3);
    }
}
