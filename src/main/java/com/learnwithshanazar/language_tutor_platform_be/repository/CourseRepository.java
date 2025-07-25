package com.learnwithshanazar.language_tutor_platform_be.repository;

import com.learnwithshanazar.language_tutor_platform_be.entity.Course;
import com.learnwithshanazar.language_tutor_platform_be.enums.Language;
import com.learnwithshanazar.language_tutor_platform_be.enums.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    List<Course> findByIsActiveTrue();

    List<Course> findByLanguageAndIsActiveTrue(Language language);

    List<Course> findByLevelAndIsActiveTrue(Level level);

    List<Course> findByLanguageAndLevelAndIsActiveTrue(Language language, Level level);

    @Query("SELECT c FROM Course c WHERE c.currentStudents < c.maxStudents AND c.isActive = true")
    List<Course> findAvailableCourses();

    @Query("SELECT c FROM Course c WHERE c.teacher.id = :teacherId")
    List<Course> findByTeacherId(@Param("teacherId") String teacherId);
}
