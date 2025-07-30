package com.learnwithshanazar.language_tutor_platform_be.mapper;

import com.learnwithshanazar.language_tutor_platform_be.dto.TeacherDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Teacher;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {

    @Mapping(source = "yearsExperience", target = "experience")
    TeacherDTO toDTO(Teacher teacher);

    @Mapping(source = "experience", target = "yearsExperience")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);

    @Mapping(source = "experience", target = "yearsExperience")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntityFromDTO(TeacherDTO dto, @MappingTarget Teacher entity);
}