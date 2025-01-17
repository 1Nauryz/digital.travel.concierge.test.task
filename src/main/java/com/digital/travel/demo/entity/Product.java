package com.digital.travel.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseModel {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getName(){
        return name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice(){
        return price;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity(){
        return quantity;
    }


}
