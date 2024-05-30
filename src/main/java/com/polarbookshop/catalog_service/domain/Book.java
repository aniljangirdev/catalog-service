package com.polarbookshop.catalog_service.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.Instant;

public record Book(

        @Id
        Long id,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Pattern(message = "The ISBN format must be valid. eg: ISBN-10 or ISBN-13",
                regexp = "^([0-9]{10}|[0-9]{13})$" // write a regex for number should be allowed 10 or 13 digits only
        )
        String isbn,
        @NotBlank(message = "The book title must be defined!")
        String title,

        @NotBlank(message = "The book author must be defined!")
        String author,

        // check below validation for errors
        // need to check with price
        @NotNull(message = "The Book price must be defined!")
        @Positive(message = "The book price must be greater then zero!")
        BigDecimal price,

        @Version
        int version
) {

    public static Book of(String isbn, String title, String author, BigDecimal price) {
        return new Book(null, null, null, isbn, title, author, price, 0);
    }
}
