package com.wcyv90.rabbit.message;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnClass(Docket.class)
@ConditionalOnProperty(prefix = XMessageProperty.PREFIX, name = "swagger.enable-swagger-ui")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestfulApi(XMessageProperty property) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(property.getSwagger()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(property.getSwagger().getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(XMessageProperty.Swagger swagger) {
        Contact contact = new Contact(swagger.getContactName(), swagger.getContactUrl(), swagger.getContactEmail());
        return new ApiInfoBuilder()
                .title(swagger.getApiTitle())
                .description("前台API接口")
                .contact(contact)
                .version(swagger.getApiVersion())
                .build();
    }

}
