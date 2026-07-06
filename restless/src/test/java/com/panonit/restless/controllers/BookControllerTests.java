package com.panonit.restless.controllers;

import com.panonit.restless.TestDataUtil;
import com.panonit.restless.domain.dto.BookDto;
import com.panonit.restless.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerTests {

    private final MockMvc mockMvc;

    private final BookService service;

    private final ObjectMapper objectMapper;


    @Autowired
    public BookControllerTests(MockMvc mockMvc, BookService service) {
        this.mockMvc = mockMvc;
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturnsHttp201Created() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateBookReturnsSavedBook() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        book = service.createBook(book.getIsbn(), book);
        book.setTitle("new book title");
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/books/{id}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatCreateExistingBookUpdatesThatBook() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        book = service.createBook(book.getIsbn(), book);
        book.setTitle("new book title");
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListBooksReturnsBooks() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);

        service.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(book.getAuthor()));
    }

    @Test
    public void testThatGetBookByIdReturnsHttpStatus200WhenBookExists() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);

        book = service.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBookByIdReturnsHttpStatus404WhenNoBookExists() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/books/123")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetBookByIdReturnsBook() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);

        book = service.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        book = service.createBook(book.getIsbn(), book);
        book.setTitle("new title");
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus404WhenNoBookExists() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/books/123")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateExistingBookUpdatesThatBook() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        book = service.createBook(book.getIsbn(), book);
        book.setTitle("new title");
        String json = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatDeleteNonExistingBooksReturnsHttpStatus204() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/books/123")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteExistingBookReturnsHttpStatus204() throws Exception {
        BookDto book = TestDataUtil.getFirstTestBook(null);
        book = service.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/books/{isbn}", book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
