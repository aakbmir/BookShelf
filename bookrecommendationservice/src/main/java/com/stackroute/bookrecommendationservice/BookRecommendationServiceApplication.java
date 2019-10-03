package com.stackroute.bookrecommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BookRecommendationServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(BookRecommendationServiceApplication.class, args);
	}
}