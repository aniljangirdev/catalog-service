package com.polarbookshop.catalog_service.service;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.exception.BookNotFoundException;
import com.polarbookshop.catalog_service.repository.BookRepository;
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
        return Optional.ofNullable(bookRepository.findByIsbn(isbn))
                .orElseThrow(() -> new BookNotFoundException(String.format("book not find with isbn:%s:", isbn)));
    }

    public void deleteByIsbn(String isbn) {
        Optional.ofNullable(bookRepository.findByIsbn(isbn))
                .map(existingBook -> {
                    bookRepository.deleteByIsbn(isbn);
                    return existingBook;
                })
                .orElseThrow(() -> new BookNotFoundException(String.format("book not find with isbn:%s:", isbn)));
        bookRepository.deleteByIsbn(isbn);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book, String isbn) {
        return Optional.ofNullable(bookRepository.findByIsbn(isbn))
                .map(existingBook -> {
                    var newBook = new Book(
                            existingBook.id(),
                            existingBook.createdDate(),
                            existingBook.lastModifiedDate(),
                            existingBook.publisher(),
                            book.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            existingBook.version()
                    );
                    return bookRepository.save(newBook);
                })
                .orElseGet(() -> createBook(book));
    }
}
