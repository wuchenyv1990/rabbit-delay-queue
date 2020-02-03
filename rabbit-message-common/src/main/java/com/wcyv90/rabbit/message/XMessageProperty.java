package com.wcyv90.rabbit.message;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.wcyv90.rabbit.message.XMessageProperty.PREFIX;

@ConfigurationProperties(prefix = PREFIX)
@Data
public class XMessageProperty {

    public static final String PREFIX = "x-common";

    private boolean enableJacksonConfig = true;

    private boolean enableXMessage = true;

    private Swagger swagger;

    @Data
    public static class Swagger {

        private boolean enableSwaggerUi = false;

        private String basePackage;

        private String apiTitle;

        private String apiVersion;

        private String contactName;

        private String contactUrl;

        private String contactEmail;

    }

}
