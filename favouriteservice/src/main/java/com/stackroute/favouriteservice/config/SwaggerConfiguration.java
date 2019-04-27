package com.stackroute.favouriteservice.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Autowired
	Environment environment;

	@Bean
	public Docket getDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getAPIInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.stackroute.favouriteservice"))
				.paths(regex("/news/api/v1.*")).build();

	}

	private ApiInfo getAPIInfo() {
		return new ApiInfoBuilder().title(environment.getProperty("news.swagger.title"))
				.description(environment.getProperty("news.swagger.description"))
				.termsOfServiceUrl(environment.getProperty("news.swagger.terms.of.service"))
				.contact(new Contact(environment.getProperty("news.swagger.contact.name"),
						environment.getProperty("news.swagger.contact.url"),
						environment.getProperty("news.swagger.contact.email")))
				.build();
	}

}
