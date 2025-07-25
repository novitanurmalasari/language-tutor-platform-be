package com.learnwithshanazar.language_tutor_platform_be.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(columnDefinition = "DECIMAL(3,2)")
    private Double rating;

    @ElementCollection
    @CollectionTable(name = "teacher_specializations",
            joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "specialization")
    private List<String> specializations = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();
}

