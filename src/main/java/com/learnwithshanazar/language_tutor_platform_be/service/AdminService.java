package com.learnwithshanazar.language_tutor_platform_be.service;

import com.learnwithshanazar.language_tutor_platform_be.dto.*;
import com.learnwithshanazar.language_tutor_platform_be.entity.Admin;
import com.learnwithshanazar.language_tutor_platform_be.exception.ResourceNotFoundException;
import com.learnwithshanazar.language_tutor_platform_be.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    public AuthResponseDTO login(LoginDTO loginDTO) {
        Admin admin = adminRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        // Simple password check - use BCrypt in production
        if (!admin.getPassword().equals(loginDTO.getPassword())) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        if (!admin.getIsActive()) {
            throw new IllegalStateException("Account is deactivated");
        }

        // Simple token generation - use JWT in production
        String token = UUID.randomUUID().toString();

        AdminDTO adminDTO = AdminDTO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .isActive(admin.getIsActive())
                .build();

        return AuthResponseDTO.builder()
                .token(token)
                .admin(adminDTO)
                .build();
    }

    public void createDefaultAdmin() {
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = Admin.builder()
                    .username("admin")
                    .password("admin123") // Change this!
                    .email("admin@linguateach.com")
                    .isActive(true)
                    .build();
            adminRepository.save(admin);
        }
    }
}
