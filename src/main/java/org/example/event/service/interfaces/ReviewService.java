package org.example.event.service.interfaces;

import org.example.event.dtos.ReviewDTO;
import org.example.event.entity.Review;

import java.util.List;

public interface ReviewService {
    Review create(ReviewDTO dto);
    Review update(Long id, ReviewDTO dto);
    void delete(Long id);
    Review getById(Long id);
    List<Review> getAll();
    List<Review> getByEvent(Long eventId);
    List<Review> getByUser(Long userId);
}
