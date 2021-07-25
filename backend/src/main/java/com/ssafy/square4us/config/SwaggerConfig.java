package com.ssafy.square4us.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Component
public class SwaggerConfig {

    public static final String SECURITY_SCHEMA_NAME = "Bearer";
    public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
    public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";
        
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//    
//    private List<SecurityReference> defaultAuth() {
//    	AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL, AUTHORIZATION_SCOPE_GLOBAL_DESC);
//    	AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        
//        return newArrayList(new SecurityReference(SECURITY_SCHEMA_NAME, authorizationScopes));
//    }
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("SSAFY Project API")
//				.description("<h2>Square 4 us API Reference for Developers</h2>")
//				.termsOfServiceUrl("https://edu.ssafy.com")
//				.version("1.0").build();
//	}
//
//	@Bean
//	public Docket postsApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.groupName("Square 4 us")
//				.apiInfo(apiInfo())
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.ssafy.square4us.api.mvc.controller"))
//				.paths(PathSelectors.any())
//				.build()
//                .securityContexts(newArrayList(securityContext()))
//                .securitySchemes(newArrayList(new ApiKey(SECURITY_SCHEMA_NAME, "Authorization", "header")))
//				;
//	}
	
	private Info info() {
		return new Info().title("SSAFY Project API")
				.description("<h2>Square 4 us API Reference for Developers</h2>")
				.termsOfService("https://edu.ssafy.com")
				.contact(new Contact().name("김준영, 윤이진, 천수승, 최경운").email("483759@naver.com"))
				.license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
				.version("1.0");
	}
					
	@Bean
	public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
		Info info = new Info().title("SSAFY Project API")
				.description("<h2>Square 4 us API Reference for Developers</h2>")
				.termsOfService("https://edu.ssafy.com")
				.contact(new Contact().name("김준영, 윤이진, 천수승, 최경운").email("483759@naver.com"))
				.license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
				.version("1.0");
		
		SecurityScheme basicAuth = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
				.in(SecurityScheme.In.HEADER).name("Authorization");		
		SecurityRequirement securityItem = new SecurityRequirement().addList("bearerAuth");
		
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("bearerAuth", basicAuth))
				.addSecurityItem(securityItem)
				.info(info)
				;
				
	}
	
//	  @Bean
//	  public OpenAPI openAPI(@Value("${springdoc.version}") String appVersion) {
//	    Info info = new Info().title("Demo API").version(appVersion)
//	            .description("Spring Boot를 이용한 Demo 웹 애플리케이션 API입니다.")
//	            .termsOfService("http://swagger.io/terms/")
//	            .contact(new Contact().name("jini").url("https://blog.jiniworld.me/").email("jini@jiniworld.me"))
//	            .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));
//
//	    return new OpenAPI()
//	            .components(new Components())
//	            .info(info);
//	  }
}
