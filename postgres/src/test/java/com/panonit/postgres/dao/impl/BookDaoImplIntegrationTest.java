package com.panonit.postgres.dao.impl;

import com.panonit.postgres.TestDataUtil;
import com.panonit.postgres.dao.AuthorDao;
import com.panonit.postgres.dao.BookDao;
import com.panonit.postgres.domain.Author;
import com.panonit.postgres.domain.Book;
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
public class BookDaoImplIntegrationTest {

    private final BookDao underTest;
    private final AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTest(BookDao underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.getTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        Optional<Book> result = underTest.get(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatManyBooksCanBeCreatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        authorDao.create(author);

        Book book1 = TestDataUtil.getTestBookA();
        book1.setAuthorId(author.getId());
        underTest.create(book1);

        Book book2 = TestDataUtil.getTestBookB();
        book2.setAuthorId(author.getId());
        underTest.create(book2);

        Book book3 = TestDataUtil.getTestBookC();
        book3.setAuthorId(author.getId());
        underTest.create(book3);

        List<Book> result = underTest.getAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(book1, book2, book3);
    }

    @Test
    public void testThatBookCanBeUpdatedAndRetrieved() {
        Author author = TestDataUtil.getTestAuthorA();
        authorDao.create(author);

        Book book1 = TestDataUtil.getTestBookA();
        book1.setAuthorId(author.getId());
        underTest.create(book1);

        Book book2 = TestDataUtil.getTestBookB();
        book2.setAuthorId(author.getId());
        underTest.update(book1.getIsbn(), book2);

        Optional<Book> result = underTest.get(book2.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book2);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.getTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.getTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> result = underTest.get(book.getIsbn());
        assertThat(result).isEmpty();
    }
}
