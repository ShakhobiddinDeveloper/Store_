package com.store.repository;

import com.store.entity.PurchaseEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<PurchaseEntity, String> {
    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=?1")
    void deleteById(String id);

}
