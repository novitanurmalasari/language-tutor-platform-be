package com.learnwithshanazar.language_tutor_platform_be.controller;

import com.learnwithshanazar.language_tutor_platform_be.dto.CourseDTO;
import com.learnwithshanazar.language_tutor_platform_be.enums.Language;
import com.learnwithshanazar.language_tutor_platform_be.enums.Level;
import com.learnwithshanazar.language_tutor_platform_be.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(
            @RequestParam(required = false) Language language,
            @RequestParam(required = false) Level level) {

        List<CourseDTO> courses;

        if (language != null && level != null) {
            courses = courseService.getCoursesByLanguageAndLevel(language, level);
        } else if (language != null) {
            courses = courseService.getCoursesByLanguage(language);
        } else if (level != null) {
            courses = courseService.getCoursesByLevel(level);
        } else {
            courses = courseService.getAllCourses();
        }

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/available")
    public ResponseEntity<List<CourseDTO>> getAvailableCourses() {
        return ResponseEntity.ok(courseService.getAvailableCourses());
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable String id,
            @Valid @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
