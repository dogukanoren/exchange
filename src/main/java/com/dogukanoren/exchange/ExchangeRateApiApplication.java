package com.dogukanoren.exchange;

import java.util.HashMap;

import com.dogukanoren.exchange.service.ExchangeService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dogukanoren.*")
@SpringBootApplication
public class ExchangeRateApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			HashMap<String, Double> map = new HashMap<>();
			map.put("USD", 1.2d);
			map.put("EUR", 1.0d);
			map.put("TRY", 14.3d);
			map.put("ECO", 0.03d);
			map.put("DOU", 0.001d);

			ExchangeService.fillRates(map);
		};
	}

}
