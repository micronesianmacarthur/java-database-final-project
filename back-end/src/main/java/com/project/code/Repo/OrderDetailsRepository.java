package com.project.code.Repo;

import com.project.code.Model.OrderDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    // No custom methods required, default CRUD operations are available out of the box.

}