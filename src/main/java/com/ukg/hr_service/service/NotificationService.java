package com.ukg.hr_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ukg.hr_service.model.Notification;
import com.ukg.hr_service.repository.NotificationRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByReadOrderByCreatedAtDesc(false);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
