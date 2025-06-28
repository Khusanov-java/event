package org.example.event.dtos.req;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long userId;
    private String message;
}