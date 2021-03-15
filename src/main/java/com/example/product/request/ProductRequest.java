package com.example.product.request;

import lombok.Data;

@Data
public class ProductRequest {

    Integer id;

    String productName;

    Integer stock;

    Integer price;

}
