package org.example.event.service.interfaces;

import org.example.event.dtos.req.ReviewDTO;
import org.example.event.dtos.res.ReviewResDTO;
import org.example.event.entity.Review;

import java.util.List;

public interface
ReviewService {
    Review create(ReviewDTO dto);

    Review update(Long id, ReviewDTO dto);

    void delete(Long id);

    ReviewResDTO getById(Long id);

    List<Review> getAll();

    List<Review> getByEvent(Long eventId);

    List<Review> getByUser(String userId);

    List<Review> getRecentReviews();

}
