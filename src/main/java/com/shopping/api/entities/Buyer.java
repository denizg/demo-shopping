package com.shopping.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "buyer" )
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Buyer
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( name = "email" )
    private String email;

    @OneToMany(
        mappedBy = "buyer",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<Order> orders;
}
