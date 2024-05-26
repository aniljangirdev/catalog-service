package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookIsCreated(){
        var expectedBook = new Book(
                "1234567890",
                "Spring boot in action",
                "DEAGO",
                new BigDecimal("120.00")
        );

        webTestClient.post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertNotNull(actualBook);
                    assertEquals(expectedBook.isbn(), actualBook.isbn());
                });


    }
}
