package com.panonit.database;


import com.panonit.database.domain.Author;
import com.panonit.database.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static Author getTestAuthorA() {
        return Author.builder()
                .name("a")
                .age(31)
                .build();
    }

    public static Author getTestAuthorB() {
        return Author.builder()
                .name("b")
                .age(32)
                .build();
    }

    public static Author getTestAuthorC() {
        return Author.builder()
                .name("c")
                .age(33)
                .build();
    }

    public static Book getTestBookA(Author author) {
        return Book.builder()
                .isbn("a")
                .title("a")
                .author(author)
                .build();
    }

    public static Book getTestBookB(Author author) {
        return Book.builder()
                .isbn("b")
                .title("b")
                .author(author)
                .build();
    }

    public static Book getTestBookC(Author author) {
        return Book.builder()
                .isbn("c")
                .title("c")
                .author(author)
                .build();
    }
}
