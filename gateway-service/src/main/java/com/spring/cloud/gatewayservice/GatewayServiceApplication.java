package com.spring.cloud.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	/*
	@Bean
	RouteLocator staticRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r->r.path("/customers/**").uri("1b://CUSTOMER-SERVICE/").id("r1"))
				.route(r->r.path("/priducts/**").uri("1b://INVENTORY-SERVICE/").id("r2"))
				.build();
	}
	*/

	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,
														DiscoveryLocatorProperties dpl){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dpl);

	}

}
