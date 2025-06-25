package org.example.event.service.interfaces;

import org.example.event.dtos.EvenDTO;
import org.example.event.entity.Event;
import org.example.event.entity.User;

import java.util.List;

public interface EventService {

    List<Event> getEventsByCategory(Long categoryId);


    Event getNewEvent(User user, EvenDTO eventDto);

    List<Event> getLatestEvents();
}
