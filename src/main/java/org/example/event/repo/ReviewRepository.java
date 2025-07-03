package org.example.event.repo;

import org.example.event.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findTop5ByOrderByCreatedAtDesc();

    List<Review> findByEventId(Long eventId);

    List<Review> findByUserId(String userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.event.id = :eventId")
    Double calculateAverageRating(@Param("eventId") Long eventId);
}