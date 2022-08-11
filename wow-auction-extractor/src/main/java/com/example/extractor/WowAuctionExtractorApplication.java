package com.example.extractor;

import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.tcp.TcpClient;

@SpringBootApplication
@EnableScheduling
public class WowAuctionExtractorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WowAuctionExtractorApplication.class, args);
    }

    @Bean
    public WebClient webClient(@Value("${wow.auctionHelperUrl}") String baseUrl) {
        return WebClient.create(baseUrl);
    }
}
