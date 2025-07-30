package com.learnwithshanazar.language_tutor_platform_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.learnwithshanazar.language_tutor_platform_be.entity")
@EnableJpaRepositories(basePackages = "com.learnwithshanazar.language_tutor_platform_be.repository")
@EnableJpaAuditing
public class LanguageTutorPlatformBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageTutorPlatformBeApplication.class, args);
	}
}