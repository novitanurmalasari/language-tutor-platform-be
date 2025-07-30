package com.learnwithshanazar.language_tutor_platform_be.mapper;

import com.learnwithshanazar.language_tutor_platform_be.dto.TestimonialDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Testimonial;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TestimonialMapper {

    TestimonialDTO toDTO(Testimonial testimonial);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Testimonial toEntity(TestimonialDTO testimonialDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDTO(TestimonialDTO dto, @MappingTarget Testimonial entity);
}