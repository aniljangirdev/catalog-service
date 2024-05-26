package com.polarbookshop.catalog_service.service;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.repository.BookRepository;
import com.polarbookshop.catalog_service.repository.InMemoryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(@Qualifier("inMemoryBookRepository") BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public void deleteByIsbn(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book createBook(Book book) {
        return bookRepository.saveBook(book);
    }

    public Book updateBook(Book book, String isbn) {
        return Optional.ofNullable(bookRepository.findByIsbn(isbn))
                .map(existingBook -> {
                    var newBook = new Book(
                            book.isbn(),
                            book.title(),
                            book.author(),
                            book.price()
                    );
                    return bookRepository.saveBook(newBook);
                })
                .orElseGet(() -> createBook(book));
    }
}
