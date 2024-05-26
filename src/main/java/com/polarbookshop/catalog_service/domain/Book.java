package com.polarbookshop.catalog_service.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record Book(

        @Pattern(message = "The ISBN format must be valid. eg: ISBN-10 or ISBN-13",
                regexp = "^([0-9]{10}|[0-9]{13})$" // write a regex for number should be allowed 10 or 13 digits only
        )
        String isbn,
        @NotBlank(message = "The book title must be defined!")
        String title,

        @NotBlank(message = "The book author must be defined!")
        String author,

        @NotNull(message = "The Book price must be defined!")
        @Positive(message = "The book price must be greater then zero!")
        BigDecimal price
) {
    // check below validation for errors
    // need to check with price
    //
}
