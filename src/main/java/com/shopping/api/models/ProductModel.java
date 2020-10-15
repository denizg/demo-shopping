package com.shopping.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.shopping.api.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.lang.Nullable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProductModel
{
    @JsonInclude( JsonInclude.Include.NON_NULL )
    @Nullable
    private Long id;
    private String name;
    private Integer currentPrice;

    public Product convertToEntity()
    {
        Product product = new Product();
        product.setName( name );
        product.setCurrentPrice( currentPrice );
        return product;
    }

    public static ProductModel convertToModel( Product product )
    {
        return new ProductModel( product.getId(), product.getName(), product.getCurrentPrice() );
    }
}
