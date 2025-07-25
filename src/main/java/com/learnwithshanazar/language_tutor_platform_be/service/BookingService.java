package com.learnwithshanazar.language_tutor_platform_be.service;

import com.learnwithshanazar.language_tutor_platform_be.dto.BookingDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.Booking;
import com.learnwithshanazar.language_tutor_platform_be.entity.Course;
import com.learnwithshanazar.language_tutor_platform_be.enums.BookingStatus;
import com.learnwithshanazar.language_tutor_platform_be.exception.ResourceNotFoundException;
import com.learnwithshanazar.language_tutor_platform_be.mapper.BookingMapper;
import com.learnwithshanazar.language_tutor_platform_be.repository.BookingRepository;
import com.learnwithshanazar.language_tutor_platform_be.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CourseRepository courseRepository;
    private final BookingMapper bookingMapper;
    private final EmailService emailService;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return bookingMapper.toDTO(booking);
    }

    public List<BookingDTO> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getBookingsByEmail(String email) {
        return bookingRepository.findByStudentEmail(email).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Validate course exists and has available slots
        Course course = courseRepository.findById(bookingDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + bookingDTO.getCourseId()));

        if (course.getCurrentStudents() >= course.getMaxStudents()) {
            throw new IllegalStateException("Course is fully booked");
        }

        // Check for conflicting bookings
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                bookingDTO.getCourseId(),
                bookingDTO.getDate(),
                bookingDTO.getTime()
        );

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Time slot is already booked");
        }

        // Create booking
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setCourse(course);
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        // Send confirmation email
        emailService.sendBookingConfirmation(savedBooking);

        return bookingMapper.toDTO(savedBooking);
    }

    public BookingDTO updateBookingStatus(String id, BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(status);

        // Update course student count
        if (status == BookingStatus.CONFIRMED && oldStatus != BookingStatus.CONFIRMED) {
            Course course = booking.getCourse();
            course.setCurrentStudents(course.getCurrentStudents() + 1);
            courseRepository.save(course);
        } else if (oldStatus == BookingStatus.CONFIRMED &&
                (status == BookingStatus.CANCELLED || status == BookingStatus.COMPLETED)) {
            Course course = booking.getCourse();
            course.setCurrentStudents(Math.max(0, course.getCurrentStudents() - 1));
            courseRepository.save(course);
        }

        Booking updatedBooking = bookingRepository.save(booking);

        // Send status update email
        emailService.sendBookingStatusUpdate(updatedBooking);

        return bookingMapper.toDTO(updatedBooking);
    }

    public void deleteBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            Course course = booking.getCourse();
            course.setCurrentStudents(Math.max(0, course.getCurrentStudents() - 1));
            courseRepository.save(course);
        }

        bookingRepository.deleteById(id);
    }
}

