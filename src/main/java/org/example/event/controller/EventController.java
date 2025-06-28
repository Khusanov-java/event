package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.EventDTO;
import org.example.event.entity.Event;
import org.example.event.service.interfaces.EventService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Util.EVENT_PATH)
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody EventDTO dto) {
        return ResponseEntity.ok(eventService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody EventDTO dto) {
        return ResponseEntity.ok(eventService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}


