package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.ReviewDTO;
import org.example.event.dtos.res.ReviewResDTO;
import org.example.event.entity.Event;
import org.example.event.entity.Review;
import org.example.event.entity.User;
import org.example.event.repo.EventRepository;
import org.example.event.repo.ReviewRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Review create(ReviewDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .user(user)
                .event(event)
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public Review update(Long id, ReviewDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        return reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewResDTO getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        Double totalRating = reviewRepository.calculateAverageRating(review.getEvent().getId());

        return ReviewResDTO.builder()
                .review(review)
                .totalRating(totalRating)
                .build();
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getByEvent(Long eventId) {
        return reviewRepository.findByEventId(eventId);
    }

    @Override
    public List<Review> getByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public List<Review> getRecentReviews() {
        return reviewRepository.findTop5ByOrderByCreatedAtDesc();
    }

}
