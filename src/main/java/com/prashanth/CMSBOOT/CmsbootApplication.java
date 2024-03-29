package com.prashanth.CMSBOOT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@ComponentScan(basePackages = {"com.prashanth.*"})
 @EntityScan(basePackages = {"com.prashanth.model"}) 
@EnableJpaRepositories(basePackages = {"com.prashanth.dao"})
public class CmsbootApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CmsbootApplication.class, args);
	}
	@Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(CmsbootApplication.class);
  }
	
}
