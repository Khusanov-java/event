package org.example.event.repo;

import org.example.event.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {

    List<EventCategory> findAllByCategory_Id(Long categoryId);

}