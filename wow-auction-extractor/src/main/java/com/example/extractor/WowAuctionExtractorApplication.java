package com.example.extractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WowAuctionExtractorApplication {

	public static void main(String[] args) {
		SpringApplication.run(WowAuctionExtractorApplication.class, args);
	}
}
