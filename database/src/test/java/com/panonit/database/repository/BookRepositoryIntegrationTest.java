package com.panonit.database.repository;

import com.panonit.database.TestDataUtil;
import com.panonit.database.domain.Author;
import com.panonit.database.domain.Book;
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
public class BookRepositoryIntegrationTest {

    private final BookRepository repository;

    @Autowired
    public BookRepositoryIntegrationTest(BookRepository repository) {
        this.repository = repository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        Book book = TestDataUtil.getTestBookA(author);

        book = repository.save(book);
        Optional<Book> result = repository.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatManyBooksCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        Book book1 = TestDataUtil.getTestBookA(author);
        Book book2 = TestDataUtil.getTestBookB(author);
        Book book3 = TestDataUtil.getTestBookC(author);

        book1 = repository.save(book1);
        book2 = repository.save(book2);
        book3 = repository.save(book3);
        Iterable<Book> result = repository.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(book1, book2, book3);
    }

    @Test
    public void testThatBookCanBeUpdatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        Book book = TestDataUtil.getTestBookA(author);

        book = repository.save(book);
        book.setTitle("updated title");
        book = repository.save(book);
        Optional<Book> result = repository.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.getTestAuthorA();
        Book book = TestDataUtil.getTestBookA(author);

        book = repository.save(book);
        repository.deleteById(book.getIsbn());
        Optional<Book> result = repository.findById(book.getIsbn());

        assertThat(result).isEmpty();
    }
}
