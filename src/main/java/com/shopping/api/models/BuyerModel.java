package com.shopping.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.shopping.api.entities.Buyer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.lang.Nullable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BuyerModel
{
    @JsonInclude( JsonInclude.Include.NON_NULL )
    @Nullable
    private Long id;
    private String email;

    public Buyer convertToEntity()
    {
        Buyer buyer = new Buyer();
        buyer.setEmail( email );
        return buyer;
    }

    public static BuyerModel convertToModel( Buyer buyer )
    {
        return new BuyerModel( buyer.getId(), buyer.getEmail() );
    }
}
