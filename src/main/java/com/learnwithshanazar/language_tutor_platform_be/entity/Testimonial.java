package com.learnwithshanazar.language_tutor_platform_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "testimonials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Testimonial extends BaseEntity {

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "is_approved")
    @Builder.Default
    private Boolean isApproved = false;
}