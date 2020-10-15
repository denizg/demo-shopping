package com.shopping.api.services.impl;

import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;
import com.shopping.api.repositories.OrderDetailsRepository;
import com.shopping.api.repositories.OrderRepository;
import com.shopping.api.repositories.ProductRepository;
import com.shopping.api.services.OrderService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderServiceImpl(
        OrderRepository orderRepository,
        ProductRepository productRepository,
        OrderDetailsRepository orderDetailsRepository
    )
    {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<Order> getOrdersInRange( LocalDate startDate, LocalDate endDate )
    {
        List<Order> orders = orderRepository.findByOrderDateBetween( startDate, endDate );
        return orders;
    }

    @Override @Transactional
    public Order createOrder( Order newOrder )
    {
        Order order = orderRepository.save( newOrder );
        order.getOrderDetails().forEach( this::createOrderDetails );
        return order;
    }

    @Override
    public List<Order> getOrders()
    {
        return orderRepository.findAll();
    }

    @Override
    public OrderDetails createOrderDetails( OrderDetails orderDetails )
    {
        return orderDetailsRepository.save( orderDetails );
    }

    @Override
    public List<OrderDetails> getOrdersDetails( Long orderId )
    {
        Optional<Order> optionalOrder = orderRepository.findById( orderId );
        Order order = optionalOrder.orElseThrow( NullPointerException::new );
        return orderDetailsRepository.findByOrder( order );
    }

}
