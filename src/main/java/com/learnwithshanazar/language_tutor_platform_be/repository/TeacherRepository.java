package com.learnwithshanazar.language_tutor_platform_be.repository;

import com.learnwithshanazar.language_tutor_platform_be.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Optional<Teacher> findByEmail(String email);
    boolean existsByEmail(String email);
}
