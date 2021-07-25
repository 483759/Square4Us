package com.ssafy.square4us.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String SECURITY_SCHEMA_NAME = "Bearer";
    public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
    public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";
        
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }
    
    private List<SecurityReference> defaultAuth() {
    	AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL, AUTHORIZATION_SCOPE_GLOBAL_DESC);
    	AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        
        return newArrayList(new SecurityReference(SECURITY_SCHEMA_NAME, authorizationScopes));
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SSAFY Project API")
				.description("<h2>Square 4 us API Reference for Developers</h2>")
				.termsOfServiceUrl("https://edu.ssafy.com")
				.version("1.0").build();
	}

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Square 4 us")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.square4us.api.mvc.controller"))
				.paths(PathSelectors.any())
				.build()
                .securityContexts(newArrayList(securityContext()))
                .securitySchemes(newArrayList(new ApiKey(SECURITY_SCHEMA_NAME, "Authorization", "header")))
				;
	}
	
//	private Info info() {
//		return new Info().title("SSAFY Project API")
//				.description("<h2>Square 4 us API Reference for Developers</h2>")
//				.termsOfService("https://edu.ssafy.com")
//				.contact(new Contact().name("김준영, 윤이진, 천수승, 최경운").email("483759@naver.com"))
//				.license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
//				.version("1.0");
//	}
//					
//	@Bean
//	public OpenAPI openAPI() {
//		SecurityScheme basicAuth = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic");		
//		SecurityRequirement securityItem = new SecurityRequirement().addList("basicAuth");
//		
//		return new OpenAPI()
//				.components(new Components().addSecuritySchemes("basicAuth", basicAuth))
//				.addSecurityItem(securityItem)
//				.info(info())
//				;
//				
//	}
	
//    @Bean
//    UiConfiguration uiConfig() {
//        return UiConfigurationBuilder.builder()
////                .supportedSubmitMethods(newArrayList("get").toArray(new String[0])) // try it 기능 활성화 범위
////                .operationsSorter(METHOD)
//                .build();
//    }
}
