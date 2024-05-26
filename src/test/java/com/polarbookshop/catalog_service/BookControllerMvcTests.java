package com.polarbookshop.catalog_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polarbookshop.catalog_service.controller.BookController;
import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.exception.BookNotFoundException;
import com.polarbookshop.catalog_service.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;


@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenPostRequestThenBookIsCreated() throws Exception {
        // GIVEN
        var expectedBook = new Book("1231231231", "Spring boot in action", "JOHNNEd", new BigDecimal("10.20"));
        String strExpectedBook = objectMapper.writeValueAsString(expectedBook);

        //WHEN
        when(bookService.createBook(any(Book.class))).thenReturn(expectedBook);

        // THEN
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(strExpectedBook))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn", is(expectedBook.isbn())))
                .andExpect(jsonPath("$.title", is(expectedBook.title())))
                .andExpect(jsonPath("$.author", is(expectedBook.author())));

        // below snippet does not work while create book
//        webTestClient.post()
//                .uri("/books")
//                .bodyValue(expectedBook)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectBody(Book.class)
//                .value(actualBook -> {
//                    assertNotNull(actualBook);
//                    assertEquals(expectedBook.isbn(), actualBook.isbn());
//                });
    }

    @Test
    void whenGetBookNotExistThenShouldBeReturn404() throws Exception {
        // GIVEN
        String isbn = "1234568770";
        when(bookService.findBookByIsbn(isbn)).thenThrow(BookNotFoundException.class);

        // WHEN|THEN
        mockMvc
                .perform(get("/books/" + isbn))
                .andExpect(status().isNotFound());
    }
}
