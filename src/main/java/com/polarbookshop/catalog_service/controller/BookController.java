package com.polarbookshop.catalog_service.controller;

import com.polarbookshop.catalog_service.domain.Book;
import com.polarbookshop.catalog_service.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> getBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> findBook(@PathVariable("isbn") String isbn){
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@Valid @RequestBody  Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{isbn}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn){
        bookService.deleteByIsbn(isbn);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> editBookDetails( @Valid  @RequestBody Book book, @PathVariable String isbn) {
        return ResponseEntity.ok(bookService.updateBook(book, isbn));
    }
}
