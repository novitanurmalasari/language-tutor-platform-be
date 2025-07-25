package com.learnwithshanazar.language_tutor_platform_be.dto;

import com.learnwithshanazar.language_tutor_platform_be.enums.BookingStatus;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {

    private String id;

    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String studentName;

    @NotBlank(message = "Student email is required")
    @Email(message = "Email must be valid")
    private String studentEmail;

    @NotBlank(message = "Student phone is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be valid")
    private String studentPhone;

    @NotNull(message = "Course is required")
    private String courseId;

    @NotNull(message = "Date is required")
    @Future(message = "Date must be in the future")
    private LocalDate date;

    @NotNull(message = "Time is required")
    private LocalTime time;

    @Size(max = 500, message = "Message must not exceed 500 characters")
    private String message;

    private BookingStatus status;

    private LocalDateTime createdAt;

    // Additional fields for response
    private String courseTitle;
    private String courseLanguage;
    private String courseLevel;
}