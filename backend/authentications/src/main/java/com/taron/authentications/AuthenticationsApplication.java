package com.taron.authentications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AuthenticationsApplication {

    public static void main(String[] args) {
        System.setProperty("com.netflix.eureka.client.RestClientFactory.disableRedirectFollowWorkaround", "true");
        SpringApplication.run(AuthenticationsApplication.class, args);
    }

}
