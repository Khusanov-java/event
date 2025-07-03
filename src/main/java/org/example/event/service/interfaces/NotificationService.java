package org.example.event.service.interfaces;

import org.example.event.dtos.req.NotificationDTO;
import org.example.event.entity.Notification;

import java.util.List;

public interface NotificationService {
    String sendNotification(NotificationDTO notificationDTO);

    List<Notification> getUserNotifications(String userId);

    void markAsRead(Long id);
}