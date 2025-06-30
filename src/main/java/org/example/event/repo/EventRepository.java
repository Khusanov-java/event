package org.example.event.repo;

import org.example.event.entity.Category;
import org.example.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findTop5ByOrderByDateDesc();

    List<Event> findByTitleContainingIgnoreCase(String keyword);

    @Query("""
    SELECT e FROM Event e
    WHERE (:categoryId IS NULL OR e.category.id = :categoryId)
      AND (:filterDate IS NULL OR (:filterDate BETWEEN e.startTime AND e.endTime))
      AND (:minPrice IS NULL OR e.price >= :minPrice)
      AND (:maxPrice IS NULL OR e.price <= :maxPrice)
""")
    List<Event> filterEvents(
            @Param("categoryId") Long categoryId,
            @Param("filterDate") LocalDate filterDate,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );


}