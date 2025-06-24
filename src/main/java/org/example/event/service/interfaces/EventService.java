package org.example.event.service.interfaces;

import org.example.event.entity.Event;

import java.util.List;

public interface EventService {

    List<Event> getEventsByCategory(Long categoryId);
}
