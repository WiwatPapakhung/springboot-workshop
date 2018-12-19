package com.business.order;

import java.util.ArrayList;
import java.util.List;

import com.business.order.item.ItemRepository;
import com.business.order.item.ProductRepository;
import com.business.order.item.SaleOrderRepository;

import com.business.order.item.Product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner initialProductData(ProductRepository productRepo, SaleOrderRepository saleRepo,
			ItemRepository itemRepo) {
		return (args) -> {
			List<Product> products = new ArrayList<>();
			products.add(new Product("10001", "Java Version 1.0", "pct", 100d));
			products.add(new Product("10002", "Devops", "pct", 230d));
			products.add(new Product("10003", "Spring Boot ver. 2.1", "pct", 200d));
			products.add(new Product("10004", "Flutter", "pct", 350d));
			productRepo.saveAll(products);
		};
	}
}
