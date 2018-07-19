package com.datami.swagger;

import static springfox.documentation.builders.PathSelectors.ant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
//import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Shiv Pratap Singh
 * @since june 22,2018
 *
 */


@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages="com.datami")
public class SwaggerConfig {
	
    @Bean
	public Docket reconciliationApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("ReadWriteJSON")
				.apiInfo(JSONReadWriteApiInfo()).select()
				// .paths(PathSelectors.regex("/anyPath.*"))
				.paths(readwriteJsonApiPaths()).build().enableUrlTemplating(false)
//                .securitySchemes(Collections.singletonList(securitySchema()));
				.securitySchemes(Lists.newArrayList(apiKey()));
	}
 
	private ApiKey apiKey() {
		return new ApiKey("AUTHORIZATION", "AUTHORIZATION", "header"); // to pass access_token in header "bearer {access_token}"
	}

    private ApiInfo JSONReadWriteApiInfo() {
        return new ApiInfoBuilder()
                .title("JSONFileReadWrite APIs")
                .description("JSONFileReadWrite APIs description")
                .termsOfServiceUrl("https://www.datami.com") 
                .contact(new Contact("Shiv Pratap Singh", "http://datami.com", "shiv.singh@datami.com"))
                .license("JSONFileReadWrite Api license")
               .licenseUrl("https://www.jsonFileReadWriteApiUrl.com")
                .version("1.0")
                .build();
    }
    
    private Predicate<String> readwriteJsonApiPaths() {
        return ant("/**");
    }
}
