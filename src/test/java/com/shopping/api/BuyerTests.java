package com.shopping.api;

import com.shopping.api.controllers.BuyerController;
import com.shopping.api.entities.Buyer;
import com.shopping.api.exceptions.BuyerNotFoundException;
import com.shopping.api.services.BuyerService;

import static org.hamcrest.core.StringContains.containsString;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest @AutoConfigureMockMvc
public class BuyerTests
{
    private final BuyerController buyerController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerService buyerService;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup( buyerController ).build();
    }

    public BuyerTests()
    {
        buyerController = new BuyerController( buyerService );
    }

    @Test
    public void testBuyerGetSuccess() throws Exception
    {
        String email = "denizyanik@gmail.com";
        Buyer buyer = new Buyer();
        buyer.setId( 1 );
        buyer.setEmail( email );
        Mockito.doReturn( buyer ).when( buyerService ).getBuyerById( 1L );
        mockMvc.perform( MockMvcRequestBuilders.get( "/buyers/1" ) ).andExpect(
            MockMvcResultMatchers.status().is( HttpStatus.OK.value() )
        ).andExpect( MockMvcResultMatchers.content().string( containsString( email ) ) );
    }

    @Test
    public void testProductGetError() throws Exception
    {
        Mockito.doThrow( new BuyerNotFoundException() ).when( buyerService ).getBuyerById( 1L );
        mockMvc.perform( MockMvcRequestBuilders.get( "/buyers/1" ) ).andExpect(
            MockMvcResultMatchers.status().is( HttpStatus.NOT_FOUND.value() )
        );
    }

}
