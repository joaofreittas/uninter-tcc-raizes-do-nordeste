package com.uninter.raizesdonordeste.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.uninter.raizesdonordeste.common.AuthConstants.API_TITLE;
import static com.uninter.raizesdonordeste.common.AuthConstants.AUTH_DESCRIPTION;
import static com.uninter.raizesdonordeste.common.AuthConstants.BEARER_AUTH;
import static com.uninter.raizesdonordeste.common.AuthConstants.BEARER_FORMAT;
import static com.uninter.raizesdonordeste.common.AuthConstants.BEARER_SCHEME;
import static com.uninter.raizesdonordeste.common.AuthConstants.CONTACT_EMAIL;
import static com.uninter.raizesdonordeste.common.AuthConstants.CONTACT_NAME;
import static com.uninter.raizesdonordeste.common.AuthConstants.PROJECT_DESCRIPTION;
import static com.uninter.raizesdonordeste.common.AuthConstants.PROJECT_VERSION;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(API_TITLE)
                .description(PROJECT_DESCRIPTION)
                .version(PROJECT_VERSION)
                .contact(new Contact()
                    .name(CONTACT_NAME)
                    .email(CONTACT_EMAIL)))
            .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
            .components(new Components()
                .addSecuritySchemes(BEARER_AUTH,
                    new SecurityScheme()
                        .name(BEARER_AUTH)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_SCHEME)
                        .bearerFormat(BEARER_FORMAT)
                        .description(AUTH_DESCRIPTION)));
    }

}
