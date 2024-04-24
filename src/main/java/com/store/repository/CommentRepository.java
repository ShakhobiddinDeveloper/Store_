package com.store.repository;

import com.store.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, String>, PagingAndSortingRepository<CommentEntity, String> {
    Optional<CommentEntity> findByIdAndVisible(String id, Boolean visible);

    @Transactional
    @Modifying
    @Query("update CommentEntity set visible=false where id=?1")
    void deleteById(Integer id);

    List<CommentEntity> findByBookIdAndVisible(String bookId, Boolean visible);

}
