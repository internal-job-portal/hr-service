package com.ukg.hr_service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ukg.hr_service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReadOrderByCreatedAtDesc(boolean read);
}
