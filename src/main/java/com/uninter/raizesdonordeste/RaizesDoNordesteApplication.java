package com.uninter.raizesdonordeste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RaizesDoNordesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaizesDoNordesteApplication.class, args);
	}

}
