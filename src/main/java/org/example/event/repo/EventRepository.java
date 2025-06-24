package org.example.event.repo;

import org.example.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {


    List<Event> getEventsByTitleAndLocation(String title, String location);
}