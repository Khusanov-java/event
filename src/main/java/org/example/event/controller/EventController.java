package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.EvenDTO;
import org.example.event.entity.Event;
import org.example.event.entity.User;
import org.example.event.repo.EventRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.EventService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(Util.EVENT_PATH)
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventService eventService;


    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return ResponseEntity.ok(optionalEvent.get().getDescription());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Event> createEvent(@RequestBody EvenDTO eventDto, @PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Event event=Event.builder()
                    .title(eventDto.getTitle())
                    .description(eventDto.getDescription())
                    .location(eventDto.getLocation())
                    .imageUrl(eventDto.getImageUrl())
                    .organizer(user)
                    .date(LocalDateTime.now())
                    .build();
            eventRepository.save(event);
            return ResponseEntity.ok(event);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/edit/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            if (updatedEvent.getTitle() != null) {
                event.setTitle(updatedEvent.getTitle());
            }
            if (updatedEvent.getDescription() != null) {
                event.setDescription(updatedEvent.getDescription());
            }
            if (updatedEvent.getLocation() != null) {
                event.setLocation(updatedEvent.getLocation());
            }
            if (updatedEvent.getImageUrl() != null) {
                event.setImageUrl(updatedEvent.getImageUrl());
            }
            if (updatedEvent.getOrganizer() != null) {
                event.setOrganizer(updatedEvent.getOrganizer());
            }
            eventRepository.save(event);
            return ResponseEntity.ok(event);

        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long eventId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            eventRepository.delete(event);
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search?title=x&location=y")
    public ResponseEntity<List<Event>> searchEvents(@RequestParam String title, @RequestParam String location) {
        List<Event> eventsByTitleAndLocation = eventRepository.getEventsByTitleAndLocation(title, location);
        if (!eventsByTitleAndLocation.isEmpty()) {
            return ResponseEntity.ok(eventsByTitleAndLocation);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Event>> searchEventsByCategory(@PathVariable Long categoryId) {
        List<Event> events = eventService.getEventsByCategory(categoryId);
        return ResponseEntity.ok(events);
    }

}
