package com.spring.cloud.springcloud;

import com.spring.cloud.springcloud.model.entities.Customer;
import com.spring.cloud.springcloud.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class MicroServicesSpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServicesSpringCloudApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration repositoryRestConfiguration){
        return args -> {
            repositoryRestConfiguration.exposeIdsFor(Customer.class);
            customerRepository.save(new Customer(null, "test1","test1@gmail.com"));
            customerRepository.save(new Customer(null, "test2","test2@gmail.com"));
            customerRepository.save(new Customer(null, "test3","test3@gmail.com"));
            customerRepository.save(new Customer(null, "test4","test4@gmail.com"));
            customerRepository.findAll().forEach(System.out::println);
        };
    }

}
