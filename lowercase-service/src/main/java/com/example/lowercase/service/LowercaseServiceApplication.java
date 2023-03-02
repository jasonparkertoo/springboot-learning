package com.example.lowercase.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
public class LowercaseServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(LowercaseServiceApplication.class, args);
    }

    @Slf4j
    @RestController
    @RequestMapping("/api/v1/lower")
    public static class LowercaseController
    {
        @PostMapping
        public ResponseEntity<String> toLower(@RequestBody String s)
        {
            log.info("{} is converting {} to lowercase", this.getClass().getName(), s);
            if (s == null || s.isBlank())
                return ResponseEntity.badRequest().build();

            return ResponseEntity.ok(s.toLowerCase());
        }
    }

    @RestController
    public static class ServiceInstanceRestController
    {
        private final DiscoveryClient discoveryClient;

        public ServiceInstanceRestController(DiscoveryClient discoveryClient)
        {
            this.discoveryClient = discoveryClient;
        }

        @RequestMapping(value = "/service-instances/{applicationName}", produces = MediaType.APPLICATION_JSON_VALUE)
        public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName)
        {
            return this.discoveryClient.getInstances(applicationName);
        }
    }
}
