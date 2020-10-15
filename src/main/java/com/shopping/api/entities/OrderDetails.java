package com.shopping.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table( name = "order_product" )
@Getter @Setter @NoArgsConstructor
public class OrderDetails implements Serializable
{
    @EmbeddedId
    @RestResource( exported = false )
    private OrderDetailsId orderDetailsId;

    @ManyToOne
    @MapsId( "orderId" )
    @JoinColumn( name = "order_id" )
    @JsonIgnore
    private Order order;

    @ManyToOne
    @MapsId( "productId" )
    @JoinColumn( name = "product_id" )
    @JsonIgnore
    private Product product;

    @Column( name = "order_price" )
    private int orderPrice;

    @Column( name = "product_amount" )
    private int productAmount = 1;

    public OrderDetails( Product product, Order order, int productAmount )
    {
        orderDetailsId = new OrderDetailsId( order.getId(), product.getId() );
        this.order = order;
        this.product = product;
        this.orderPrice = this.product.getCurrentPrice();
        this.productAmount = productAmount;
    }

    @Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class OrderDetailsId implements Serializable
    {
        private long orderId;
        private long productId;

        @Override
        public boolean equals( Object o )
        {
            if( this == o )
                return true;
            if( o == null || getClass() != o.getClass() )
                return false;
            OrderDetailsId orderDetailsId = ( OrderDetailsId )o;
            return Objects.equals( orderId, orderDetailsId.orderId ) &&
                Objects.equals( productId, orderDetailsId.productId );
        }

        @Override
        public int hashCode()
        {
            return Objects.hash( orderId, productId );
        }
    }
}
