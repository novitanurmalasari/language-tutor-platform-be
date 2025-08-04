package com.learnwithshanazar.language_tutor_platform_be.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDTO {
    private String id;
    private String username;
    private String email;
    private Boolean isActive;
}

