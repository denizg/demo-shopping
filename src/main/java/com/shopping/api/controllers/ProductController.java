package com.shopping.api.controllers;

import com.shopping.api.entities.Product;
import com.shopping.api.models.ProductModel;
import com.shopping.api.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( ProductController.ENDPOINT )
@Api(
    produces = MediaType.APPLICATION_JSON_VALUE,
    tags = "Product"
)
@RequiredArgsConstructor
public class ProductController
{
    public static final String ENDPOINT = "/api/products";
    public static final String ENDPOINT_ID = "/{id}";

    private final ProductService productService;

    @ApiOperation( "Get all Products with the latest Price value" )
    @ResponseStatus( code = HttpStatus.OK )
    @GetMapping
    public List<ProductModel> getProducts()
    {
        return productService.getProducts().stream()
            /**/ .map( ProductModel::convertToModel ).collect( Collectors.toList() );
    }

    @ApiOperation( "Create a Product" )
    @PostMapping
    @ResponseStatus( code = HttpStatus.CREATED )
    public ProductModel createProduct( final @RequestBody ProductModel newProduct )
    {
        Product product = productService.createProduct( newProduct.convertToEntity() );
        newProduct.setId( product.getId() );
        return newProduct;
    }

    @ApiOperation( "Update a Product's Price" )
    @PutMapping( ENDPOINT_ID )
    @ResponseStatus( code = HttpStatus.OK )
    public ProductModel updateProduct(
        @PathVariable( value = "id" ) Long productId,
        final @RequestBody ProductModel updatedProduct
    )
    {
        updatedProduct.setId( productId );
        Product product = productService.updateProduct( updatedProduct.convertToEntity() );
        updatedProduct.setId( product.getId() );
        return updatedProduct;
    }
}
