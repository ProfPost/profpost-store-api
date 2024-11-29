package com.Profpost.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

// http://localhost:8080/api/v1/swagger-ui/index.html
@Configuration
public class SwaggerAPIConfig {

    @Value("${profpost.openapi.dev-url}")
    private String devUrl;

    @Value("${profpost.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myopenAPI() {
        //Servidor de desarrollo
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        //Servidor de produccion
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        //Informacion de contacto
        Contact contact = new Contact();
        contact.setName("Profpost");
        contact.setUrl("https://profpost.github.io/profpost-landing-page/"); //landingpage

        //Licencia
        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        //Informacion general de la API
        Info info = new Info()
                .title("API  de Plataforma de Publicaciones - Profpost")
                .version("1.0")
                .contact(contact)
                .description("Esta API expone endpoints para la monetización y gestión de publicaciones")
                .termsOfService("https://www.profpost.com/terms")
                .license(mitLicense);

        // Configuracion de seguridad JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication");

        Components components = new Components()
                .addSecuritySchemes("bearerAuth", securityScheme);

        // Requerimiento de seguridad para utilizar en las operaciones
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
