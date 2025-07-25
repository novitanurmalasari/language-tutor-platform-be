package com.learnwithshanazar.language_tutor_platform_be.dto;

import com.learnwithshanazar.language_tutor_platform_be.enums.Language;
import com.learnwithshanazar.language_tutor_platform_be.enums.Level;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {

    private String id;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @NotNull(message = "Language is required")
    private Language language;

    @NotNull(message = "Level is required")
    private Level level;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 30, message = "Duration must be at least 30 minutes")
    @Max(value = 180, message = "Duration must not exceed 180 minutes")
    private Integer duration;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private BigDecimal price;

    @Min(value = 1, message = "Max students must be at least 1")
    @Max(value = 20, message = "Max students must not exceed 20")
    private Integer maxStudents;

    private Integer currentStudents;

    private Boolean isActive;

    private List<String> schedule;

    private String teacherId;
}
