package com.learnwithshanazar.language_tutor_platform_be.controller;

import com.learnwithshanazar.language_tutor_platform_be.dto.LoginRequest;
import com.learnwithshanazar.language_tutor_platform_be.dto.LoginResponse;
import com.learnwithshanazar.language_tutor_platform_be.entity.Admin;
import com.learnwithshanazar.language_tutor_platform_be.repository.AdminRepository;
import com.learnwithshanazar.language_tutor_platform_be.security.CustomUserDetails;
import com.learnwithshanazar.language_tutor_platform_be.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostConstruct
    public void init() {
        // Create default admin if not exists
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = Admin.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@linguateach.com")
                    .fullName("Administrator")
                    .role(Admin.Role.SUPER_ADMIN)
                    .isActive(true)
                    .build();
            adminRepository.save(admin);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        LoginResponse response = LoginResponse.builder()
                .accessToken(jwt)
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();

        return ResponseEntity.ok(response);
    }
}