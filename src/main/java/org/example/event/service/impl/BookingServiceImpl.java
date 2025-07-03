
package org.example.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.Booking;
import org.example.event.entity.Event;
import org.example.event.entity.User;
import org.example.event.repo.BookingRepository;
import org.example.event.repo.EventRepository;
import org.example.event.repo.UserRepository;
import org.example.event.service.interfaces.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Booking create(String userId, Long eventId) {
        Optional<User> byId = userRepository.findById(userId);
        Optional<Event> byEvent = eventRepository.findById(eventId);

        if (byId.isPresent() && byEvent.isPresent()) {
            User user = byId.get();
            Event event = byEvent.get();
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setEvent(event);
            booking.setStatus(Booking.Status.BOOKED);
            booking.setBookedAt(LocalDateTime.now());
            return bookingRepository.save(booking);
        }
        return null;
    }

    @Override
    public List<Event> getAllBookedEvents(String userId) {
        List<Booking> bookings = bookingRepository.findAllByUserIdAndStatus(userId, Booking.Status.BOOKED);
        return bookings.stream()
                .map(Booking::getEvent)
                .collect(Collectors.toList());
    }

    @Override
    public Booking deleteBooking(Long bookingId) {
        Optional<Booking> byId = bookingRepository.findById(bookingId);
        if (byId.isPresent()) {
            Booking booking = byId.get();
            booking.setStatus(Booking.Status.CANCELLED);
            bookingRepository.save(booking);
            return booking;
        } else {
            return null;
        }
    }
}

