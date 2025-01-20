package com.digital.travel.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseModel {

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Order(String customerName, OrderStatus status, double totalPrice, List<Product> products) {
        this.customerName = customerName;
        this.status = status;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    public Order(){}

    public void addProduct(Product product) {
        product.setOrder(this);
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setOrder(null);
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

}
