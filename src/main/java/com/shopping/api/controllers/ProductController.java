package com.shopping.api.controllers;

import com.shopping.api.entities.Product;
import com.shopping.api.exceptions.ProductNotFoundException;
import com.shopping.api.models.ProductModel;
import com.shopping.api.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping( ProductController.ENDPOINT )
@Api(
    produces = MediaType.APPLICATION_JSON_VALUE,
    tags = "Product"
)
@RequiredArgsConstructor
public class ProductController
{
    public static final String ENDPOINT = "/products";
    public static final String ENDPOINT_ID = "/{id}";
    private final ProductService productService;

    @ApiOperation(
        value = "Get all Products",
        response = ProductModel.class
    )
    @ResponseStatus( code = HttpStatus.OK )
    @GetMapping
    public List<ProductModel> getProducts()
    {
        return productService.getProducts().stream()
            /**/ .map( ProductModel::convertToModel ).collect( Collectors.toList() );
    }

    @ApiOperation(
        value = "Create a Product",
        response = ProductModel.class
    )
    @PostMapping
    @ResponseStatus( code = HttpStatus.CREATED )
    public ProductModel createProduct( final @Valid @RequestBody ProductModel newProduct )
    {
        Product product = productService.createProduct( newProduct.convertToEntity() );
        newProduct.setId( product.getId() );
        return newProduct;
    }

    @ApiOperation( "Get a Product" )
    @GetMapping( ENDPOINT_ID )
    @ResponseStatus( code = HttpStatus.OK )
    public ProductModel getProduct( final @Valid
        @PathVariable( value = "id" )
        long productId
    )
    {
        return ProductModel.convertToModel( productService.getProductById( productId ) );
    }

    @ApiOperation( "Update a Product" )
    @PutMapping( ENDPOINT_ID )
    @ResponseStatus( code = HttpStatus.OK )
    public ProductModel updateProduct(
        final @Valid
        @PathVariable( value = "id" )
        long productId,
        final @Valid @RequestBody ProductModel productModel
    )
    {
        Product product;
        try {
            product = productService.getProductById( productId );
        } catch( ProductNotFoundException ex ) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Product Not Found", ex );
        }
        if( !StringUtils.isEmpty( productModel.getName() ) ) {
            product.setName( productModel.getName() );
        }
        if( productModel.getCurrentPrice() != null ) {
            product.setCurrentPrice( productModel.getCurrentPrice() );
        }
        product = productService.updateProduct( product );
        productModel.setId( product.getId() );
        return productModel;
    }
}
