package com.example.desafiobackvotos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class SpringFoxConfig {

    private static final String SWAGGER_CONFIG = "/v3/api-docs/swagger-config";
    private static final String SWAGGER_URL = "/v3/api-docs";
    private static final List<String> CONTROLLER_PACKAGES =
            List.of("com.example.desafiobackvotos.controller");

    @Bean
    @Primary
    public SpringDocConfigProperties springDocConfigProperties(SpringDocConfigProperties config){
        config.setPackagesToScan(CONTROLLER_PACKAGES);
        return config;
    }

    @Bean
    @Primary
    public SwaggerUiConfigProperties swaggerUiConfigProperties(SwaggerUiConfigProperties config){
        config.setConfigUrl(SWAGGER_CONFIG);
        config.setUrl(SWAGGER_URL);
        return config;
    }

    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("Desafio Técnico - votos")
                        .description("API para contagem de votos")
                        .contact(new Contact().name("Diego Matté")
                                .email("diegocmatte@gmail.com")
                                .url("https://github.com/diegocmatte")
                        )
                );
    }
}
