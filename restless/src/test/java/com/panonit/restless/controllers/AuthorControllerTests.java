package com.panonit.restless.controllers;

import com.panonit.restless.TestDataUtil;
import com.panonit.restless.domain.dto.AuthorDto;
import com.panonit.restless.services.AuthorService;
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
class AuthorControllerTests {

    private final MockMvc mockMvc;

    private final AuthorService service;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerTests(MockMvc mockMvc, AuthorService service) {
        this.mockMvc = mockMvc;
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorReturnsHttp201Created() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAuthorReturnsSavedAuthor() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(author.getAge()));
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorsReturnsAuthors() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        service.createAuthor(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(author.getAge()));
    }

    @Test
    public void testThatGetAuthorByIdReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAuthorByIdReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetAuthorByIdReturnsAuthorWhenAuthorExists() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(author.getAge()));
    }

    @Test
    public void testThatReplaceAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);
        author.setName("new name");
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReplaceAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThaReplaceAuthorUpdatesExistingAuthor() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);
        author.setName("new name");
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(author.getAge()));
    }

    @Test
    public void testThatUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);
        author.setName("new name");
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateExistingAuthorUpdatesThatAuthor() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);
        author.setName("new name");
        String json = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(author.getAge()));
    }

    @Test
    public void testThatDeleteNonExistingAuthorReturnsHttpStatus204() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteExistingAuthorReturnsHttpStatus204() throws Exception {
        AuthorDto author = TestDataUtil.getFirstTestAuthor();
        author = service.createAuthor(author);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/authors/{id}", author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}