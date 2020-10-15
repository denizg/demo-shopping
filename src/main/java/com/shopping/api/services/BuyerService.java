package com.shopping.api.services;

import com.shopping.api.entities.Buyer;

import java.util.List;

import javax.validation.Valid;

public interface BuyerService
{
    Buyer createBuyer( @Valid Buyer newBuyer );

    List<Buyer> getBuyers();

    Buyer getBuyerById( long buyerId );
}
