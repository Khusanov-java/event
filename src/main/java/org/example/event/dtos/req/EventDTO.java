package org.example.event.dtos.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String organizerId;
}