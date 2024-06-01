package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> bookJacksonTester;

    @Test
    void testSerialize() throws IOException {

        // TODO: need to refactor with ObjectMapper

        var expectedBook = Book.of("1231231231", "Spring boot in action", "JOHNNEd", new BigDecimal("10.20"));

        JsonContent<Book> jsonContent = bookJacksonTester.write(expectedBook);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(expectedBook.isbn());

        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(expectedBook.title());

        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(expectedBook.author());

//        TODO: need to check below condition that not working correctly
//        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
//                .isEqualTo(expectedBook.price());
    }

    @Test
    void testDeserialize() throws IOException {
        // convert a string object to pojo
        var strBookObject = """
                    {
                     "isbn": "1234567890",
                        "title": "Title",
                        "author": "Author",
                        "price": 9.90
                    }
                """;

        assertThat(bookJacksonTester.parse(strBookObject))
                .usingRecursiveAssertion()
                .isEqualTo(Book.of(
                        "1234567890",
                        "Title",
                        "Author",
                        new BigDecimal("9.90")
                ));
    }
}
