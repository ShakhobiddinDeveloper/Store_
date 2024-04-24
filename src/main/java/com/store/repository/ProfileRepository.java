package com.store.repository;

import com.store.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {

    @Query("from ProfileEntity where login=?1 and visible=true and status='ACTIVE' ")
    Optional<ProfileEntity> findByLogin(String username);

    Optional<ProfileEntity> findByLoginAndPassword(String login, String password);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=?1")
    void deleteById(String id);

    @Query("from ProfileEntity where visible=true order by purchasesCount desc limit 1")
    ProfileEntity activePurchasesCount();

    @Query("from ProfileEntity where visible=true order by pricePurchases desc limit 1")
    ProfileEntity activePricePurchases();

    @Query("from ProfileEntity where visible=true order by commentCount desc limit 1")
    ProfileEntity activeCommentCount();
}
