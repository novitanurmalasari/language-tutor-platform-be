package com.learnwithshanazar.language_tutor_platform_be.mapper;

import com.learnwithshanazar.language_tutor_platform_be.dto.BookingDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingMapper {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseTitle")
    @Mapping(source = "course.language", target = "courseLanguage")
    @Mapping(source = "course.level", target = "courseLevel")
    BookingDTO toDTO(Booking booking);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Booking toEntity(BookingDTO bookingDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromDTO(BookingDTO dto, @MappingTarget Booking entity);
}