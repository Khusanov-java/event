package org.example.event.dtos.req;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String title;
    private String description;
    private LocalDate startTime;
    private LocalDate endTime;
    private String location;
    private String organizerId;
}