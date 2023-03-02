package com.example.upper.lower.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@EnableFeignClients
@SpringBootApplication
public class UpperLowerServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(UpperLowerServiceApplication.class, args);
    }

    @Slf4j
    @RestController
    @RequestMapping("/api/v1/discovery")
    public static class DiscoveryUpperLowerController
    {
        private final DiscoveryUppercaseServiceClient uClient;
        private final DiscoveryLowercaseServiceClient lClient;

        public DiscoveryUpperLowerController(DiscoveryUppercaseServiceClient uClient, DiscoveryLowercaseServiceClient lClient)
        {
            this.uClient = uClient;
            this.lClient = lClient;
        }

        @PostMapping("/lower")
        public ResponseEntity<String> transformToLowercase(@RequestParam String s)
        {
            log.info("{} {} to lowercase", this.getClass().getName(), s);
            return ResponseEntity.ok(this.lClient.toLowercase(s));
        }

        @PostMapping("/upper")
        public ResponseEntity<String> transformToUppercase(@RequestParam String s)
        {
            log.info("{} {} to uppercase", this.getClass(), s);
            return ResponseEntity.ok(this.uClient.toUppercase(s));
        }
    }

    @FeignClient("lowercase-service")
    public interface DiscoveryLowercaseServiceClient
    {
        @PostMapping("/api/v1/lower")
        String toLowercase(@RequestBody String s);
    }

    @FeignClient("uppercase-service")
    public interface DiscoveryUppercaseServiceClient
    {
        @PostMapping("/api/v1/upper")
        String toUppercase(@RequestBody String s);
    }
}
