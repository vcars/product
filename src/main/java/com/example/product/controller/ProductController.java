package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.request.PaymentRequest;
import com.example.product.response.Response;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Response response;

    @GetMapping(value = "/list")
    public ResponseEntity<Response> list(){
        List<ProductDto> result = this.productService.list();
        response = new Response(result,"Get List Product",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<Response> detail(@PathVariable Integer id){
        ProductDto result = this.productService.detail(id);
        response = new Response(result,"Get Detail Product",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response> create(@RequestBody Product product){
        Boolean result = this.productService.create(product);
        response = new Response(result,"Product Submitted Succesfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Response> update(@RequestBody Product product){
        Boolean result = this.productService.update(product);
        response = new Response(result,"Product Updated Succesfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Integer id){
        Boolean result = this.productService.delete(id);
        response = new Response(result,"Product Deleted Succesfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/buy")
    public ResponseEntity<Response> buy(@RequestBody PaymentRequest request){
        Boolean result = this.productService.buy(request);
        response = new Response(result,"Product Buyed Succesfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value = "/cancel")
    public ResponseEntity<Response> cancel(@RequestBody PaymentRequest request){
        Boolean result = this.productService.cancel(request);
        response = new Response(result,"Product Canceled Succesfully",true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
