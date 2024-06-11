package com.polarbookshop.catalog_service.loader;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.domain.UserData;
import com.polarbookshop.catalog_service.repository.BookRepository;
import com.polarbookshop.catalog_service.repository.UserDataRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("testData") // instread of this '@ConditionalOnProperty' is more conveniant
@ConditionalOnProperty(prefix = "polar.testData", name = "enabled" ,havingValue = "true")
public class BookDataLoader {

    private final BookRepository bookRepository;
    private final UserDataRepository userDataRepository;

    public BookDataLoader(BookRepository bookRepository, UserDataRepository userDataRepository) {
        this.bookRepository = bookRepository;
        this.userDataRepository = userDataRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        bookRepository.deleteAll();

        var book = Book.of(
                "1234567890",
                "Spring in action",
                "JOHHN",
                new BigDecimal("30.20"),
                "Manning!"
        );
        bookRepository.save(book);

        userDataRepository.deleteAll();

        var userData = UserData.of(
                "sampleUsername",
                "aniljangir@gmail.com",
                null
        );
        userDataRepository.save(userData);
    }
}
