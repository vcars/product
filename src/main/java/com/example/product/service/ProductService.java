package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.request.PaymentRequest;

import java.util.List;

public interface ProductService {

    List<ProductDto> list();
    ProductDto detail(Integer id);
    Boolean create(Product product);
    Boolean update(Product product);
    Boolean delete(Integer id);
    Boolean buy(PaymentRequest request);
    Boolean cancel(PaymentRequest request);
}
