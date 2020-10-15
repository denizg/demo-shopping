package com.shopping.api.services;

import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;

import java.time.LocalDate;
import java.util.List;

public interface OrderService
{
    List<Order> getOrdersInRange( LocalDate startDate, LocalDate finishDate );

    Order createOrder( Order newOrder );

    List<Order> getOrders();

    OrderDetails createOrderDetails( OrderDetails orderDetails );

    List<OrderDetails> getOrdersDetails( Long orderId );
}
