package com.shopping.api.services.impl;

import com.shopping.api.entities.Buyer;
import com.shopping.api.exceptions.BuyerNotFoundException;
import com.shopping.api.repositories.BuyerRepository;
import com.shopping.api.services.BuyerService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService
{
    private BuyerRepository buyerRepository;

    @Autowired
    public BuyerServiceImpl( BuyerRepository buyerRepository )
    {
        this.buyerRepository = buyerRepository;
    }

    @Override
    public Buyer createBuyer( Buyer newBuyer )
    {
        return buyerRepository.save( newBuyer );
    }

    @Override
    public List<Buyer> getBuyers()
    {
        return buyerRepository.findAll();
    }

    @Override
    public Buyer getBuyerById( long buyerId )
    {
        return buyerRepository.findById( buyerId ).orElseThrow( BuyerNotFoundException::new );
    }
}
