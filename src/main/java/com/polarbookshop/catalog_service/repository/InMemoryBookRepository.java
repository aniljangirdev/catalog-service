package com.polarbookshop.catalog_service.repository;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.exception.BookNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Book findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn))
                .orElseThrow(() -> new BookNotFoundException(String.format("Book: %s not found: ", isbn)));
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn)).isPresent();
    }

    @Override
    public Book saveBook(Book book) {
        return books.put(book.isbn(), book);
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}
