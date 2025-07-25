package com.learnwithshanazar.language_tutor_platform_be.repository;

import com.learnwithshanazar.language_tutor_platform_be.entity.Booking;
import com.learnwithshanazar.language_tutor_platform_be.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findByStudentEmail(String email);

    List<Booking> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT b FROM Booking b WHERE b.course.id = :courseId AND b.date = :date AND b.time = :time AND b.status != 'CANCELLED'")
    List<Booking> findConflictingBookings(@Param("courseId") String courseId,
                                          @Param("date") LocalDate date,
                                          @Param("time") LocalTime time);

    @Query("SELECT b FROM Booking b WHERE b.course.id = :courseId")
    List<Booking> findByCourseId(@Param("courseId") String courseId);
}
