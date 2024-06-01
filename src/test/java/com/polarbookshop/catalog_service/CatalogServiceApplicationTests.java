package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class CatalogServiceApplicationTests {

    /* this class is containing integration Test*/

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    private static final String BOOK_ISBN = "1234567891";
    private static final Book BOOK_TO_CREATE = Book.of(BOOK_ISBN, "spring boot in action!", "BBC", new BigDecimal("10.2"));

    private static final String ENDPOINT = "/books";

    @BeforeEach
    public void setup() {
        bookRepository.deleteByIsbn(BOOK_ISBN);
    }

    @Test
    void whenGetRequestWithIdThenBookReturnedStatus200() {
        //GIVEN

        //WHEN
        Book expectedBook = webTestClient
                .post()
                .uri(ENDPOINT)
                .bodyValue(BOOK_TO_CREATE)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> assertThat(book).isNotNull())
                .returnResult()
                .getResponseBody();

        //THEN
        webTestClient
                .get()
                .uri(ENDPOINT + "/{isbn}", BOOK_ISBN)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(expectedBook).isNotNull();
                    assertThat(book.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenPostRequestWithBookThenBookReturnedStatus201() {
        // WHEN | THEN
        webTestClient
                .post()
                .uri(ENDPOINT)
                .bodyValue(BOOK_TO_CREATE)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> assertThat(book).isNotNull());
    }

    @Test
    void whenDeleteRequestWithIdThenReturnStatus204() {
        // GIVEN | WHEN
        webTestClient
                .post()
                .uri(ENDPOINT)
                .bodyValue(BOOK_TO_CREATE)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> assertThat(book).isNotNull());

        // THEN
        webTestClient
                .delete()
                .uri(ENDPOINT + "/{isbn}", BOOK_ISBN)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        webTestClient
                .get()
                .uri(ENDPOINT + "/{isbn}", BOOK_ISBN)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Error.class)
                .value(err -> {
                    assertThat(err.getMessage()).isEqualTo("book not find with isbn:" + BOOK_ISBN + ":");
                });
    }

    @Test
    void whenPutRequestWithIdThenReturnStatus200() {
        // GIVEN
        var UPDATE_TITLE = "Spring boot in action";
        var BOOK_TO_UPDATE = Book.of(
                BOOK_ISBN,
                UPDATE_TITLE,
                BOOK_TO_CREATE.author(),
                BOOK_TO_CREATE.price()
        );

        // WHEN
        webTestClient
                .post()
                .uri(ENDPOINT)
                .bodyValue(BOOK_TO_CREATE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(book.isbn()).isEqualTo(BOOK_ISBN);
                });

        // THEN
        webTestClient
                .put()
                .uri(ENDPOINT + "/{isbn}", BOOK_ISBN)
                .bodyValue(BOOK_TO_UPDATE)
                .exchange()
                .expectBody(Book.class)
                .value(book -> {
                    assertThat(book.isbn()).isEqualTo(BOOK_ISBN);
                    assertThat(book.title()).isEqualTo(UPDATE_TITLE);
                });
    }

    @Test
    void whenGetAllBooksRequestThenReturnStatus200() {
        //WHEN
        Book expectedBook = webTestClient
                .post()
                .uri(ENDPOINT)
                .bodyValue(BOOK_TO_CREATE)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .value(book -> assertThat(book).isNotNull())
                .returnResult()
                .getResponseBody();

        //THEN
        webTestClient
                .get()
                .uri(ENDPOINT)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Book.class)
                .value(books -> {
                    Assertions.assertThat(books).isNotEmpty();
                    boolean isMatch = books.stream()
                            .anyMatch(book -> book.isbn().equals(expectedBook.isbn()));
                    Assertions.assertThat(isMatch).isTrue();
                });
    }
}
