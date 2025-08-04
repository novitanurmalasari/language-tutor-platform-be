package com.learnwithshanazar.language_tutor_platform_be.controller;

import com.learnwithshanazar.language_tutor_platform_be.dto.BookingDTO;
import com.learnwithshanazar.language_tutor_platform_be.dto.ContactMessageDTO;
import com.learnwithshanazar.language_tutor_platform_be.dto.TestimonialDTO;
import com.learnwithshanazar.language_tutor_platform_be.enums.BookingStatus;
import com.learnwithshanazar.language_tutor_platform_be.service.BookingService;
import com.learnwithshanazar.language_tutor_platform_be.service.ContactService;
import com.learnwithshanazar.language_tutor_platform_be.service.TestimonialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final BookingService bookingService;
    private final TestimonialService testimonialService;
    private final ContactService contactService;

    // Dashboard Stats
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = Map.of(
                "totalBookings", bookingService.getAllBookings().size(),
                "pendingBookings", bookingService.getBookingsByStatus(BookingStatus.PENDING).size(),
                "pendingTestimonials", testimonialService.getPendingTestimonials().size(),
                "unreadMessages", contactService.getUnreadCount()
        );
        return ResponseEntity.ok(stats);
    }

    // Booking Management
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PatchMapping("/bookings/{id}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable String id,
            @RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    // Testimonial Management
    @GetMapping("/testimonials/pending")
    public ResponseEntity<List<TestimonialDTO>> getPendingTestimonials() {
        return ResponseEntity.ok(testimonialService.getPendingTestimonials());
    }

    @PatchMapping("/testimonials/{id}/approve")
    public ResponseEntity<TestimonialDTO> approveTestimonial(@PathVariable String id) {
        return ResponseEntity.ok(testimonialService.approveTestimonial(id));
    }

    @DeleteMapping("/testimonials/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable String id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }

    // Contact Message Management
    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessageDTO>> getAllMessages() {
        return ResponseEntity.ok(contactService.getAllMessages());
    }

    @PatchMapping("/messages/{id}/read")
    public ResponseEntity<ContactMessageDTO> markMessageAsRead(@PathVariable String id) {
        return ResponseEntity.ok(contactService.markAsRead(id));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        contactService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}