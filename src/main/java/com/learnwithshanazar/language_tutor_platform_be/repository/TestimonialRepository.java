package com.learnwithshanazar.language_tutor_platform_be.repository;

import com.learnwithshanazar.language_tutor_platform_be.entity.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, String> {
    List<Testimonial> findByIsApprovedTrue();
    List<Testimonial> findByIsApprovedFalse();
}
