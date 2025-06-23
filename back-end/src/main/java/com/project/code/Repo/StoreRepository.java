package com.project.code.Repo;

import com.project.code.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // find store by id
    Store findById(long id);

    // find store by sub name
    @Query("SELECT i FROM Store i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :storeName, '%'))")
    List<Store> findBySubName(String storeName);

}