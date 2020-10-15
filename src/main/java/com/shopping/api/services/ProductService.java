package com.shopping.api.services;

import com.shopping.api.entities.Product;

import java.util.List;

import javax.validation.Valid;

public interface ProductService
{
    Product createProduct( @Valid Product newProduct );

    Product updateProduct( @Valid Product updateProduct );

    List<Product> getProducts();

    Product getProductById( Long key );
}
