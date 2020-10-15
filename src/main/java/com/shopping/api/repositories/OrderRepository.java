package com.shopping.api.repositories;

import com.shopping.api.entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByOrderDateBetween( LocalDate startDate, LocalDate endDate );
}
