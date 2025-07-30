package com.learnwithshanazar.language_tutor_platform_be.entity;

import com.learnwithshanazar.language_tutor_platform_be.enums.Language;
import com.learnwithshanazar.language_tutor_platform_be.enums.Level;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Course extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer duration; // in minutes

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "current_students")
    @Builder.Default
    private Integer currentStudents = 0;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @ElementCollection
    @CollectionTable(name = "course_schedules",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "day_of_week")
    @Builder.Default
    private List<String> schedule = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();
}
