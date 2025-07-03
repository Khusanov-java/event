
package org.example.event.service.interfaces;

import org.example.event.entity.Booking;
import org.example.event.entity.Event;
import org.example.event.entity.User;

import java.util.List;

public interface BookingService {

    Booking create(String userId, Long eventId);

    List<Event> getAllBookedEvents(String userId);


    Booking deleteBooking(Long bookingId);
}
