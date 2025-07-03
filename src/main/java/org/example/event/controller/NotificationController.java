package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.NotificationDTO;
import org.example.event.entity.Notification;
import org.example.event.service.interfaces.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.event.utils.Util.NOTIFICATION_PATH;

@RestController
@RequestMapping(NOTIFICATION_PATH)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDTO dto) {
        return ResponseEntity.ok(notificationService.sendNotification(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
