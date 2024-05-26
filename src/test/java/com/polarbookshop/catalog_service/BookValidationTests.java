package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp(){
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenAllFieldCorrectThenValidationSucceeds(){
        var book = new Book(
                "1232567890",
                "Spring in action",
                "JOHHNN",
                new BigDecimal("120.0")
        );

        Set<ConstraintViolation<Book>> validate = validator.validate(book);
        assertTrue(validate.isEmpty());
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFailed(){
        var book = new Book(
                "ISBN11",
                "spring boot in action",
                "JEKKY",
                new BigDecimal("100.00")
        );
        Set<ConstraintViolation<Book>> validate = validator.validate(book);
        assertFalse(validate.isEmpty());
        assertEquals(validate.size(), 1);
        String errorMessage = validate.iterator().next().getMessage();
        assertEquals(errorMessage, "The ISBN format must be valid. eg: ISBN-10 or ISBN-13");
    }
}
