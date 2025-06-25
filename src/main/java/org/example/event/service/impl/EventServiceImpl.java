package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.EvenDTO;
import org.example.event.entity.Event;
import org.example.event.entity.EventCategory;
import org.example.event.entity.User;
import org.example.event.repo.EventCategoryRepository;
import org.example.event.repo.EventRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventCategoryRepository eventCategoryRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Event> getEventsByCategory(Long categoryId) {
        List<EventCategory> eventCategories = eventCategoryRepository.findAllByCategory_Id(categoryId);
        return eventCategories.stream()
                .map(EventCategory::getEvent)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Event getNewEvent(User user, EvenDTO eventDto) {
            Event event = Event.builder()
                    .title(eventDto.getTitle())
                    .description(eventDto.getDescription())
                    .location(eventDto.getLocation())
                    .imageUrl(eventDto.getImageUrl())
                    .organizer(user)
                    .date(LocalDateTime.now())
                    .build();
            return eventRepository.save(event);
        }

    @Override
    public List<Event> getLatestEvents() {
        return eventRepository.findTop5ByOrderByDateDesc();
    }

}




