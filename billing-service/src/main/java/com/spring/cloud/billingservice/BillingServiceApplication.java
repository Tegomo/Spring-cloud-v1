package com.spring.cloud.billingservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Bill{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date billingDate;
	private  Long customerId;
	@OneToMany(mappedBy = "bill")
	private Collection<ProductItem> productItems;
}

@RepositoryRestResource
interface BillRepository extends JpaRepository<Bill, Long>{}

@Projection(name = "fullbill", types = Bill.class)
interface BillProjection{
	public Long getId();
	public Date getBillingDate();
	public Long getCustomerId();
	public Collection<ProductItem> getProductItems();
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class ProductItem{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private  Long productId;
	private  double price;
	private  double quantity;
	@ManyToOne
	private Bill bill;
}

@RepositoryRestResource
interface ProductItemRepository extends JpaRepository<ProductItem, Long>{}

@Data
class Customer{
	private  Long id;
	private String name;
	private String email;
}
@FeignClient(name = "CUSTOMER-SERVICE")
interface CustomerService{
	@GetMapping("/customers/{id}")
	public  Customer findCustomerById(@PathVariable(name = "id") Long id);
}

@Data
class Product{
	private  Long id;
	private String name;
	private double price;
}
@FeignClient(name = "INVENTORY-SERVICE")
interface InventoryService{
	@GetMapping("/products/{id}")
	public  Product findProductById(@PathVariable(name = "id") Long id);

	@GetMapping("/products")
	public PagedModel<Product> findAllProducts();

}

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(BillRepository billRepository,
							ProductItemRepository productItemRepository,
							CustomerService customerService,
							InventoryService inventoryService){
		return args -> {
			Customer c1 = customerService.findCustomerById(1L);
			System.out.println("********************************************");
			System.out.println("ID= "+c1.getId());
			System.out.println("NAME= "+c1.getName());
			System.out.println("EMAIL= "+c1.getEmail());
			System.out.println("********************************************");
			Bill bill1 = billRepository.save(new Bill(null, new Date(), c1.getId(), null));

			PagedModel<Product> products = inventoryService.findAllProducts();
			products.getContent().forEach(p -> {
				productItemRepository.save(new ProductItem(null, p.getId(),p.getPrice(), 20, bill1));
				System.out.println("********************************************");
				System.out.println("ID= "+p.getId());
				System.out.println("NAME= "+p.getName());
				System.out.println("PRICE= "+p.getPrice());
				System.out.println("********************************************");
			});

		};
	}

}
