package org.example.event.dtos.req;

import lombok.Data;

@Data
public class MessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
}
