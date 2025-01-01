package com.shekhawat.journalApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {
        return new OpenAPI()
                .info(new Info().title("Journal App APIs").description("By Jaivardhan"))
                .servers(List.of(new Server().url("http://localhost:8080").description("local"),
                        new Server().url("https://rocky-inlet-63478-2514cf114d43.herokuapp.com").description("prod")));
    }
}
