package com.polarbookshop.catalog_service.repository;

import com.polarbookshop.catalog_service.domain.Book;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("inMemoryBookRepository")
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @Modifying
    @Transactional
    @Query("delete from book where isbn= :isbn")
    void deleteByIsbn(String isbn);
}
