package com.spring.cloud.inventoryservice;

import com.spring.cloud.inventoryservice.model.entities.Product;
import com.spring.cloud.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;


@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner star(ProductRepository productRepository,
						   RepositoryRestConfiguration repositoryRestConfiguration){
		return args -> {
			repositoryRestConfiguration.exposeIdsFor(Product.class);
			productRepository.save(new Product(null, "Ordinateur portable",85000));
			productRepository.save(new Product(null, "Imprimante",200000));
			productRepository.save(new Product(null, "Téléphone",20000));
			productRepository.save(new Product(null, "Ecra,",100000));
			productRepository.findAll().forEach(System.out::println);
		};
	}

}
