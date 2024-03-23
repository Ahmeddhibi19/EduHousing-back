package com.PFA2.EduHousing.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Dhibi",
                        email = "adhibi345@gmail.com",
                        url = ""
                ),
                description = "OpenApi documentation for Eduhousing",
                title = "OpenApi specification - Dhibi",
                version = "1.0",
                license = @License(
                        name = "EduHousing license"
                )
        ),
        servers = {
                @Server(
                        description = "local",
                        url = "http://localhost:8081"
                )
        }
)
@Configuration
public class OpenApiConfig {
   /* @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("SpringShop API")
                        .description("Spring shop sample application")
                        .version("v3.1.0")
                        .license(new io.swagger.v3.oas.models.info.License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }*/
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("eduhousing-public")
                .pathsToMatch("/api"+APP_ROOT+"/**")
                .packagesToScan("com.PFA2.EduHousing.controller")
                .build();
    }
}
