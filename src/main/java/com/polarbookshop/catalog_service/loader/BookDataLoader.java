package com.polarbookshop.catalog_service.loader;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.repository.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
//@Profile("testData") instread of this '@ConditionalOnProperty' is more conveniant
@ConditionalOnProperty(prefix = "polar.testData", name = "enabled" ,havingValue = "true")
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(@Qualifier("inMemoryBookRepository") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        var book = new Book(
                "1234567890",
                "Spring in action",
                "JOHHN",
                new BigDecimal("30.20")
        );
        bookRepository.saveBook(book);
    }
}
