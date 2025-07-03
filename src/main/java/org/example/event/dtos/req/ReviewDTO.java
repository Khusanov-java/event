package org.example.event.dtos.req;

import lombok.Data;

import java.util.List;

@Data
public class ReviewDTO {
    private String userId;
    private Long eventId;
    private Integer rating;
    private String comment;
}
