package com.learnwithshanazar.language_tutor_platform_be.service;

import com.learnwithshanazar.language_tutor_platform_be.dto.ContactMessageDTO;
import com.learnwithshanazar.language_tutor_platform_be.entity.ContactMessage;
import com.learnwithshanazar.language_tutor_platform_be.exception.ResourceNotFoundException;
import com.learnwithshanazar.language_tutor_platform_be.mapper.ContactMessageMapper;
import com.learnwithshanazar.language_tutor_platform_be.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;
    private final EmailService emailService;

    public List<ContactMessageDTO> getAllMessages() {
        return contactMessageRepository.findAll().stream()
                .map(contactMessageMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ContactMessageDTO> getUnreadMessages() {
        return contactMessageRepository.findByIsReadFalse().stream()
                .map(contactMessageMapper::toDTO)
                .collect(Collectors.toList());
    }

    public long getUnreadCount() {
        return contactMessageRepository.countByIsReadFalse();
    }

    public ContactMessageDTO getMessageById(String id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));
        return contactMessageMapper.toDTO(message);
    }

    public ContactMessageDTO createMessage(ContactMessageDTO messageDTO) {
        ContactMessage message = contactMessageMapper.toEntity(messageDTO);
        ContactMessage savedMessage = contactMessageRepository.save(message);

        // Send notification email
        emailService.sendNewMessageNotification(savedMessage);

        return contactMessageMapper.toDTO(savedMessage);
    }

    public ContactMessageDTO markAsRead(String id) {
        ContactMessage message = contactMessageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found with id: " + id));

        message.setIsRead(true);
        ContactMessage updatedMessage = contactMessageRepository.save(message);
        return contactMessageMapper.toDTO(updatedMessage);
    }

    public void deleteMessage(String id) {
        if (!contactMessageRepository.existsById(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        contactMessageRepository.deleteById(id);
    }
}