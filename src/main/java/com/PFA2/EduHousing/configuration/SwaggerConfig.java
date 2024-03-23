package com.PFA2.EduHousing.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
/*import springfox.documentation.builders.PathSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;*/

import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.SpringWebProvider;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;


@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "EduHousing API", version = "3.1.0", description = "API for EduHousing application"))
public class SwaggerConfig {
    /*@Bean
    public SwaggerWelcomeCommon swaggerWelcomeCommon(
            SwaggerUiConfigProperties swaggerUiConfigProperties,
            SpringDocConfigProperties springDocConfigProperties,
            SwaggerUiConfigParameters swaggerUiConfigParameters,
            SpringWebProvider springWebProvider) {
        return new SwaggerWelcomeWebMvc(
                swaggerUiConfigProperties,
                springDocConfigProperties,
                swaggerUiConfigParameters,
                springWebProvider);
    }*/
    @Bean
    @Primary
    public SpringDocConfiguration springDocConfiguration() {
        return new SpringDocConfiguration();
    }


  /*  @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SpringShop API")
                        .description("Spring shop sample application")
                        .version("v3.1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch("/api"+APP_ROOT+"/*")
                .packagesToScan("com.PFA2.EduHousing")
                .build();
    }*/





}
