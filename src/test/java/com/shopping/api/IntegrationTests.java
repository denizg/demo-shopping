package com.shopping.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.shopping.api.entities.Buyer;
import com.shopping.api.entities.Order;
import com.shopping.api.entities.OrderDetails;
import com.shopping.api.entities.Product;
import com.shopping.api.services.BuyerService;
import com.shopping.api.services.OrderService;
import com.shopping.api.services.ProductService;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest @AutoConfigureMockMvc
public class IntegrationTests
{
    public static final String ORDER_ENDPOINT = "/orders";
    public static final String PRODUCT_ENDPOINT = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private BuyerService buyerService;

    @MockBean
    private OrderService orderService;

    @Test
    public void testProductCreation() throws Exception
    {
        Buyer buyer = new Buyer();
        buyer.setEmail( "denizyanik@gmail.com" );
        Product product = new Product();
        product.setCurrentPrice( 100 );
        product.setName( "new product" );

        this.mockMvc.perform(
            MockMvcRequestBuilders
            /**/ .post( PRODUCT_ENDPOINT )
            /**/ .content( new ObjectMapper().writeValueAsString( product ) )
            /**/ .contentType( MediaType.APPLICATION_JSON )
            /**/ .accept( MediaType.APPLICATION_JSON )
        ).andExpect( status().isCreated() );
    }

    @Test
    public void testProductUpdate() throws Exception
    {
        Product product = new Product();
        product.setId( 1 );
        product.setCurrentPrice( 100 );
        product.setName( "new product" );
        productService.createProduct( product );

        this.mockMvc.perform(
            MockMvcRequestBuilders
            /**/ .post( PRODUCT_ENDPOINT )
            /**/ .content( new ObjectMapper().writeValueAsString( product ) )
            /**/ .contentType( MediaType.APPLICATION_JSON )
            /**/ .accept( MediaType.APPLICATION_JSON )
        ).andExpect( status().isCreated() ).andReturn();
        product.setName( "updated product" );
        MvcResult mvcResult =
            this.mockMvc.perform(
                MockMvcRequestBuilders
                /**/ .put( PRODUCT_ENDPOINT + "/1" )
                /**/ .content( new ObjectMapper().writeValueAsString( product ) )
                /**/ .contentType( MediaType.APPLICATION_JSON )
                /**/ .accept( MediaType.APPLICATION_JSON )
            ).andExpect( status().isOk() ).andReturn();

        assertTrue( mvcResult.getResponse().getContentAsString().contains( "updated product" ) );
    }

    @Test
    public void testOrderCreation() throws Exception
    {
        Buyer buyer = new Buyer();
        buyer.setEmail( "denizyanik@gmail.com" );
        Order order = new Order();
        order.setBuyer( buyer );

        this.mockMvc.perform(
            MockMvcRequestBuilders
            /**/ .post( ORDER_ENDPOINT )
            /**/ .content( new ObjectMapper().writeValueAsString( order ) )
            /**/ .contentType( MediaType.APPLICATION_JSON )
            /**/ .accept( MediaType.APPLICATION_JSON )
        ).andExpect( status().isCreated() );
    }

    @Test
    public void testGetOrdersInARange() throws Exception
    {
        Buyer buyer = new Buyer();
        buyer.setEmail( "denizyanik@gmail.com" );
        buyerService.createBuyer( buyer );
        Product product = new Product( 1, "new product", 100 );
        productService.createProduct( product );
        Product product2 = new Product( 2, "new product2", 100 );
        productService.createProduct( product2 );

        Order order = new Order();
        order.setBuyer( buyer );
        OrderDetails orderDetails = new OrderDetails( product, order, 1 );
        OrderDetails orderDetails2 = new OrderDetails( product2, order, 2 );

        Set<OrderDetails> details = new HashSet<>();
        details.add( orderDetails );
        details.add( orderDetails2 );
        order.setOrderDetails( details );
        orderService.createOrder( order );

        String startDate = "2020-10-10";
        String endDate = "2020-10-14";
        MvcResult mvcResult =
            this.mockMvc.perform(
                MockMvcRequestBuilders
                /**/ .get( ORDER_ENDPOINT )
                /**/ .param( "startDate", startDate )
                /**/ .param( "endDate", endDate )
                /**/ .accept( MediaType.APPLICATION_JSON_VALUE )
            ).andExpect( status().isOk() ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertTrue( content.contains( "\"totalElements\" : 1" ) );
    }
}
