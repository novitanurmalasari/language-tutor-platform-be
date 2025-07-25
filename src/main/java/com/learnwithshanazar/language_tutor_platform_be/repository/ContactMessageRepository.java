package com.learnwithshanazar.language_tutor_platform_be.repository;

import com.learnwithshanazar.language_tutor_platform_be.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, String> {
    List<ContactMessage> findByIsReadFalse();
    long countByIsReadFalse();
}
