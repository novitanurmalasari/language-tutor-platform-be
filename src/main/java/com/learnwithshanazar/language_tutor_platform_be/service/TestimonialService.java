package com.learnwithshanazar.language_tutor_platform_be.service;

import com.learnwithshanazar.language_tutor_platform_be.dto.TestimonialDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Testimonial;
import com.learnwithshanazar.language_tutor_platform_be.exception.ResourceNotFoundException;
import com.learnwithshanazar.language_tutor_platform_be.mapper.TestimonialMapper;
import com.learnwithshanazar.language_tutor_platform_be.repository.TestimonialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final TestimonialMapper testimonialMapper;

    public List<TestimonialDTO> getApprovedTestimonials() {
        return testimonialRepository.findByIsApprovedTrue().stream()
                .map(testimonialMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TestimonialDTO> getPendingTestimonials() {
        return testimonialRepository.findByIsApprovedFalse().stream()
                .map(testimonialMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TestimonialDTO getTestimonialById(String id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found with id: " + id));
        return testimonialMapper.toDTO(testimonial);
    }

    public TestimonialDTO createTestimonial(TestimonialDTO testimonialDTO) {
        Testimonial testimonial = testimonialMapper.toEntity(testimonialDTO);
        testimonial.setIsApproved(false); // Require approval

        Testimonial savedTestimonial = testimonialRepository.save(testimonial);
        return testimonialMapper.toDTO(savedTestimonial);
    }

    public TestimonialDTO approveTestimonial(String id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found with id: " + id));

        testimonial.setIsApproved(true);
        Testimonial updatedTestimonial = testimonialRepository.save(testimonial);
        return testimonialMapper.toDTO(updatedTestimonial);
    }

    public void deleteTestimonial(String id) {
        if (!testimonialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Testimonial not found with id: " + id);
        }
        testimonialRepository.deleteById(id);
    }
}
