package com.shopping.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "product" )
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( name = "name" )
    private String name;

    @Column( name = "current_price" )
    private int currentPrice;
}
