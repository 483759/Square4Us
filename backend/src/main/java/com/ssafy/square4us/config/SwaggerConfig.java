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

    public static final String SECURITY_SCHEMA_NAME = "bearer";
    public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
    public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";
        
	
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
}
