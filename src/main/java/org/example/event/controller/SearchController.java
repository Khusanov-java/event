package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.Event;
import org.example.event.repo.EventRepository;
import org.example.event.service.interfaces.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.event.utils.Util.SEARCH_PATH;

@RestController
@RequestMapping(SEARCH_PATH)
@RequiredArgsConstructor
public class SearchController {

    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<List<Event>> searchEvents(@RequestParam("q") String keyword) {
        System.out.println(keyword);
        return ResponseEntity.ok(eventService.searchedEvent(keyword));
    }
}
