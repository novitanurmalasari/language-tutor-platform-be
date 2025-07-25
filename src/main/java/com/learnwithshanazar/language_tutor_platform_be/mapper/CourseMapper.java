package com.learnwithshanazar.language_tutor_platform_be.mapper;

import com.learnwithshanazar.language_tutor_platform_be.dto.CourseDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

    @Mapping(source = "teacher.id", target = "teacherId")
    CourseDTO toDTO(Course course);

    @Mapping(target = "teacher", ignore = true)
    Course toEntity(CourseDTO courseDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    void updateEntityFromDTO(CourseDTO dto, @MappingTarget Course entity);
}


