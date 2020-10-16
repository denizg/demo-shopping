package com.shopping.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.shopping.api.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderModel
{
    private BuyerModel buyer;
    private Map<Long, Integer> productAmounts;

    @JsonInclude( JsonInclude.Include.NON_NULL )
    @Nullable
    private long id;

    @JsonInclude( JsonInclude.Include.NON_NULL )
    @Nullable
    private Integer totalValue;

    @JsonInclude( JsonInclude.Include.NON_NULL )
    @Nullable
    private LocalDate orderDate;

    public static OrderModel convertToModel( Order order )
    {
        OrderModel orderModel = new OrderModel();
        orderModel.setId( order.getId() );
        orderModel.setOrderDate( order.getOrderDate() );
        orderModel.setTotalValue( order.getTotalValue() );
        orderModel.setBuyer( BuyerModel.convertToModel( order.getBuyer() ) );
        return orderModel;
    }
}
