package org.example.event.dtos.req;

import lombok.Data;

@Data
public class NotificationDTO {
    private String userId;
    private String message;
}