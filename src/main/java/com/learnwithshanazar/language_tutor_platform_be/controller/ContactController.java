package com.learnwithshanazar.language_tutor_platform_be.controller;

import com.learnwithshanazar.language_tutor_platform_be.dto.ContactMessageDTO;
import com.learnwithshanazar.language_tutor_platform_be.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessageDTO>> getAllMessages() {
        return ResponseEntity.ok(contactService.getAllMessages());
    }

    @GetMapping("/messages/unread")
    public ResponseEntity<List<ContactMessageDTO>> getUnreadMessages() {
        return ResponseEntity.ok(contactService.getUnreadMessages());
    }

    @GetMapping("/messages/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        long count = contactService.getUnreadCount();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<ContactMessageDTO> getMessageById(@PathVariable String id) {
        return ResponseEntity.ok(contactService.getMessageById(id));
    }

    @PostMapping
    public ResponseEntity<ContactMessageDTO> createMessage(@Valid @RequestBody ContactMessageDTO messageDTO) {
        ContactMessageDTO createdMessage = contactService.createMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }

    @PatchMapping("/messages/{id}/read")
    public ResponseEntity<ContactMessageDTO> markAsRead(@PathVariable String id) {
        return ResponseEntity.ok(contactService.markAsRead(id));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        contactService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}

