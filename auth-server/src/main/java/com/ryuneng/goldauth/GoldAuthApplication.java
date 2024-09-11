package com.ryuneng.goldauth;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {GrpcServerSecurityAutoConfiguration.class})
public class GoldAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldAuthApplication.class, args);
	}
}
