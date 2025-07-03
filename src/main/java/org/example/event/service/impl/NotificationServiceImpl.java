package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.NotificationDTO;
import org.example.event.entity.Notification;
import org.example.event.entity.User;
import org.example.event.repo.NotificationRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public String sendNotification(NotificationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = Notification.builder()
                .user(user)
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/notifications/" + dto.getUserId(), dto.getMessage());

        return "Notification sent";
    }

    @Override
    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setSeen(true);
        notificationRepository.save(notification);
    }
}
