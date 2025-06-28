package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.EventDTO;
import org.example.event.entity.Event;
import org.example.event.entity.Review;
import org.example.event.entity.User;
import org.example.event.repo.EventRepository;
import org.example.event.repo.ReviewRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Event create(EventDTO dto) {
        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .location(dto.getLocation())
                .organizer(organizer)
                .averageRating(0.0)
                .build();

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public Event update(Long id, EventDTO dto) {
        Event event = getById(id);
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setLocation(dto.getLocation());

        return eventRepository.save(event);
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void updateAverageRating(Long eventId) {
        Event event = getById(eventId);
        List<Review> reviews = reviewRepository.findByEventId(eventId);

        double avg = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        event.setAverageRating(avg);
        eventRepository.save(event);
    }

    @Override
    public List<Event> getLatestEvents() {
        return eventRepository.findTop5ByOrderByDateDesc();
    }
}





