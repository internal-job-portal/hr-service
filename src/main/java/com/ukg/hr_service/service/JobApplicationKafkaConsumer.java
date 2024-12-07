package com.ukg.hr_service.service;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ukg.hr_service.model.Notification;
import com.ukg.hr_service.repository.NotificationRepository;

@Service
public class JobApplicationKafkaConsumer {

    private final NotificationRepository notificationRepository;

    public JobApplicationKafkaConsumer(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "job-applications", groupId = "hr-service")
    public void consume(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}
