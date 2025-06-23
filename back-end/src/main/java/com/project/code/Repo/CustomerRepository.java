package com.project.code.Repo;

import com.project.code.Model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Customer findByEmail(String email);

    public Customer findByCustomerId(Long id);

    public Customer findByName(String name);

    public Customer findByPhone(String phone);

}