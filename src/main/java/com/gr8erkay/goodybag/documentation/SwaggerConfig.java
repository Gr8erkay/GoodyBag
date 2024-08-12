package com.gr8erkay.goodybag.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    // Get the Dependencies, setup docs

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Goodybag Application API")
                        .description("Goodybag is a platform that connects HouseOwners and Tenants. " +
                                "HouseOwners are users who have an apartment or a space they need to get occupied eg; " +
                                "service apartment, residential apartment, commercial outlets etc. While tenants are users who sign " +
                                "up to take up these rent and occupy them for short or long terms. ")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .email("gr8erkay@gmail.com")
                                .name("LIVE PROJECT")
                                .url("https://github.com/gr8erkay/goodyBag")
                        )
                );



    }



}