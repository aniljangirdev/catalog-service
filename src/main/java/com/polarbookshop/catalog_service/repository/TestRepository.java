package com.polarbookshop.catalog_service.repository;

import com.polarbookshop.catalog_service.domain.Book;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository implements BookRepository{
    @Override
    public Iterable<Book> findAll() {
        return null;
    }

    @Override
    public Book findByIsbn(String isbn) {
        return null;
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return false;
    }

    @Override
    public Book saveBook(Book book) {
        return null;
    }

    @Override
    public void deleteByIsbn(String isbn) {

    }
}
