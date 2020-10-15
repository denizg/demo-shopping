package com.shopping.api;

import com.shopping.api.entities.Buyer;
import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;
import com.shopping.api.entities.Product;
import com.shopping.api.repositories.BuyerRepository;
import com.shopping.api.repositories.OrderDetailsRepository;
import com.shopping.api.repositories.OrderRepository;
import com.shopping.api.repositories.ProductRepository;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional @Rollback
@RunWith( SpringRunner.class )
@SpringBootTest
public class OrderDetailsUnitTests
{
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void createObjects()
    {
        Buyer buyer = new Buyer( 1, "denizyanik@gmail.com", null );
        buyer = buyerRepository.saveAndFlush( buyer );
        entityManager.detach( buyer );

        Product product1 = new Product( 1, "fancy product 1", 25 );
        product1 = productRepository.saveAndFlush( product1 );
        entityManager.detach( product1 );

        Product product2 = new Product( 2, "fancy product 2", 60 );
        product2 = productRepository.saveAndFlush( product2 );
        entityManager.detach( product2 );

        Order order = new Order();
        order.setBuyer( buyer );
        order = orderRepository.saveAndFlush( order );
        entityManager.detach( order );

        OrderDetails details1 = new OrderDetails( product1, order, 1 );
        orderDetailsRepository.saveAndFlush( details1 );
        entityManager.detach( details1 );

        OrderDetails details2 = new OrderDetails( product2, order, 2 );
        orderDetailsRepository.saveAndFlush( details2 );
        entityManager.detach( details2 );
    }

    @Test
    public void testRelationsForOrderDetails()
    {
        List<Order> allOrders = orderRepository.findAll();
        List<OrderDetails> allOrderDetails = orderDetailsRepository.findAll();
        assertEquals( 2, allOrderDetails.size() );
        assertEquals( 2, allOrders.get( 0 ).getOrderDetails().size() );
    }

    @Test
    public void testTotalPrice()
    {
        Optional<Product> optionalProduct1 = productRepository.findById( 1L );
        Product product1 = optionalProduct1.orElseThrow( NullPointerException::new );
        assertEquals( 25, product1.getCurrentPrice() );

        product1.setCurrentPrice( 250 );
        productRepository.saveAndFlush( product1 );
        entityManager.detach( product1 );
        assertEquals( 250, product1.getCurrentPrice() );

        Optional<Order> optionalOrder = orderRepository.findById( 1L );
        Order order = optionalOrder.orElseThrow( NullPointerException::new );
        assertEquals( 145, order.getTotalValue() );
    }
}
