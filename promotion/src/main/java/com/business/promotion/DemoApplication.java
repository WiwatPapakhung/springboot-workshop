package com.business.promotion;

import java.util.ArrayList;
import java.util.List;

import com.business.promotion.promotion.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initialPromoData(PromotionRepository repository) {
		return (args) -> {
			List<Promotion> promotion = new ArrayList<>();
			promotion.add(new Promotion("FREE100", 100d));
			promotion.add(new Promotion("FREE200", 200d));
			promotion.add(new Promotion("FREE150", 150d));
			repository.saveAll(promotion);
		};
	}

}
