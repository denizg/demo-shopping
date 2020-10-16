package com.shopping.api.services;

import com.shopping.api.entities.Product;

import java.util.List;

public interface ProductService
{
    Product createProduct( Product newProduct );

    Product updateProduct( Product updateProduct );

    List<Product> getProducts();

    Product getProductById( long key );
}
