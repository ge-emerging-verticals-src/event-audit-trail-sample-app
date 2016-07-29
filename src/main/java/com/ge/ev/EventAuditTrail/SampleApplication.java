package com.ge.ev.EventAuditTrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"com.ge.ev.EventAuditTrail"})
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
public class SampleApplication {
	public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
