package org.example.event.service.interfaces;

import org.example.event.dtos.req.EventDTO;
import org.example.event.entity.Event;

import java.util.List;

public interface EventService {
    List<Event> getLatestEvents();
    Event create(EventDTO dto);
    List<Event> getAll();
    Event getById(Long id);
    Event update(Long id, EventDTO dto);
    void delete(Long id);
    void updateAverageRating(Long eventId);
}
