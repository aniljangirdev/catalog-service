package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.config.DataConfig;
import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void
    findBookByIsbnWhenExisting(){
        // GIVEN
        var bookIsbn = "1234567890";
        var book = Book.of(bookIsbn,
                "Spring boot in action",
                "JEACC",
                new BigDecimal("100.0"),
                "Manning!");
        jdbcAggregateTemplate.insert(book);

        // WHEN
        Book actualBook = bookRepository.findByIsbn(bookIsbn);

        // THEN
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.isbn()).isEqualTo(bookIsbn);
    }

}
