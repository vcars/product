package com.example.product.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Integer id;

    @Column(name = "product_name")
    String productName;

    @Column(name = "stock")
    Integer stock;

    @Column(name = "price")
    Integer price;

}
