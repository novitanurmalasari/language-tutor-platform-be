package com.learnwithshanazar.language_tutor_platform_be.service;

import com.learnwithshanazar.language_tutor_platform_be.dto.CourseDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Course;
import com.learnwithshanazar.language_tutor_platform_be.entity.Teacher;
import com.learnwithshanazar.language_tutor_platform_be.enums.Language;
import com.learnwithshanazar.language_tutor_platform_be.enums.Level;
import com.learnwithshanazar.language_tutor_platform_be.exception.ResourceNotFoundException;
import com.learnwithshanazar.language_tutor_platform_be.mapper.CourseMapper;
import com.learnwithshanazar.language_tutor_platform_be.repository.CourseRepository;
import com.learnwithshanazar.language_tutor_platform_be.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findByIsActiveTrue().stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return courseMapper.toDTO(course);
    }

    public List<CourseDTO> getCoursesByLanguage(Language language) {
        return courseRepository.findByLanguageAndIsActiveTrue(language).stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByLevel(Level level) {
        return courseRepository.findByLevelAndIsActiveTrue(level).stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByLanguageAndLevel(Language language, Level level) {
        return courseRepository.findByLanguageAndLevelAndIsActiveTrue(language, level).stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getAvailableCourses() {
        return courseRepository.findAvailableCourses().stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);

        if (courseDTO.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(courseDTO.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + courseDTO.getTeacherId()));
            course.setTeacher(teacher);
        }

        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDTO(savedCourse);
    }

    public CourseDTO updateCourse(String id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        courseMapper.updateEntityFromDTO(courseDTO, existingCourse);

        if (courseDTO.getTeacherId() != null && !courseDTO.getTeacherId().equals(existingCourse.getTeacher().getId())) {
            Teacher teacher = teacherRepository.findById(courseDTO.getTeacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + courseDTO.getTeacherId()));
            existingCourse.setTeacher(teacher);
        }

        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toDTO(updatedCourse);
    }

    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        course.setIsActive(false);
        courseRepository.save(course);
    }
}