package com.hyperativa.payment.securecard.infrastructure.configuration.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebMvc
@ConditionalOnProperty(value = "true", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig implements WebMvcConfigurer {

    @Value("${security.jwt.auth.header}")
    private String jwtAuthHeader;  

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${application.doc}")
    private String applicationDoc;

    @Value("${application.url}")
    private String applicationUrl;  

    static final String JWT_DESC="JWT Authentication Header";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(applicationName)
                        .version(applicationVersion)
                        .description(applicationDoc))
                .addSecurityItem(new SecurityRequirement().addList(jwtAuthHeader)) 
                .components(new Components()
                        .addSecuritySchemes(jwtAuthHeader,
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(jwtAuthHeader) 
                                        .description(JWT_DESC)));  
    }

   

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(applicationVersion)
                .setViewName(applicationUrl);
    }
}