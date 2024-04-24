package com.store.repository;

import com.store.entity.BookEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<BookEntity, String> {
    @Query("from BookEntity where id=?1 and visible=true ")
    Optional<BookEntity> getById(String bookId);

    @Transactional
    @Modifying
    @Query("update BookEntity set visible=false where id=?1")
    void deleteById(String id);

    @Query("from BookEntity where visible=true ")
    List<BookEntity> getAll();

    @Query("from BookEntity where visible=true order by salesCount desc limit 1")
    BookEntity maxSales();

    @Query("from BookEntity where visible=true order by salesCount desc limit 1")
    BookEntity maxSalesCount();

    @Query("from BookEntity where visible=true order by commentCount desc limit 1")
    BookEntity maxCommentCount();

    @Query("from BookEntity where visible=true order by viewCount desc limit 1")
    BookEntity maxViewCount();
}
