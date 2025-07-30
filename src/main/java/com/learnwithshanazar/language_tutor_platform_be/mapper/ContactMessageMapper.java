package com.learnwithshanazar.language_tutor_platform_be.mapper;

import com.learnwithshanazar.language_tutor_platform_be.dto.ContactMessageDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.ContactMessage;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactMessageMapper {

    ContactMessageDTO toDTO(ContactMessage contactMessage);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ContactMessage toEntity(ContactMessageDTO contactMessageDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDTO(ContactMessageDTO dto, @MappingTarget ContactMessage entity);
}