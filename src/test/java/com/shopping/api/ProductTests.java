package com.shopping.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.shopping.api.controllers.ProductController;
import com.shopping.api.entities.Product;
import com.shopping.api.exceptions.ProductNotFoundException;
import com.shopping.api.models.ProductModel;
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

@SpringBootTest @AutoConfigureMockMvc
public class ProductTests
{
    private final ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup( productController ).build();
    }

    public ProductTests()
    {
        productController = new ProductController( productService );
    }

    @Test
    public void testProductGetSuccess() throws Exception
    {
        String productName = "fancy product";
        int currentPrice = 100;
        Product product = new Product( 1L, productName, currentPrice );
        Mockito.doReturn( product ).when( productService ).getProductById( 1L );
        mockMvc.perform( MockMvcRequestBuilders.get( "/products/1" ) ).andExpect(
            MockMvcResultMatchers.status().is( HttpStatus.OK.value() )
        ).andExpect( MockMvcResultMatchers.content().string( containsString( productName ) ) );
    }

    @Test
    public void testProductGetError() throws Exception
    {
        Mockito.doThrow( new ProductNotFoundException() ).when( productService ).getProductById( 1L );
        mockMvc.perform( MockMvcRequestBuilders.get( "/products/1" ) ).andExpect(
            MockMvcResultMatchers.status().is( HttpStatus.NOT_FOUND.value() )
        );
    }

    @Test
    public void testProductPutSuccess() throws Exception
    {
        String productName = "fancy product";
        int currentPrice = 100;
        Product product = new Product( 1L, productName, currentPrice );
        Mockito.doReturn( product ).when( productService ).getProductById( 1L );
        Mockito.doReturn( product ).when( productService ).updateProduct( product );
        ProductModel model = ProductModel.convertToModel( product );
        String productModel = new ObjectMapper().writeValueAsString( model );

        mockMvc.perform(
            MockMvcRequestBuilders.put( "/products/1" ).contentType( MediaType.APPLICATION_JSON ).content( productModel )
            .accept( MediaType.APPLICATION_JSON )
        ).andExpect( MockMvcResultMatchers.status().is( HttpStatus.OK.value() ) ).andExpect(
            MockMvcResultMatchers.content().string( containsString( productName ) )
        );
    }
}
