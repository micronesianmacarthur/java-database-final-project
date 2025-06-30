package com.project.code.Repo;

import com.project.code.Model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    Customer findByid(Long id);

    Customer findByName(String name);

    Customer findByPhone(String phone);

}