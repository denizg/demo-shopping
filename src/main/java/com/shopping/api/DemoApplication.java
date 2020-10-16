package com.shopping.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication @EnableSwagger2
public class DemoApplication
{

    public static void main( String[] args )
    {
        System.setProperty( "server.servlet.context-path", "/shopping-api" );
        SpringApplication.run( DemoApplication.class, args );
    }

    @Bean
    public Docket api()
    {
        return
            new Docket( DocumentationType.SWAGGER_2 ).select()
            /**/ .apis( RequestHandlerSelectors.basePackage( "com.shopping.api.controllers" ) )
            /**/ .paths( PathSelectors.any() ).build();
    }

    private ApiInfo apiEndPointsInfo()
    {
        return
            new ApiInfoBuilder()
            /**/ .title( "Shopping Application REST API" )
            /**/ .description( "Shopping REST API that includes Product, Buyer and Order operations" )
            /**/ .contact(
                new Contact( "Banu Deniz YANIK GULER", "https://github.com/denizg/demo-shopping", "denizyanik@gmail.com" )
            ).build();
    }

}
