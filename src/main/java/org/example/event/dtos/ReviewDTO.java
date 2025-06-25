package org.example.event.dtos;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long userId;
    private Long eventId;
    private Double rating;
    private String comment;
}
