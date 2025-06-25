package org.example.event.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.event.entity.Event;
import org.example.event.entity.Review;
import org.example.event.entity.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeDTO {
    private List<Event> latestEvents;
    private List<User> topOrganizers;
    private List<Review> recentReviews;
}
