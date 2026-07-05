package com.panonit.jackson;

import com.panonit.jackson.domain.Book;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTests {

    @Test
    public void testThatObjectMapperMapsBookToJsonString() {
        Book book = new Book("123", "book title", "book author", "book year");
        String result = new ObjectMapper().writeValueAsString(book);
        assertThat(result).isEqualTo("{\"isbn\":\"123\",\"title\":\"book title\",\"author\":\"book author\",\"year\":\"book year\"}");
    }

    @Test
    public void testThatObjectMapperMapsJsonStringToBook() {
        String jsonString = "{\"fu\":\"kru\",\"isbn\":\"123\",\"title\":\"book title\",\"author\":\"book author\",\"year\":\"book year\"}";
        Book result = new ObjectMapper().readValue(jsonString, Book.class);
        assertThat(result).isEqualTo(new Book("123", "book title", "book author", "book year"));
    }
}
