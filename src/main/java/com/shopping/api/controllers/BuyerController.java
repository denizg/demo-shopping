package com.shopping.api.controllers;

import com.shopping.api.entities.Buyer;
import com.shopping.api.models.BuyerModel;
import com.shopping.api.services.BuyerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping( BuyerController.ENDPOINT )
@Api(
    produces = MediaType.APPLICATION_JSON_VALUE,
    tags = "Buyer"
)
@RequiredArgsConstructor
public class BuyerController
{
    public static final String ENDPOINT = "/buyers";
    public static final String ENDPOINT_ID = "/{id}";
    private final BuyerService buyerService;

    @ApiOperation( "Get all Buyers" )
    @GetMapping
    public List<BuyerModel> getBuyers()
    {
        return buyerService.getBuyers().stream()
            /**/ .map( BuyerModel::convertToModel ).collect( Collectors.toList() );
    }

    @ApiOperation( "Get a Buyer" )
    @GetMapping( ENDPOINT_ID )
    @ResponseStatus( code = HttpStatus.OK )
    public BuyerModel getBuyer( final @Valid
        @PathVariable( value = "id" )
        long buyerId
    )
    {
        return BuyerModel.convertToModel( buyerService.getBuyerById( buyerId ) );
    }

    @ApiOperation( "Create a Buyer" )
    @PostMapping
    @ResponseStatus( code = HttpStatus.CREATED )
    public BuyerModel createBuyer( final @RequestBody BuyerModel newBuyer )
    {
        Buyer buyer = buyerService.createBuyer( newBuyer.convertToEntity() );
        newBuyer.setId( buyer.getId() );
        return newBuyer;
    }

}
