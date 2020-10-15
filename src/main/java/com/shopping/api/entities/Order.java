package com.shopping.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table( name = "shop_order" )
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class Order
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column(
        name = "order_date",
        nullable = false
    )
    @CreatedDate
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate orderDate;

    @OneToMany( mappedBy = "order" )
    private Set<OrderDetails> orderDetails;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(
        name = "buyer_id",
        referencedColumnName = "id"
    )
    private Buyer buyer;

    @ManyToMany( cascade = CascadeType.ALL )
    @JoinTable(
        name = "order_product",
        joinColumns =
            @JoinColumn(
                referencedColumnName = "id",
                name = "order_id"
            ),
        inverseJoinColumns =
            @JoinColumn(
                referencedColumnName = "id",
                name = "product_id"
            )
    )
    private Set<Product> products;

    @JsonIgnore @Transient
    private int totalValue;

    public int getTotalValue()
    {
        return
            orderDetails.stream().mapToInt(
                /**/ orderDetails -> orderDetails.getProductAmount() * orderDetails.getOrderPrice()
            ).sum();
    }

    @PrePersist
    protected void onCreate()
    {
        orderDate = LocalDate.now();
    }
}
