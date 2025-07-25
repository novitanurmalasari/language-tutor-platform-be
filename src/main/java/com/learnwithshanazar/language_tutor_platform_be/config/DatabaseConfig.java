package com.learnwithshanazar.language_tutor_platform_be.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.learnwithshanazar.language_tutor_platform_be.repository")
@EntityScan(basePackages = "com.linguateach.entity")
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfig {
}