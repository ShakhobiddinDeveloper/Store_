package com.store.repository;

import com.store.entity.BookPriceEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookPriceRepository extends CrudRepository<BookPriceEntity, String> {
    @Query("from BookPriceEntity where id=?1 and visible=true ")
    Optional<BookPriceEntity> getById(String id);

    @Transactional
    @Modifying
    @Query("update BookPriceEntity set visible=false where id=?1")
    void deleteById(String id);
    @Query("from BookPriceEntity where visible=true and bookId=?1 ")
    BookPriceEntity findByBookId(String bookId);
}
