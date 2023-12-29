package com.claro.amx.sp.dataconsumptionservice.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static com.claro.amx.sp.dataconsumptionservice.constants.Constants.*;

@Configuration
@PropertySource(value = "${springdoc.config}", ignoreResourceNotFound = true)
public class SpringDocConfig {

    @Value("${springdoc.info.nameContact}")
    private String nameContact;
    @Value("${springdoc.info.mailContact}")
    private String mailContact;
    @Value("${springdoc.info.urlContact}")
    private String urlContact;
    @Value("${springdoc.info.title}")
    private String title;
    @Value("${springdoc.info.description}")
    private String description;
    @Value("${springdoc.info.version}")
    private String version;
    @Value("${springdoc.info.urlConfluence}")
    private String urlConfluence;
    @Value("${springdoc.enabledGlobalHeaders}")
    private String enabledGlobalHeaders;
    @Value("${springdoc.enabledServerHttps}")
    private String enabledServerHttps;

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        if (isEnabled(this.enabledGlobalHeaders))
            return openApi -> {
                openApi.getPaths().values().stream()
                        .flatMap(pathItem -> pathItem.readOperations().stream())
                        .forEach(operation -> operation.addParametersItem(new Parameter().in(ParameterIn.HEADER.toString())
                                        .schema(new StringSchema()).name(SESSION_NAME).required(true).description(SESSION_DESCR))
                                .addParametersItem(new Parameter().in(ParameterIn.HEADER.toString())
                                        .schema(new StringSchema()).name(CHANNEL_NAME).required(true).description(CHANNEL_DESCR))
                                .addParametersItem(new Parameter().in(ParameterIn.HEADER.toString())
                                        .schema(new StringSchema()).name(USER_NAME).required(true).description(USER_DESCR)));
                openApi.getServers().forEach(x -> {
                    if (isEnabled(this.enabledServerHttps))
                        x.setUrl(x.getUrl().replace("http", "https"));
                    x.setDescription(null);
                });
            };
        return openApi -> {
        };
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact()
                                .name(nameContact)
                                .email(mailContact)
                                .url(urlContact)
                        ))
                .externalDocs(externalConfluenceDocumentation());
    }

    private boolean isEnabled(String value) {
        return (value != null && value.length() != 0 && value.compareTo("0") != 0);
    }

    private ExternalDocumentation externalConfluenceDocumentation() {
        if (isEnabled(this.urlConfluence))
            return new ExternalDocumentation().description("Confluence Documentation").url(urlConfluence);
        return null;
    }
}
