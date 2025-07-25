package com.learnwithshanazar.language_tutor_platform_be.mapper;

import com.learnwithshanazar.language_tutor_platform_be.dto.TeacherDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Teacher;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {

    TeacherDTO toDTO(Teacher teacher);

    Teacher toEntity(TeacherDTO teacherDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(TeacherDTO dto, @MappingTarget Teacher entity);
}

