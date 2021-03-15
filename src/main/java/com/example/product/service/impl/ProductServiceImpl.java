package com.example.product.service.impl;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.request.PaymentRequest;
import com.example.product.service.ProductService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<ProductDto> list() {
        List<Product> products = this.productRepository.findAll();

        if(products.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Data not found");

        return products.stream().map(this::getFetchToDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto detail(Integer id) {
        Optional<Product> product = this.productRepository.findById(id);
        AtomicReference<ProductDto> productDto = new AtomicReference<>();
        product.ifPresentOrElse(data -> productDto.set(this.getFetchToDto(data)) ,()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        );
        return productDto.get();
    }

    @Override
    public Boolean create(Product product) {
        Product newProduct = new Product();
        Optional<Product> existingProduct = this.productRepository.findById(product.getId());
        if(!existingProduct.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product Already Exist");

        newProduct.setProductName(product.getProductName());
        newProduct.setStock(product.getStock());
        newProduct.setPrice(product.getPrice());
        this.productRepository.save(newProduct);
        return true;
    }

    @Override
    public Boolean update(Product product) {
        Optional<Product> existingProduct = this.productRepository.findById(product.getId());
        existingProduct.ifPresentOrElse(data->{
                    data.setProductName(product.getProductName());
                    data.setStock(product.getStock());
                    data.setPrice(product.getPrice());
                    this.productRepository.save(data);
                } ,()->{
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
                }
                );
            return true;
    }

    @Override
    public Boolean delete(Integer id) {
    if(ObjectUtils.isEmpty(this.productRepository.findById(id)))
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");

    this.productRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Boolean buy(PaymentRequest request) {
        Optional<Product> optionalProduct = this.productRepository.findById(request.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        Product existingProduct = optionalProduct.get();
        if (existingProduct.getStock() < request.getStock()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product stock is not enough");
        }
        existingProduct.setStock(existingProduct.getStock()- request.getStock());
        this.productRepository.save(existingProduct);
            return true;
    }

    @Override
    @Transactional
    public Boolean cancel(PaymentRequest request) {
        Optional<Product> optionalProduct = this.productRepository.findById(request.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }
        Product existingProduct = optionalProduct.get();
        existingProduct.setStock(existingProduct.getStock() + request.getStock());
        this.productRepository.save(existingProduct);
            return null;
    }

    private ProductDto getFetchToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setStock(product.getStock());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
