package com.shopping.api;

import com.shopping.api.controllers.OrderController;
import com.shopping.api.entities.Buyer;
import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;
import com.shopping.api.entities.Product;
import com.shopping.api.services.BuyerService;
import com.shopping.api.services.OrderService;
import com.shopping.api.services.ProductService;

import static org.hamcrest.core.StringContains.containsString;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest @AutoConfigureMockMvc
public class OrderTests
{
    private final OrderController orderController;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ProductService productService;
    @MockBean
    private BuyerService buyerService;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup( orderController ).build();
    }

    public OrderTests()
    {
        orderController = new OrderController( orderService, buyerService, productService );
    }

    @Test
    public void testOrdersInaRangeSuccess() throws Exception
    {
        Order order = new Order();
        String email = "denizyanik@gmail.com";
        order.setBuyer( new Buyer( 1L, email, null ) );
        order.setOrderDate( LocalDate.now() );
        List<Order> orders = new ArrayList<>();
        orders.add( order );
        Product product1 = new Product( 1, "product 1", 100 );
        Product product2 = new Product( 2, "product 2", 200 );
        Set<OrderDetails> details = new HashSet<>();
        details.add( new OrderDetails( product1, order, 1 ) );
        details.add( new OrderDetails( product2, order, 2 ) );
        order.setOrderDetails( details );

        Mockito.doReturn( orders ).when( orderService ).getOrdersInRange(
            LocalDate.of( 2020, 10, 10 ),
            LocalDate.of( 2020, 10, 20 )
        );
        mockMvc.perform(
            MockMvcRequestBuilders.get( "/orders" ).contentType( MediaType.APPLICATION_JSON )
            /**/ .param( "startDate", "2020-10-10" )
            /**/ .param( "endDate", "2020-10-20" )
        ) /**/.andExpect( MockMvcResultMatchers.status().is( HttpStatus.OK.value() ) )
        /**/ .andExpect( MockMvcResultMatchers.content().string( containsString( "\"totalValue\":500" ) ) );
    }

    @Test
    public void testOrderEmptyResult() throws Exception
    {
        Mockito.doReturn( new ArrayList<Order>() ).when( orderService ).getOrdersInRange(
            LocalDate.of( 2020, 10, 10 ),
            LocalDate.of( 2020, 10, 20 )
        );
        mockMvc.perform(
            MockMvcRequestBuilders.get( "/orders/1" ).contentType( MediaType.APPLICATION_JSON )
            /**/ .param( "startDate", "2020-10-10" )
            /**/ .param( "endDate", "2020-10-20" )
        ).andExpect( MockMvcResultMatchers.status().is( HttpStatus.NOT_FOUND.value() ) );
    }
}
