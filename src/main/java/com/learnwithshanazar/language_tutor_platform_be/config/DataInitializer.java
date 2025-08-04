package com.learnwithshanazar.language_tutor_platform_be.config;

import com.learnwithshanazar.language_tutor_platform_be.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    CommandLineRunner init(AdminService adminService) {
        return args -> {
            // Create default admin user
            adminService.createDefaultAdmin();
        };
    }
}
