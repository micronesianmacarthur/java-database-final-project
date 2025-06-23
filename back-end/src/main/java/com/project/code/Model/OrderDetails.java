package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDateTime;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonManagedReference
    private Store store;

    private double totalPrice;

    private LocalDateTime date;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    // constructor
    public OrderDetails() {}

    public OrderDetails(Customer customer, Store store, double totalPrice, LocalDateTime date) {
        this.customer =customer;
        this.store = store;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}