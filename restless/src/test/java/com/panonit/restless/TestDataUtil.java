package com.panonit.restless;


import com.panonit.restless.domain.dto.AuthorDto;
import com.panonit.restless.domain.dto.BookDto;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static AuthorDto getFirstTestAuthor() {
        return AuthorDto.builder()
                .name("author name first")
                .age(31)
                .build();
    }

    public static AuthorDto getSecondTestAuthor() {
        return AuthorDto.builder()
                .name("author name second")
                .age(32)
                .build();
    }

    public static AuthorDto getThirdTestAuthor() {
        return AuthorDto.builder()
                .name("author name third")
                .age(33)
                .build();
    }

    public static BookDto getFirstTestBook(AuthorDto author) {
        return BookDto.builder()
                .isbn("123")
                .title("book title first")
                .build();
    }

    public static BookDto getSecondTestBook(AuthorDto author) {
        return BookDto.builder()
                .isbn("234")
                .title("book title second")
                .build();
    }

    public static BookDto getThirdTestBook(AuthorDto author) {
        return BookDto.builder()
                .isbn("345")
                .title("book title third")
                .build();
    }
}
