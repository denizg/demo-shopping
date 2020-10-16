package com.shopping.api.controllers;

import com.shopping.api.entities.Buyer;
import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;
import com.shopping.api.entities.Product;
import com.shopping.api.exceptions.ProductNotFoundException;
import com.shopping.api.models.BuyerModel;
import com.shopping.api.models.OrderModel;
import com.shopping.api.services.BuyerService;
import com.shopping.api.services.OrderService;
import com.shopping.api.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping(
    value = OrderController.ENDPOINT,
    consumes = MediaType.APPLICATION_JSON_VALUE
)
@Api(
    produces = MediaType.APPLICATION_JSON_VALUE,
    tags = "Order"
)
@RequiredArgsConstructor
public class OrderController
{
    public static final String ENDPOINT = "/orders";
    private final OrderService orderService;
    private final BuyerService buyerService;
    private final ProductService productService;

    @ApiOperation( "Get all Orders between startDate and finishDate with ISO Date Format {@code yyyy-MM-dd}" )
    @GetMapping
    public List<OrderModel> getOrdersInaRange(
        final @Valid
        @RequestParam( "startDate" )
        @DateTimeFormat( iso = DateTimeFormat.ISO.DATE )
        LocalDate startDate,
        final @Valid
        @RequestParam( "endDate" )
        @DateTimeFormat( iso = DateTimeFormat.ISO.DATE )
        LocalDate endDate
    )
    {
        List<Order> ordersInRange = orderService.getOrdersInRange( startDate, endDate );
        return
            ordersInRange.stream().map(
                order -> {
                    OrderModel orderModel = new OrderModel();
                    orderModel.setBuyer( BuyerModel.convertToModel( order.getBuyer() ) );
                    orderModel.setId( order.getId() );
                    orderModel.setOrderDate( order.getOrderDate() );
                    orderModel.setTotalValue( order.getTotalValue() );
                    orderModel.setProductAmounts(
                        order.getOrderDetails().stream().collect(
                            Collectors.toMap(
                                orderDetails -> orderDetails.getProduct().getId(),
                                OrderDetails::getProductAmount
                            )
                        )
                    );
                    return orderModel;
                }
            ).collect( Collectors.toList() );
    }

    @ApiOperation( "Create an Order" )
    @PostMapping
    @ResponseStatus( code = HttpStatus.CREATED )
    public OrderModel createOrder( final @RequestBody OrderModel newOrder )
    {

        BuyerModel buyerModel = newOrder.getBuyer();
        if( buyerModel == null ) {
            throw new ResponseStatusException(
                HttpStatus.PRECONDITION_FAILED,
                "BuyerModel cannot be null.",
                new NullPointerException()
            );
        }

        Buyer buyer = buyerService.getBuyerById( buyerModel.getId() );
        Order order = new Order();
        order.setBuyer( buyer );
        Set<OrderDetails> details = new HashSet<>();
        Map<Long, Integer> productAmounts = newOrder.getProductAmounts();
        if( productAmounts == null || productAmounts.isEmpty() ) {
            throw new ResponseStatusException(
                HttpStatus.PRECONDITION_FAILED,
                "Product Amounts cannot be null or empty.",
                new NullPointerException()
            );
        }

        productAmounts.forEach(
            ( productModelId, productAmount) -> {
                Product product;
                try {
                    product = productService.getProductById( productModelId );
                } catch( ProductNotFoundException ex ) {
                    throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Product Not Found", new NullPointerException() );
                }
                OrderDetails orderDetails = new OrderDetails( product, order, productAmount );
                orderDetails.setProductAmount( productAmount );
                orderDetails.setOrderPrice( product.getCurrentPrice() );
                details.add( orderDetails );
            }
        );
        order.setOrderDetails( details );
        return OrderModel.convertToModel( orderService.createOrder( order ) );
    }
}
