package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.config.PolarConfigurationProperties;
import com.polarbookshop.catalog_service.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

//@EnableJdbcRepositories
@SpringBootApplication
@EnableJdbcRepositories(basePackageClasses = {BookRepository.class})
@EnableConfigurationProperties(PolarConfigurationProperties.class)
public class CatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServiceApplication.class, args);
	}

}
