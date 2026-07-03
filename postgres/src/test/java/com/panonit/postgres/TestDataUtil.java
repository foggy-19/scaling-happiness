package com.panonit.postgres;

import com.panonit.postgres.domain.Author;
import com.panonit.postgres.domain.Book;

public final class TestDataUtil {

    private TestDataUtil() {}

    public static Author getTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("name A")
                .age(31)
                .build();
    }

    public static Author getTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("name B")
                .age(32)
                .build();
    }

    public static Author getTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("name C")
                .age(33)
                .build();
    }

    public static Book getTestBookA() {
        return Book.builder()
                .isbn("isbn A")
                .title("title A")
                .authorId(1L)
                .build();
    }

    public static Book getTestBookB() {
        return Book.builder()
                .isbn("isbn B")
                .title("title B")
                .authorId(1L)
                .build();
    }

    public static Book getTestBookC() {
        return Book.builder()
                .isbn("isbn C")
                .title("title C")
                .authorId(1L)
                .build();
    }
}
