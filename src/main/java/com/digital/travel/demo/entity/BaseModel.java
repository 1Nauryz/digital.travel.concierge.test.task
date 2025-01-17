package com.digital.travel.demo.entity;


import jakarta.persistence.*;


@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

}
