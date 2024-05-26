package com.polarbookshop.catalog_service.repository;

import com.polarbookshop.catalog_service.domain.Book;

public interface BookRepository {

    Iterable<Book> findAll();
    Book findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    Book saveBook(Book book);

    void deleteByIsbn(String isbn);
}
