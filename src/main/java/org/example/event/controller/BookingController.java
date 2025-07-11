
package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.Booking;
import org.example.event.entity.Event;
import org.example.event.service.interfaces.BookingService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Util.BOOKING_PATH)
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/{eventId}/{userId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long eventId, @PathVariable String userId) {
        Booking booking = bookingService.create(userId, eventId);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Event>> getBooking(@PathVariable String userId) {
        List<Event> allBookedEvents = bookingService.getAllBookedEvents(userId);
        if (allBookedEvents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allBookedEvents);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Booking> deleteBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.deleteBooking(bookingId);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }
}

