package au.com.sm.api.mailer.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("au.com.sm"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfo(
                "SiteMinder Coding Challenge - REST API",
                "API to comply with the coding task related to SiteMinder job application",
                "1.0.0",
                "Terms of Service",
                new Contact("LinkedIn Profile", "https://www.linkedin.com/in/albertsaclot", "albert.saclot@gmail.com"),
                ApiInfo.DEFAULT.getLicense(),
                ApiInfo.DEFAULT.getLicenseUrl(),
                Collections.emptyList());
    }
}
