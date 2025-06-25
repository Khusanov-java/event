package org.example.event.controller;


import lombok.RequiredArgsConstructor;
import org.example.event.entity.Booking;
import org.example.event.entity.Event;
import org.example.event.entity.User;
import org.example.event.repo.BookingRepository;
import org.example.event.repo.EventRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.BookingService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(Util.BOOKING_PATH)
public class BookingController {
    private final BookingService bookingService;


    @PostMapping("/{eventId}/{userId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long eventId, @PathVariable Long userId) {
        Booking booking = bookingService.create(eventId, userId);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getBooking(@PathVariable Long userId) {
        List<Event> allBookedEvents = bookingService.getAllBookedEvents(userId);
        if (allBookedEvents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allBookedEvents);
    }

}
