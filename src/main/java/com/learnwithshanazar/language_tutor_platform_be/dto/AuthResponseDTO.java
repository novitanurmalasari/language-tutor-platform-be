package com.learnwithshanazar.language_tutor_platform_be.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private AdminDTO admin;
}