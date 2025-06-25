package org.example.event.repo;

import org.example.event.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findTop5ByOrderByCreatedAtDesc();

    List<Review> findByEventId(Long eventId);

    List<Review> findByUserId(Long userId);
}