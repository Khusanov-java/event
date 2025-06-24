package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.Event;
import org.example.event.entity.EventCategory;
import org.example.event.repo.EventCategoryRepository;
import org.example.event.repo.EventRepository;
import org.example.event.service.interfaces.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventCategoryRepository eventCategoryRepository;

    @Override
    public List<Event> getEventsByCategory(Long categoryId) {
        List<EventCategory> eventCategories = eventCategoryRepository.findAllByCategory_Id(categoryId);
        return eventCategories.stream()
                .map(EventCategory::getEvent)
                .distinct()
                .collect(Collectors.toList());
    }
    }

