package com.jmarcos.demoparkapi.config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest API - Spring Park")
                                .description("Api para gestão de estacionamento de veículos")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Jose Marcos").email("jose.marcos.efi@gmail.com"))
                );
    }
   private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira um bearer token valido para prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");

        }

}
