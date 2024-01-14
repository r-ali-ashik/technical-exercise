package co.uk.apg.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //required openApi bean for swagger documentation
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Technical Exercise")
                        .description("Technical Exercise - Event API")
                        .version("1.0")
                        .termsOfService("www.example.com/terms")
                        .license(new License().name("MIT License").url("https://opensource.org/license/mit/")));
    }
}
