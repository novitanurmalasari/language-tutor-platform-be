package com.learnwithshanazar.language_tutor_platform_be.controller;

import com.learnwithshanazar.language_tutor_platform_be.dto.TestimonialDTO;
import com.learnwithshanazar.language_tutor_platform_be.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;
import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@RequiredArgsConstructor
public class TestimonialController {

    private final TestimonialService testimonialService;

    @GetMapping
    public ResponseEntity<List<TestimonialDTO>> getApprovedTestimonials() {
        return ResponseEntity.ok(testimonialService.getApprovedTestimonials());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TestimonialDTO>> getPendingTestimonials() {
        return ResponseEntity.ok(testimonialService.getPendingTestimonials());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonialDTO> getTestimonialById(@PathVariable String id) {
        return ResponseEntity.ok(testimonialService.getTestimonialById(id));
    }

    @PostMapping
    public ResponseEntity<TestimonialDTO> createTestimonial(@Valid @RequestBody TestimonialDTO testimonialDTO) {
        TestimonialDTO createdTestimonial = testimonialService.createTestimonial(testimonialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial);
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<TestimonialDTO> approveTestimonial(@PathVariable String id) {
        return ResponseEntity.ok(testimonialService.approveTestimonial(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable String id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}

