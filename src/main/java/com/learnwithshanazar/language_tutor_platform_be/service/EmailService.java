package com.learnwithshanazar.language_tutor_platform_be.service;

import com.learnwithshanazar.language_tutor_platform_be.entity.Booking;
import com.learnwithshanazar.language_tutor_platform_be.entity.ContactMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.admin}")
    private String adminEmail;

    public void sendBookingConfirmation(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(booking.getStudentEmail());
        message.setSubject("Booking Confirmation - learnwithshanazar.language_tutor_platform_be");
        message.setText(String.format(
                "Dear %s,\n\n" +
                        "Thank you for booking a lesson with learnwithshanazar.language_tutor_platform_be!\n\n" +
                        "Course: %s\n" +
                        "Date: %s\n" +
                        "Time: %s\n" +
                        "Status: %s\n\n" +
                        "We will contact you within 24 hours to confirm your booking.\n\n" +
                        "Best regards,\n" +
                        "learnwithshanazar.language_tutor_platform_be Team",
                booking.getStudentName(),
                booking.getCourse().getTitle(),
                booking.getDate(),
                booking.getTime(),
                booking.getStatus().getDisplayName()
        ));

        try {
            mailSender.send(message);
            log.info("Booking confirmation email sent to {}", booking.getStudentEmail());
        } catch (Exception e) {
            log.error("Failed to send booking confirmation email", e);
        }
    }

    public void sendBookingStatusUpdate(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(booking.getStudentEmail());
        message.setSubject("Booking Status Update - learnwithshanazar.language_tutor_platform_be");
        message.setText(String.format(
                "Dear %s,\n\n" +
                        "Your booking status has been updated.\n\n" +
                        "Course: %s\n" +
                        "Date: %s\n" +
                        "Time: %s\n" +
                        "New Status: %s\n\n" +
                        "If you have any questions, please contact us.\n\n" +
                        "Best regards,\n" +
                        "learnwithshanazar.language_tutor_platform_be Team",
                booking.getStudentName(),
                booking.getCourse().getTitle(),
                booking.getDate(),
                booking.getTime(),
                booking.getStatus().getDisplayName()
        ));

        try {
            mailSender.send(message);
            log.info("Booking status update email sent to {}", booking.getStudentEmail());
        } catch (Exception e) {
            log.error("Failed to send booking status update email", e);
        }
    }

    public void sendNewMessageNotification(ContactMessage contactMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(adminEmail);
        message.setSubject("New Contact Message - " + contactMessage.getSubject());
        message.setText(String.format(
                "New message received from website:\n\n" +
                        "From: %s (%s)\n" +
                        "Subject: %s\n\n" +
                        "Message:\n%s",
                contactMessage.getName(),
                contactMessage.getEmail(),
                contactMessage.getSubject(),
                contactMessage.getMessage()
        ));

        try {
            mailSender.send(message);
            log.info("New message notification sent to admin");
        } catch (Exception e) {
            log.error("Failed to send new message notification", e);
        }
    }
}
