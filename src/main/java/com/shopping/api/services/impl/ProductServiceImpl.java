package com.shopping.api.services.impl;

import com.shopping.api.entities.Product;
import com.shopping.api.repositories.ProductRepository;
import com.shopping.api.services.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class ProductServiceImpl implements ProductService
{
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl( ProductRepository productRepository )
    {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct( Product newProduct )
    {
        return productRepository.save( newProduct );
    }

    @Override
    public Product updateProduct( Product updateProduct )
    {
        return productRepository.save( updateProduct );
    }

    @Override
    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById( Long id )
    {
        return productRepository.getOne( id );
    }
}
