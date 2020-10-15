package com.shopping.api.repositories;

import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetails.OrderDetailsId>
{
    List<OrderDetails> findByOrder( Order order );
}
